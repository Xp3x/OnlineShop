$ErrorActionPreference = 'Stop'

$javaExecutable = if ($env:JAVA_HOME) {
    Join-Path $env:JAVA_HOME 'bin/java.exe'
} else {
    (Get-Command java -ErrorAction Stop).Source
}

$jarPath = Join-Path $PSScriptRoot 'target/shop_backend-0.0.1-SNAPSHOT.jar'
if (-not (Test-Path -LiteralPath $jarPath)) {
    throw 'Backend zuerst mit .\mvnw.cmd package bauen.'
}

# Use the Windows certificate store while retaining TLS certificate verification.
Push-Location $PSScriptRoot
try {
    & $javaExecutable '-Djavax.net.ssl.trustStoreType=Windows-ROOT' '-Djavax.net.ssl.trustStore=NONE' '-jar' $jarPath @args
    $backendExitCode = $LASTEXITCODE
} finally {
    Pop-Location
}
exit $backendExitCode
