package com.example.demo.src.feed.repository;

import com.example.demo.src.feed.entity.MediaConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MediaConnectionRepository extends JpaRepository<MediaConnection, Long> {
    @Query(
            "SELECT m FROM MediaConnection m JOIN FETCH m.mediaContent " +
                    "WHERE (m.feed.id = :feedId) " +
                    "AND (m.state = com.example.demo.common.entity.BaseEntity$State.ACTIVE) " +
                    "ORDER BY m.displayOrder ASC "
    )
    List<MediaConnection> findAllActiveMediaByFeedId(@Param("feedId") Long feedId);
}
