package com.example.demo.src.user.repository;

import com.example.demo.src.user.entity.Terms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermsRepository extends JpaRepository<Terms, Long> {
}
