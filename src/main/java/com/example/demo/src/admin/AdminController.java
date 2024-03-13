package com.example.demo.src.admin;

import com.example.demo.src.feed.FeedService;
import com.example.demo.src.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final FeedService feedService;
}
