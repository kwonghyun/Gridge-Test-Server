package com.example.demo.src.report;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.feed.entity.*;
import com.example.demo.src.feed.repository.CommentRepository;
import com.example.demo.src.feed.repository.FeedRepository;
import com.example.demo.src.report.entity.CommentReport;
import com.example.demo.src.report.entity.FeedReport;
import com.example.demo.src.report.entity.Report;
import com.example.demo.src.report.model.PostReportCommentReq;
import com.example.demo.src.report.model.PostReportFeedReq;
import com.example.demo.src.user.UserRepository;
import com.example.demo.src.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final FeedRepository feedRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public void reportFeed(PostReportFeedReq req, Long feedId, Long userId) {
        if (reportRepository.findByFeedIdAndUserId(feedId, userId).isPresent()) {
            throw new BaseException(BaseResponseStatus.ALREADY_REPORTED_FEED);
        }

        Feed feed = feedRepository.findActiveFeedById(feedId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_FEED));
        User user = userRepository.getReferenceById(userId);
        reportRepository.save(
                FeedReport.builder()
                        .feed(feed)
                        .user(user)
                        .reason(Report.Reason.valueOf(req.getReason().toUpperCase()))
                        .build()
        );

    }

    public void reportComment(PostReportCommentReq req, Long commentId, Long feedId, Long userId) {
        if (reportRepository.findByCommentIdAndUserId(commentId, userId).isPresent()) {
            throw new BaseException(BaseResponseStatus.ALREADY_REPORTED_COMMENT);
        }

        Comment comment = commentRepository.findVisibleCommentById(commentId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_COMMENT));

        Long commentFeedId = comment.getFeed().getId();
        if (!commentFeedId.equals(feedId)) {
            throw new BaseException(BaseResponseStatus.NOT_AUTHORIZED_COMMENT);
        }

        User user = userRepository.getReferenceById(userId);
        reportRepository.save(
                CommentReport.builder()
                        .comment(comment)
                        .user(user)
                        .reason(Report.Reason.valueOf(req.getReason().toUpperCase()))
                        .build()
        );

    }


}
