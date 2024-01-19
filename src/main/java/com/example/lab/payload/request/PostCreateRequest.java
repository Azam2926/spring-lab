package com.example.lab.payload.request;

import com.example.lab.model.Comment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class PostCreateRequest {
    private String title;

    private String description;

    private LocalDateTime publishedDate;

    @JsonIgnoreProperties("post")
    private Set<Comment> comments;
}
