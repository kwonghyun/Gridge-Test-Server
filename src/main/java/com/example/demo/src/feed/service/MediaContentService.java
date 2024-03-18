package com.example.demo.src.feed.service;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.feed.entity.Feed;
import com.example.demo.src.feed.entity.MediaConnection;
import com.example.demo.src.feed.entity.MediaContent;
import com.example.demo.src.feed.model.GetMediaRes;
import com.example.demo.src.feed.model.PostFeedMediaReq;
import com.example.demo.src.feed.model.PostMediaContentRes;
import com.example.demo.src.feed.repository.MediaConnectionRepository;
import com.example.demo.src.feed.repository.MediaContentRepository;
import com.example.demo.src.user.UserRepository;
import com.example.demo.src.user.entity.User;
import com.example.demo.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MediaContentService {
    private final FileUtil fileUtil;
    private final MediaContentRepository mediaContentRepository;
    private final MediaConnectionRepository mediaConnectionRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<PostMediaContentRes> uploadFiles(List<MultipartFile> multipartFiles, Long userId) {
        if (multipartFiles == null || multipartFiles.size() == 0) {
            return new ArrayList<>();
        } else if (multipartFiles.size() > 10) {
            throw new BaseException(BaseResponseStatus.TOO_MANY_MEDIA_CONTENT);
        }
        User user = userRepository.findActiveUserById(userId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_USER));
        String loginId = user.getLoginId();
        String name = user.getName();
        String namePrefix = name + "/" + loginId + "/" + name + "_";
        List<MediaContent> media = mediaContentRepository.saveAll(
                multipartFiles.stream()
                        .map(file -> {

                            String savedDir = fileUtil.upload(file, namePrefix);
                            return createMediaContentFrom(file, savedDir);
                        }).collect(Collectors.toList())
        );

        return media.stream()
                .map(mediaContent -> {
                    String url = getUrlByMediaContentId(mediaContent.getId());
                    return new PostMediaContentRes(mediaContent, url);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void createMediaConnections(Feed feed, List<PostFeedMediaReq> reqs) {
        if (reqs.size() > 10) {
            throw new BaseException(BaseResponseStatus.TOO_MANY_MEDIA_CONTENT);
        } else if (reqs == null || reqs.isEmpty()) {
            return;
        }
        List<MediaConnection> connections = reqs.stream()
                .map(req -> {
                    Long mediaContentId = req.getMediaContentId();
                    if (!mediaContentRepository.existsByIdAndState(mediaContentId)) {
                        throw new BaseException(BaseResponseStatus.NOT_FIND_MEDIA_CONTENT);
                    }
                    MediaContent mediaContent = mediaContentRepository.getReferenceById(mediaContentId);

                    return MediaConnection.builder()
                            .mediaContent(mediaContent)
                            .displayOrder(req.getOrder())
                            .feed(feed)
                            .build();
                }).collect(Collectors.toList());

        mediaConnectionRepository.saveAll(connections);
    }

    public String getUrlByMediaContentId(Long mediaContentId) {
        MediaContent mediaContent = mediaContentRepository.findActiveContentById(mediaContentId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_MEDIA_CONTENT));
        String dir = mediaContent.getDir();
        return fileUtil.getUrl(dir);
    }

    public List<GetMediaRes> getMediaByFeedId(Long feedId) {
        List<MediaConnection> media = mediaConnectionRepository.findAllActiveMediaByFeedId(feedId);
        return media.stream()
                .map(mediaConnection -> {
                    Long mediaContentId = mediaConnection.getMediaContent().getId();
                    return new GetMediaRes(mediaConnection, getUrlByMediaContentId(mediaContentId));
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteByFeedId(Long feedId) {
        List<MediaConnection> media = mediaConnectionRepository.findAllActiveMediaByFeedId(feedId);
        media.stream().forEach(mediaConnection -> {
            mediaConnection.delete();
            mediaConnection.getMediaContent().delete();
        });
    }

    public MediaContent createMediaContentFrom(MultipartFile file , String savedDir) {
        String originalFilenameWithExt = file.getOriginalFilename();

        String[] fileNameSplit = originalFilenameWithExt.split("\\.");

        String originalFilename = originalFilenameWithExt
                .substring(0, originalFilenameWithExt.lastIndexOf("."));

        String ext = fileNameSplit[fileNameSplit.length - 1];
        MediaContent.Type type = MediaContent.Type.getByExtension(ext);
        if (type == null) {
            throw new BaseException(BaseResponseStatus.UNSUPPORTED_MEDIA_TYPE);
        }

        return MediaContent.builder()
                .originalFileName(originalFilename)
                .extension(ext)
                .size((float) file.getSize())
                .type(type)
                .dir(savedDir)
                .build();
    }
}
