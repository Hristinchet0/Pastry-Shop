package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.CommentDto;
import com.example.pastry.shop.model.entity.Comment;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("")
    public ResponseEntity<Comment> createComment(@RequestBody CommentDto commentDto,
                                                 @AuthenticationPrincipal Users user) {
        Comment comment = commentService.save(commentDto, user);
        return ResponseEntity.ok(comment);
    }
}
