package com.example.demo.src.feed.service;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.feed.entity.Comment;
import com.example.demo.src.feed.entity.Feed;
import com.example.demo.src.feed.model.GetCommentRes;
import com.example.demo.src.feed.model.PatchCommentReq;
import com.example.demo.src.feed.model.PostCommentReq;
import com.example.demo.src.feed.repository.CommentRepository;
import com.example.demo.src.feed.repository.FeedRepository;
import com.example.demo.src.user.UserRepository;
import com.example.demo.src.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final FeedRepository feedRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createComment(PostCommentReq req, Long feedId, Long userId) {
        User user = userRepository.getReferenceById(userId);
        Feed feed = feedRepository.findActiveFeedById(feedId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_FEED));

        commentRepository.save(
                Comment.builder()
                        .content(req.getContent())
                        .feed(feed)
                        .user(user)
                        .build()
        );
    }

    public List<GetCommentRes> getCommentsAtFeedList(Long feedId) {
        return commentRepository.findWithUserByFeedIdIfCountUnder(feedId, 3)
                .stream()
                .map(GetCommentRes::new)
                .collect(Collectors.toList());
    }

    public Slice<GetCommentRes> getCommentsSliced(Long feedId, Pageable pageable) {
        return commentRepository.findVisibleCommentsByFeedId(feedId, pageable)
                .map(GetCommentRes::new);
    }

    public int countVisibleByFeedId(Long feedId) {
        return commentRepository.countVisibleCommentsByFeedId(feedId);
    }

    @Transactional
    public void deleteByFeedId(Long feedId) {
        List<Comment> comments = commentRepository.findAllActiveCommentsByFeedId(feedId);
        comments.stream().forEach(comment -> comment.delete());
    }

    @Transactional
    public void modifyComment(PatchCommentReq patchCommentReq, Long feedId, Long userId) {
        Comment comment = commentRepository.findVisibleCommentById(patchCommentReq.getCommentId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_COMMENT));
        Long commentUserId = comment.getUser().getId();
        Long commentFeedId = comment.getFeed().getId();
        if (!commentUserId.equals(userId) || !commentFeedId.equals(feedId)) {
            throw new BaseException(BaseResponseStatus.NOT_AUTHORIZED_COMMENT);
        }

        comment.updateContent(patchCommentReq.getContent());
    }

    public void deleteComment(Long commentId, Long feedId,Long userId) {
        Comment comment = commentRepository.findVisibleCommentById(commentId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_COMMENT));

        Long commentUserId = comment.getUser().getId();
        Long commentFeedId = comment.getFeed().getId();
        if (!commentUserId.equals(userId) || !commentFeedId.equals(feedId)) {
            throw new BaseException(BaseResponseStatus.NOT_AUTHORIZED_COMMENT);
        }

        comment.delete();
    }
}
