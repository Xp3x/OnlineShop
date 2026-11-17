package com.example.shop_backend.user;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(
        origins = "http://localhost:5173",
        allowedHeaders = {"Content-Type", "X-Client-Id"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
)
public class UserController {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserController(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @GetMapping
    public User findCurrent(@RequestHeader("X-Client-Id") String clientId) {
        return repo.findByClientId(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }





    @PostMapping
    public User save(@Valid @RequestBody User user, @RequestHeader("X-Client-Id") String clientId) {
        User u = new User();
        u.setEmail(user.getEmail());
        u.setName(user.getName());
        u.setPassword(user.getPassword());
        u.setOnlineStatus(user.getOnlineStatus());
        u.setClientId(clientId);
        u.setAdmin(Boolean.TRUE.equals(user.getAdmin()));

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Du musst ein Passwort eingeben");
        }
        u.setPassword(encoder.encode(user.getPassword()));
        return repo.save(u);
    }




    @PutMapping
    public User update(@RequestBody User user, @RequestHeader("X-Client-Id") String clientId) {
        User u = repo.findByClientId(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        u.setName(user.getName());
        u.setEmail(user.getEmail());
        u.setOnlineStatus(user.getOnlineStatus());

        if  (user.getPassword() != null && !user.getPassword().isBlank()) {
            u.setPassword(encoder.encode(user.getPassword()));
        }

        return repo.save(u);
    }


}
