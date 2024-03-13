package com.example.demo.src.feed.repository;

import com.example.demo.src.feed.entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilesRepository extends JpaRepository<Files, Long> {
}
