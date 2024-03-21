package com.example.demo.src.feed.controller;

import com.example.demo.common.Constant;
import com.example.demo.common.model.CustomPageable;
import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.feed.model.*;
import com.example.demo.src.report.model.*;
import com.example.demo.src.feed.service.FeedService;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/feeds")
public class FeedController {
    private final FeedService feedService;
    private final JwtService jwtService;

    @PostMapping
    public BaseResponse<FeedIdRes> createFeed(@RequestBody @Valid PostFeedReq postFeedReq) {
        Long userId = jwtService.getUserId();
        FeedIdRes feedIdRes = feedService.createFeed(postFeedReq, userId);
        return new BaseResponse<>(feedIdRes);
    }

    @GetMapping
    public BaseResponse<Slice<GetFeedRes>> getFeeds(@ModelAttribute @Valid CustomPageable pageable) {
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
    public BaseResponse<FeedIdRes> editFeed(
            @PathVariable("feedId") Long feedId,
            @RequestBody @Valid PatchFeedReq patchFeedReq
        ) {
        Long userId = jwtService.getUserId();
        FeedIdRes feedIdRes = feedService.modifyFeed(patchFeedReq, feedId, userId);
        return new BaseResponse(feedIdRes);
    }

    @PatchMapping("{feedId}/inactive")
    public BaseResponse<String> deleteFeed(@PathVariable("feedId") Long feedId) {
        Long userId = jwtService.getUserId();
        feedService.deleteFeed(feedId, userId);
        return new BaseResponse(Constant.DELETED);
    }

    @PostMapping("{feedId}/likes")
    public BaseResponse<PostFeedLikeRes> likeFeed(@PathVariable("feedId") Long feedId) {
        Long userId = jwtService.getUserId();
        PostFeedLikeRes postFeedLikeRes = feedService.likeFeed(feedId, userId);
        return new BaseResponse(postFeedLikeRes);
    }



}
