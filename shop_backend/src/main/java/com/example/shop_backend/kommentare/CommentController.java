package com.example.shop_backend.kommentare;


import com.example.shop_backend.user.User;
import com.example.shop_backend.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;



@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentRepository repo;
    private final UserRepository userRepository;


    public CommentController(CommentRepository repo, UserRepository userRepository) {
        this.repo = repo;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Comment> findAll(){
        return repo.findAll();
    }

    @PostMapping
    public Comment save(@RequestBody Comment comment,  @RequestHeader("X-Client-Id") String clientId) {
        Comment newComment = new Comment();
        User user =new User();
        newComment.setClientId(clientId);
        newComment.setComment(comment.getComment());
        newComment.setProductId(comment.getProductId());
        String username = userRepository.findByClientId(clientId).map(User::getName).orElse(null);
        newComment.setUsername(username);
        return repo.save(newComment);
    }






}
