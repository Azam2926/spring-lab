package com.example.lab.repository.impl;

import com.example.lab.model.Comment;
import com.example.lab.model.Post;
import com.example.lab.model.QComment;
import com.example.lab.model.QPost;
import com.example.lab.repository.CustomPostRepository;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomPostRepositoryImpl implements CustomPostRepository {
    private final EntityManager em;

    public CustomPostRepositoryImpl(EntityManager em) {
        this.em = em;
    }


    @Override
    public Long count() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Post> root = query.from(Post.class);

        // Subquery to count all books
        CriteriaQuery<Long> countSubquery = cb.createQuery(Long.class);
        countSubquery.select(cb.count(countSubquery.from(Post.class))); // Count all rows in the books table

        // Set the count result in the main query
        query.select(countSubquery.getSelection());

        return em.createQuery(query).getSingleResult();
    }

    public Long countPostComments() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Subquery<Long> subquery = cq.subquery(Long.class);
        Root<Post> post = subquery.from(Post.class);
        Join<Post, Comment> comment = post.join("comments", JoinType.LEFT);

        subquery.select(cb.count(comment.get("id")))
                .having(cb.greaterThan(cb.count(comment.get("id")), 1L))
                .groupBy(post.get("id"));

        cq.select(cb.count(subquery));

        Long count = em.createQuery(cq).getSingleResult();

        System.out.println("Count of    posts with more than one comment: " + count);
        return count;
    }

    public Long getCountOfPostsHavingMultipleComments() {
        JPAQuery<Tuple> query = new JPAQuery<>();
        QPost post = QPost.post;
        QComment comment = QComment.comment1;

        List<Post> tuples = query.from(post)
                .leftJoin(comment).on(post.id.eq(comment.post.id))
                .groupBy(post)
                .having(comment.count().gt(1L))
                .select(Projections.fields(Post.class, post, comment.count().as("commentCount")))
                .fetch();

        return (long) tuples.size();
    }
}


