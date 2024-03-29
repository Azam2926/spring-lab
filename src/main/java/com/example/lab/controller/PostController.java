package com.example.lab.controller;

import com.example.lab.model.Post;
import com.example.lab.payload.request.PostCreateRequest;
import com.example.lab.payload.request.PostParams;
import com.example.lab.payload.response.PostDto;
import com.example.lab.repository.PostCustomRepo;
import com.example.lab.repository.PostRepository;
import com.example.lab.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    final PostRepository repository;
    final PostService service;
    final PostCustomRepo customRepo;

    @GetMapping
    public Page<Post> all(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @GetMapping("dto")
    public Page<PostDto> all(@ModelAttribute PostParams params, Pageable pageable) {
        return customRepo.findAllDto(params, pageable);
    }

    @PostMapping
    public Post save(@RequestBody PostCreateRequest post) {
        Post saved = service.save(post);

        Post byId = service.getById(saved.getId());

        return byId;
    }

    @GetMapping("{id}")
    public Post one(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("count")
    public Long count() {
        return service.count();
    }

}
