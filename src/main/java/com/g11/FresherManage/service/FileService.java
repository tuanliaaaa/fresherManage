package com.g11.FresherManage.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileService {

    void init();

    Path load(String filename);

    void store(MultipartFile file);


    Path getUniqueDestinationFile(Path destinationFile);

    boolean isImage(MultipartFile file);

    String getPhotoURL(String fileName);

    Resource loadAsResource(String filename);
}