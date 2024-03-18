package com.example.demo.src.oauth;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.oauth.entity.OAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuthRepository extends JpaRepository<OAuth, Long> {
    Optional<OAuth> findByExternalIdAndState(String externalId, BaseEntity.State state);

    Optional<OAuth> findByUserIdAndState(Long userId, BaseEntity.State state);
}
