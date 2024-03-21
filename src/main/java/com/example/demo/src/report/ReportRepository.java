package com.example.demo.src.report;

import com.example.demo.src.report.entity.CommentReport;
import com.example.demo.src.report.entity.FeedReport;
import com.example.demo.src.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query(
            "SELECT r FROM FeedReport r " +
                    "WHERE (r.feed.id = :feedId) " +
                    "AND (r.user.id = :userId) " +
                    "AND r.state = com.example.demo.common.entity.BaseEntity$State.ACTIVE "
    )
    Optional<FeedReport> findByFeedIdAndUserId(@Param("feedId") Long feedId, @Param("userId") Long userId);

    @Query(
            "SELECT r FROM CommentReport r " +
                    "WHERE (r.comment.id = :commentId) " +
                    "AND (r.user.id = :userId) " +
                    "AND r.state = com.example.demo.common.entity.BaseEntity$State.ACTIVE "
    )
    Optional<CommentReport> findByCommentIdAndUserId(@Param("commentId") Long commentId, @Param("userId") Long userId);
}
