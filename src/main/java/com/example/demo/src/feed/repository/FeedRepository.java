package com.example.demo.src.feed.repository;

import com.example.demo.src.feed.entity.Feed;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    @Query(
            "SELECT f FROM Feed f JOIN FETCH f.user " +
                    "WHERE (f.id = :feedId) " +
                    "AND (f.state = com.example.demo.common.entity.BaseEntity$State.ACTIVE) " +
                    "AND (f.visible = true) "
    )
    Optional<Feed> findValidFeedWithUserById(@Param("feedId") Long feedId);

    @Query(
            "SELECT f FROM Feed f " +
                    "WHERE (f.id = :feedId) " +
                    "AND (f.state = com.example.demo.common.entity.BaseEntity$State.ACTIVE) "
    )
    Optional<Feed> findActiveFeedById(@Param("feedId") Long feedId);

    @Query(
            "SELECT f FROM Feed f JOIN FETCH f.user " +
                    "WHERE (f.state = com.example.demo.common.entity.BaseEntity$State.ACTIVE) " +
                    "AND (f.visible = true) " +
                    "ORDER BY f.createdAt DESC "
    )
    Slice<Feed> findAllValidFeedsWithUser(Pageable pageable);
}
