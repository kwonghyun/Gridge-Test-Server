package com.example.demo.src.feed.repository;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.feed.entity.MediaContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MediaContentRepository extends JpaRepository<MediaContent, Long> {
    Boolean existsByIdAndState(Long mediaContentId, BaseEntity.State state);
    default Boolean existsByIdAndState(Long mediaContentId) {
        return existsByIdAndState(mediaContentId, BaseEntity.State.ACTIVE);
    }

    @Query(
            "SELECT m FROM MediaContent m " +
                    "WHERE (m.id = :mediaContentId) " +
                    "AND m.state = com.example.demo.common.entity.BaseEntity$State.ACTIVE"
    )
    Optional<MediaContent> findActiveContentById(@Param("mediaContentId") Long mediaContentId);

}
