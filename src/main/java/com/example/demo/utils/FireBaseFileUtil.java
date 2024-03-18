package com.example.demo.utils;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class FireBaseFileUtil implements FileUtil {
    private final Bucket bucket;
    @Override
    public String upload(MultipartFile file, String namePrefix) {
        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String filename = namePrefix + System.currentTimeMillis() + ext;
        try {
            InputStream content = new ByteArrayInputStream(file.getBytes());
            Blob blob = bucket.create(filename, content, file.getContentType());
            return filename;
        } catch (IOException e) {
            log.error("업로드 실패 : {}", filename);
            throw new BaseException(BaseResponseStatus.UPLOAD_FAIL_MEDIA_CONTENT);
        }
    }

    @Override
    public String getUrl(String dir) {
        return bucket.get(dir).signUrl(120L, TimeUnit.MINUTES).toString();
    }
}
