package com.example.demo.src.report;

import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.report.model.PostReportCommentReq;
import com.example.demo.src.report.model.PostReportFeedReq;
import com.example.demo.utils.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@Tag(name = "feed 도메인", description = "Feed API, MediaContent(이미지/동영상) API") // swagger 접속: http://localhost:3000/swagger-ui/index.html
@RequestMapping("app/feeds")
public class ReportController {
    private final ReportService reportService;
    private final JwtService jwtService;

    @PostMapping("{feedId}/reports")
    @ResponseBody
    public BaseResponse reportFeed(
            @PathVariable("feedId") Long feedId,
            @RequestBody @Valid PostReportFeedReq req
    ) {
        Long userId = jwtService.getUserId();
        reportService.reportFeed(req, feedId, userId);
        return new BaseResponse("신고완료");
    }

    @PostMapping("{feedId}/comments/{commentId}/reports")
    @ResponseBody
    public BaseResponse reportComment(
            @PathVariable("feedId") Long feedId,
            @PathVariable("commentId") Long commentId,
            @RequestBody @Valid PostReportCommentReq req
    ) {
        Long userId = jwtService.getUserId();
        reportService.reportComment(req, commentId, feedId, userId);
        return new BaseResponse("신고완료");
    }
}
