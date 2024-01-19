package com.example.lab.service.impl;

import com.example.lab.model.Comment;
import com.example.lab.model.Post;
import com.example.lab.payload.request.PostCreateRequest;
import com.example.lab.repository.CommentRepository;
import com.example.lab.repository.PostRepository;
import com.example.lab.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PostServiceImpl implements PostService {
    final PostRepository repository;
    final CommentRepository commentRepository;

    public PostServiceImpl(PostRepository repository, CommentRepository commentRepository) {
        this.repository = repository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public Post save(PostCreateRequest requested) {
        Post post = new Post(requested);
        Post saved = repository.save(post);

        Comment comment = new Comment();
        comment.setComment("Automatic comment");
        comment.setPost(saved);
        comment.setCreatedDate(LocalDateTime.now());

        commentRepository.save(comment);
        log.info("Saved automatic comment: {}", comment);

        log.info("Saved post: {}", saved);


        return saved;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public Post getById(Long id) {
        log.info("Finding post by id: {}", id);

        Post post = repository.findById(id).orElse(new Post());

        return post;
    }
}
