package com.example.lab.repository;

public interface CustomPostRepository {
    Long count();
    Long countPostComments();

    Long getCountOfPostsHavingMultipleComments();
}
