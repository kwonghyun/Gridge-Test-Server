package com.example.demo.src.feed.controller;

import com.example.demo.common.model.CustomPageable;
import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.feed.model.GetCommentRes;
import com.example.demo.src.feed.model.PostCommentReq;
import com.example.demo.src.feed.service.CommentService;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;
    private final JwtService jwtService;

    @PostMapping("{feedId}/comments")
    public BaseResponse createComment(
            @PathVariable("feedId") Long feedId,
            PostCommentReq postCommentReq
    ) {
        Long userId = jwtService.getUserId();
        commentService.createComment(postCommentReq, feedId, userId);
        return new BaseResponse("생성완료");
    }

    @GetMapping("{feedId}/comments")
    public BaseResponse getComments(
            @PathVariable("feedId") Long feedId,
            CustomPageable pageable
    ) {
        jwtService.validateJwt();
        Slice<GetCommentRes> commentsSliced = commentService.getCommentsSliced(feedId, pageable);
        return new BaseResponse(commentsSliced);
    }

    @PatchMapping("{feedId}/comments/{commentId}/inactive")
    public BaseResponse deleteComment(
            @PathVariable("feedId") Long feedId,
            @PathVariable("commentId") Long commentId
    ) {
        Long userId = jwtService.getUserId();
        commentService.deleteComment(commentId, feedId, userId);
        return new BaseResponse<>("삭제 완료");
    }
}
