package com.example.pastry.shop.service;

import com.example.pastry.shop.model.dto.CommentDto;
import com.example.pastry.shop.model.entity.Comment;
import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.repository.CommentRepository;
import com.example.pastry.shop.repository.ShopsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ShopsRepository shopsRepository;

    public CommentService(CommentRepository commentRepository, ShopsRepository shopsRepository) {
        this.commentRepository = commentRepository;
        this.shopsRepository = shopsRepository;
    }

    public Comment save(CommentDto commentDto, Users user) {
        Comment comment = new Comment();
        Shops shopId = shopsRepository.getById(commentDto.getShopId());

        comment.setText(commentDto.getText());
        comment.setCreatedBy(user);
        comment.setShops(shopId);
        comment.setCreatedDate(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    public Set<Comment> getCommentsByShopId(Long shopId) {
        Set<Comment> comments = commentRepository.findByShopId(shopId);
        return comments;
    }
}
