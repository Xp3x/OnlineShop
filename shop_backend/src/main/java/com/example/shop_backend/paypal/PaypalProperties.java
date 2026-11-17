package com.example.shop_backend.paypal;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix ="paypal")
public class PaypalProperties {
    private String env = "sandbox";
    private String clientId;
    private String clientSecret;
    private String appBaseUrl;
    private String uiBaseUrl;




    public String baseUrl(){
        return "live".equalsIgnoreCase(env) ? "https://api-m.paypal.com"
                : "https://api-m.sandbox.paypal.com";
    }

    public String getEnv() {
        return env;
    }
    public void setEnv(String env) {
        this.env = env;
    }
    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public String getClientSecret() {
        return clientSecret;
    }
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
    public String getAppBaseUrl() {
        return appBaseUrl;
    }
    public void setAppBaseUrl(String appBaseUrl) {
        this.appBaseUrl = appBaseUrl;
    }
    //(appBaseUrl != null && !appBaseUrl.isBlank()) ? appBaseUrl : null

    public String getUiBaseUrl() {
        return uiBaseUrl;
    }
    public void setUiBaseUrl(String uiBaseUrl) {
        this.uiBaseUrl = uiBaseUrl;
    }

}
