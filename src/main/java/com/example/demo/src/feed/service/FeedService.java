package com.example.demo.src.feed.service;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.feed.entity.Feed;
import com.example.demo.src.feed.entity.FeedLike;
import com.example.demo.src.feed.model.*;
import com.example.demo.src.report.model.*;
import com.example.demo.src.feed.repository.FeedLikeRepository;
import com.example.demo.src.feed.repository.FeedRepository;
import com.example.demo.src.user.UserRepository;
import com.example.demo.src.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;
    private final FeedLikeRepository feedLikeRepository;
    private final CommentService commentService;
    private final UserRepository userRepository;
    private final MediaContentService mediaContentService;

    @Transactional
    public FeedIdRes createFeed(PostFeedReq req, Long userId) {
        User user = userRepository.getReferenceById(userId);
        Feed feed = feedRepository.save(
                Feed.builder()
                        .content(req.getContent())
                        .user(user)
                        .build()
        );
        mediaContentService.createMediaConnections(feed, req.getMedia());
        return new FeedIdRes(feed.getId());
    }

    @Transactional
    public FeedIdRes modifyFeed(PatchFeedReq req, Long feedId, Long userId) {
        Feed feed = feedRepository.findActiveFeedById(feedId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_FEED));

        if (!feed.getUser().getId().equals(userId)) {
            throw new BaseException(BaseResponseStatus.NOT_AUTHORIZED_FEED);
        }

        feed.updateContent(req.getContent());
        return new FeedIdRes(feed.getId());
    }

    public Slice<GetFeedRes> getFeeds(Pageable pageable, Long userId) {
        User user = userRepository.getReferenceById(userId);
        Slice<Feed> feeds = feedRepository.findAllValidFeedsWithUser(pageable);
        return feeds.map(feed -> {
            Long feedId = feed.getId();
            return new GetFeedRes(
                    feed,
                    feedLikeRepository.existsLikeByFeedIdAndUserId(feedId, userId),
                    feedLikeRepository.countLikesByFeedId(feedId),
                    commentService.countVisibleByFeedId(feedId),
                    commentService.getCommentsAtFeedList(feedId),
                    mediaContentService.getMediaByFeedId(feedId)
                    );
        });
    }

    public GetFeedDetailsRes getFeedDetails(Long feedId, Long userId) {
        Feed feed = feedRepository.findValidFeedWithUserById(feedId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_FEED));

        List<GetMediaRes> media = mediaContentService.getMediaByFeedId(feedId);
        boolean liked = feedLikeRepository.existsLikeByFeedIdAndUserId(feedId, userId);
        int likesCount = feedLikeRepository.countLikesByFeedId(feedId);
        int commentsCount = commentService.countVisibleByFeedId(feedId);

        return new GetFeedDetailsRes(feed, commentsCount, likesCount, liked, media);
    }

    @Transactional
    public void deleteFeed(Long feedId, Long userId) {
        Feed feed = feedRepository.findActiveFeedById(feedId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_FEED));
        feed.delete();
        commentService.deleteByFeedId(feedId);
        mediaContentService.deleteByFeedId(feedId);
        List<FeedLike> likes = feedLikeRepository.findAllActiveLikesByFeedId(feedId);
        likes.stream().forEach(like -> like.delete());
    }

    @Transactional
    public PostFeedLikeRes likeFeed(Long feedId, Long userId) {
        Feed feed = feedRepository.getReferenceById(feedId);
        User user = userRepository.getReferenceById(userId);
        FeedLike feedLike;
        if (feedLikeRepository.existsLikeByFeedIdAndUserId(feedId, userId)) {
            feedLike = feedLikeRepository.findByFeedIdAndUserId(feedId, userId)
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_FEED_LIKE));
            feedLike.updateLiked();
        } else {
            feedLike = feedLikeRepository.save(
                    FeedLike.builder()
                            .liked(true)
                            .feed(feed)
                            .user(user)
                            .build()
            );
        }
        return new PostFeedLikeRes(feedLike);
    }

}

