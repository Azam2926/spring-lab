package com.example.lab.repository.impl;

import com.example.lab.model.Post;
import com.example.lab.payload.request.PostParams;
import com.example.lab.payload.response.PostDto;
import com.example.lab.repository.PostCustomRepo;
import com.example.lab.repository.commons.impl.WithDtoImpl;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import static com.example.lab.repository.PostCustomRepo.Specs.byDescription;
import static com.example.lab.repository.PostCustomRepo.Specs.byTitle;
import static org.springframework.data.jpa.domain.Specification.where;

@Slf4j
@Repository
public class PostCustomRepoImpl extends WithDtoImpl<Post, PostDto> implements PostCustomRepo {
    public PostCustomRepoImpl(EntityManager em) {
        super(em, Post.class, PostDto.class);
    }

    @Override
    public Page<PostDto> findAllDto(PostParams params, Pageable pageable) {

        log.info("Params: {}", params);
        Specification<Post> specification = where(byTitle(params.getTitle()))
                .and(byDescription(params.getDescription()));

        return findAllDto(specification, pageable);
    }
}
