package com.example.shop_backend.user;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public AuthController(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public record LoginDto(String email, String password) {}
    public record UserDto(Long id, String name, String email, Boolean admin, Boolean onlineStatus, String clientId) {}

    @PostMapping("/login")
    public UserDto login(@RequestBody LoginDto dto, @RequestHeader("X-Client-Id") String clientId) {
        var u = repo.findByClientId(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        if (!encoder.matches(dto.password(), u.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }


        u.setOnlineStatus(true);
        repo.save(u);

        return new UserDto(u.getId(), u.getName(), u.getEmail(), u.getAdmin(), u.getOnlineStatus(), u.getClientId());
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("X-Client-Id") String clientId) {
        var u = repo.findByClientId(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        u.setOnlineStatus(false);
        repo.save(u);
    }
}
