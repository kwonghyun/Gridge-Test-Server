package com.example.demo.src.feed.repository;

import com.example.demo.src.feed.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(
            "SELECT c FROM Comment c JOIN FETCH c.user " +
                    "WHERE (c.feed.id = :feedId) " +
                    "AND (c.state = com.example.demo.common.entity.BaseEntity$State.ACTIVE) " +
                    "AND (c.isVisible = true) " +
                    "AND (" +
                        "(SELECT COUNT(cc) FROM Comment cc " +
                            "WHERE (cc.feed.id = :feedId) " +
                            "AND (cc.state = com.example.demo.common.entity.BaseEntity$State.ACTIVE) " +
                            "AND (cc.isVisible = true)) < :count" +
                        ") " +
                    "ORDER BY c.createdAt DESC "
    )
    List<Comment> findWithUserByFeedIdIfCountUnder(@Param("feedId") Long feedId, @Param("count") Long count);

    @Query(
            "SELECT c FROM Comment c " +
                    "WHERE (c.feed.id = :feedId) " +
                    "AND (c.state = com.example.demo.common.entity.BaseEntity$State.ACTIVE) " +
                    "AND (c.isVisible = true) " +
                    "ORDER BY c.createdAt DESC "
    )
    Slice<Comment> findVisibleCommentsByFeedId(@Param("feedId") Long feedId, Pageable pageable);


    @Query(
            "SELECT c FROM Comment c " +
                    "WHERE (c.id = :commentId) " +
                    "AND (c.state = com.example.demo.common.entity.BaseEntity$State.ACTIVE) " +
                    "AND (c.isVisible = true) "
    )
    Optional<Comment> findVisibleCommentById(@Param("commentId") Long commentId);

    @Query(
            "SELECT COUNT(c) FROM Comment c " +
                    "WHERE (c.feed.id = :feedId) " +
                    "AND (c.state = com.example.demo.common.entity.BaseEntity$State.ACTIVE) " +
                    "AND (c.isVisible = true) "
    )
    int countVisibleCommentsByFeedId(@Param("feedId") Long feedId);
    @Query(
            "SELECT c FROM Comment c " +
                    "WHERE c.feed.id = :feedId " +
                    "AND (c.state = com.example.demo.common.entity.BaseEntity$State.ACTIVE) "
    )
    List<Comment> findAllActiveCommentsByFeedId(@Param("feedId") Long feedId);
}
