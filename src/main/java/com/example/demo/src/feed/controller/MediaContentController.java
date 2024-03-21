package com.example.demo.src.feed.controller;

import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.feed.model.PostMediaContentRes;
import com.example.demo.src.feed.service.MediaContentService;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("app/media")
public class MediaContentController {
    private final JwtService jwtService;
    private final MediaContentService mediaContentService;

    @PostMapping
    public BaseResponse<List<PostMediaContentRes>> uploadMedia(
            @RequestParam(value = "contents", required = true) List<MultipartFile> files
    ) {
        Long userId = jwtService.getUserId();
        List<PostMediaContentRes> postMediaContentRes = mediaContentService.uploadFiles(files, userId);
        return new BaseResponse(postMediaContentRes);
    }
}
