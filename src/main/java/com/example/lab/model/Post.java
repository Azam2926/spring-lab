package com.example.lab.model;

import com.example.lab.payload.request.PostCreateRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @Column(length = 8192)
    private String description;

    private LocalDateTime publishedDate;

    @ToString.Exclude
    @JsonIgnoreProperties("post")
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    public Post(PostCreateRequest requested) {
        title = requested.getTitle();
        description = requested.getDescription();
        publishedDate = requested.getPublishedDate();

        addComments(requested.getComments());
    }

    public Post() {

    }

    public void addComments(Iterable<Comment> comments) {
        for (Comment comment : comments)
            addComment(comment);

    }

    private void addComment(Comment comment) {
        comment.setPost(this);
        this.comments.add(comment);
    }
}
