package com.example.lab.repository;

import com.example.lab.model.Post;
import com.example.lab.payload.request.PostParams;
import com.example.lab.payload.response.PostDto;
import com.example.lab.repository.commons.BaseSpecs;
import com.example.lab.repository.commons.WithDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface PostCustomRepo extends WithDto<Post, PostDto> {
    Page<PostDto> findAllDto(PostParams params, Pageable pageable);

    class Specs extends BaseSpecs {
        public static Specification<Post> byTitle(String title) {
            return contains("title", title);
        }

        public static Specification<Post> byDescription(String description) {
            return contains("description", description);
        }
    }
}
