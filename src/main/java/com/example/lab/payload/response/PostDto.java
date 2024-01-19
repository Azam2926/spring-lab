package com.example.lab.payload.response;

import com.example.lab.model.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime publishedDate;


    public PostDto(Post entity) {
        setId(entity.getId());
        setTitle(entity.getTitle());
        setDescription(entity.getDescription());
        setPublishedDate(entity.getPublishedDate());
    }
}
