package com.example.demo.src.feed.controller;

import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.feed.model.PostReportCommentReq;
import com.example.demo.src.feed.model.PostReportFeedReq;
import com.example.demo.src.feed.service.ReportService;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ReportController {
    private final ReportService reportService;
    private final JwtService jwtService;

    @PostMapping("{feedId}/reports}")
    public BaseResponse reportFeed(
            @PathVariable("feedId") Long feedId,
            @RequestBody PostReportFeedReq req
    ) {
        Long userId = jwtService.getUserId();
        reportService.reportFeed(req, feedId, userId);
        return new BaseResponse("신고완료");
    }

    @PostMapping("{feedId}/comments/{commentId}/reports")
    public BaseResponse reportComment(
            @PathVariable("feedId") Long feedId,
            @PathVariable("commentId") Long commentId,
            @RequestBody PostReportCommentReq req
    ) {
        Long userId = jwtService.getUserId();
        reportService.reportComment(req, commentId, feedId, userId);
        return new BaseResponse("신고완료");
    }
}
