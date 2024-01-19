package com.example.lab.payload.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PostParams extends CommonParams {
    private String title;
    private String description;

    @Override
    public String toString() {
        return "PostParams{" +
                "title=" + title +
                ", description=" + description +
                ", " + super.toString() +
                '}';
    }
}
