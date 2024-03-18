package com.example.demo.src.feed.controller;

import com.example.demo.common.model.CustomPageable;
import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.feed.model.*;
import com.example.demo.src.feed.service.FeedService;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/feeds")
public class FeedController {
    private final FeedService feedService;
    private final JwtService jwtService;

    @PostMapping
    public BaseResponse<PostFeedRes> createFeed(@RequestBody PostFeedReq postFeedReq) {
        Long userId = jwtService.getUserId();
        PostFeedRes feed = feedService.createFeed(postFeedReq, userId);
        return new BaseResponse<>(feed);
    }

    @GetMapping
    public BaseResponse<Slice<GetFeedRes>> getFeeds(CustomPageable pageable) {
        Long userId = jwtService.getUserId();
        Slice<GetFeedRes> slicedFeeds = feedService.getFeeds(pageable, userId);
        return new BaseResponse<>(slicedFeeds);
    }

    @GetMapping("{feedId}")
    public BaseResponse<GetFeedDetailsRes> getFeedDetails(@PathVariable("feedId") Long feedId) {
        Long userId = jwtService.getUserId();
        GetFeedDetailsRes feedDetails = feedService.getFeedDetails(feedId, userId);
        return new BaseResponse<>(feedDetails);
    }

    @PatchMapping("{feedId}")
    public BaseResponse editFeed(
            @PathVariable("feedId") Long feedId,
            @RequestBody PatchFeedReq patchFeedReq
        ) {
        Long userId = jwtService.getUserId();
        feedService.modifyFeed(patchFeedReq, feedId, userId);
        return new BaseResponse("수정 완료");
    }

    @PatchMapping("{feedId}/inactive")
    public BaseResponse deleteFeed(@PathVariable("feedId") Long feedId) {
        Long userId = jwtService.getUserId();
        feedService.deleteFeed(feedId, userId);
        return new BaseResponse("삭제 완료");
    }

    @PostMapping("{feedId}/likes")
    public BaseResponse<PostFeedLikeRes> likeFeed(@PathVariable("feedId") Long feedId) {
        Long userId = jwtService.getUserId();
        PostFeedLikeRes postFeedLikeRes = feedService.likeFeed(feedId, userId);
        return new BaseResponse(postFeedLikeRes);
    }



}
