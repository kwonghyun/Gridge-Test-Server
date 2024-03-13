package com.example.demo.src.test;

import com.example.demo.src.test.entity.CommentTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentTestRepository extends JpaRepository<CommentTest, Long> {
}
