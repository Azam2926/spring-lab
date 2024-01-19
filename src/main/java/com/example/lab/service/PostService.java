package com.example.lab.service;

import com.example.lab.model.Post;
import com.example.lab.payload.request.PostCreateRequest;

public interface PostService {
    Post save(PostCreateRequest requested);

    Post getById(Long id);
}
