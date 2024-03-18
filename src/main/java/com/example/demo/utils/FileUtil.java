package com.example.demo.utils;

import org.springframework.web.multipart.MultipartFile;

public interface FileUtil {
    public String  upload(MultipartFile file, String namePrefix);

    public String getUrl(String dir);
}
