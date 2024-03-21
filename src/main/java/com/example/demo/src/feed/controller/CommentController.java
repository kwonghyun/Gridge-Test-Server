package com.example.demo.src.feed.controller;

import com.example.demo.common.Constant;
import com.example.demo.common.model.PageReq;
import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.feed.model.CommentIdRes;
import com.example.demo.src.feed.model.GetCommentRes;
import com.example.demo.src.feed.model.PostCommentReq;
import com.example.demo.src.feed.service.CommentService;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("app/feeds")
public class CommentController {
    private final CommentService commentService;
    private final JwtService jwtService;

    @PostMapping("{feedId}/comments")
    public BaseResponse<CommentIdRes> createComment(
            @PathVariable("feedId") Long feedId,
            @RequestBody @Valid PostCommentReq postCommentReq
    ) {
        Long userId = jwtService.getUserId();
        CommentIdRes commentIdRes = commentService.createComment(postCommentReq, feedId, userId);
        return new BaseResponse(commentIdRes);
    }

    @GetMapping("{feedId}/comments")
    public BaseResponse<Slice<GetCommentRes>> getComments(
            @PathVariable("feedId") Long feedId,
            @ModelAttribute @Valid PageReq pageReq
            ) {
        jwtService.validateJwt();
        Pageable pageable = PageRequest.of(pageReq.getPage(), pageReq.getSize());
        Slice<GetCommentRes> commentsSliced = commentService.getCommentsSliced(feedId, pageable);
        return new BaseResponse<>(commentsSliced);
    }

    @PatchMapping("{feedId}/comments/{commentId}/inactive")
    public BaseResponse<String> deleteComment(
            @PathVariable("feedId") Long feedId,
            @PathVariable("commentId") Long commentId
    ) {
        Long userId = jwtService.getUserId();
        commentService.deleteComment(commentId, feedId, userId);
        return new BaseResponse<>(Constant.DELETED);
    }
}
