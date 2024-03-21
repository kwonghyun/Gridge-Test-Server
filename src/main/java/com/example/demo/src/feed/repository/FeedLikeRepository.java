package com.example.demo.src.feed.repository;

import com.example.demo.src.feed.entity.FeedLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FeedLikeRepository extends JpaRepository<FeedLike, Long> {

    @Query(
            "SELECT COUNT(f.id) > 0 " +
                    "FROM FeedLike f " +
                        "WHERE (f.feed.id = :feedId) " +
                        "AND (f.user.id = :userId) " +
                        "AND (f.liked = true) " +
                        "AND (f.state = com.example.demo.common.entity.BaseEntity$State.ACTIVE)"
    )
    Boolean existsLikeByFeedIdAndUserId(@Param("feedId") Long feedId, @Param("userId") Long userId);

    @Query(
            "SELECT f FROM FeedLike f " +
                    "WHERE (f.feed.id = :feedId) " +
                    "AND (f.user.id = :userId) " +
                    "AND (f.state = com.example.demo.common.entity.BaseEntity$State.ACTIVE)"
    )
    Optional<FeedLike> findByFeedIdAndUserId(@Param("feedId") Long feedId, @Param("userId") Long userId);

    @Query(
            "SELECT COUNT(l.id) FROM FeedLike l " +
                    "WHERE (l.feed.id = :feedId) " +
                    "AND (l.liked = true) " +
                    "AND (l.state = com.example.demo.common.entity.BaseEntity$State.ACTIVE) "

    )
    int countLikesByFeedId(@Param("feedId") Long feedId);

    @Query(
            "SELECT l FROM FeedLike l " +
                    "WHERE (l.feed.id = :feedId) " +
                    "AND (l.state = com.example.demo.common.entity.BaseEntity$State.ACTIVE) "
    )
    List<FeedLike> findAllActiveLikesByFeedId(@Param("feedId") Long feedId);
}
