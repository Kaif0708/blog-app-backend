package com.blog_app.blog_app_apis.services.impl;

import com.blog_app.blog_app_apis.services.FileService;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        return "";
    }

    @Override
    public InputStream getResource(String path, String filename) throws FileNotFoundException {
        return null;
    }
}
