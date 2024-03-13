package com.example.demo.src.feed;

import com.example.demo.src.feed.repository.CommentRepository;
import com.example.demo.src.feed.repository.FeedRepository;
import com.example.demo.src.feed.repository.FilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;
    private final CommentRepository commentRepository;
    private final FilesRepository filesRepository;
}

