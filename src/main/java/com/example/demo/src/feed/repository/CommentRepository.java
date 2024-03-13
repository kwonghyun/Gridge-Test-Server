package com.example.demo.src.feed.repository;

import com.example.demo.src.test.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
