package com.g11.FresherManage.service.impl;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.g11.FresherManage.exception.file.FileEmptyException;
import com.g11.FresherManage.exception.file.FileException;
import com.g11.FresherManage.exception.file.FileLocationException;
import com.g11.FresherManage.configs.FilePropertiesConfig;
import com.g11.FresherManage.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;



@Service
public class FileServiceImpl implements FileService {

    private final Path rootLocation;

    @Autowired
    public FileServiceImpl(FilePropertiesConfig properties) {

        if(properties.getLocation().trim().isEmpty()){
            throw new FileLocationException("Not found upload location");
        }

        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new FileEmptyException();
            }

            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new FileLocationException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Path uniqueDestinationFile = getUniqueDestinationFile(destinationFile);
                Files.copy(inputStream, uniqueDestinationFile,
                        StandardCopyOption.REPLACE_EXISTING);

            }
        } catch (IOException e) {
            throw new FileException(500, "INTERNAL_SERVER_ERROR", "Failed to store file.\n" + e);
        }

    }


    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new FileException(500, "INTERNAL_SERVER_ERROR", "Could not initialize storage:\n " + e.getMessage());
        }
    }

    @Override
    public Path getUniqueDestinationFile(Path destinationFile) {
        Path uniquePath = destinationFile;
        int count = 1;
        while (Files.exists(uniquePath)) {
            String filename = destinationFile.getFileName().toString();
            String baseName = FilenameUtils.getBaseName(filename);
            String extension = FilenameUtils.getExtension(filename);

            String uniqueFilename = baseName + "(" + count + ")." + extension;
            uniquePath = destinationFile.resolveSibling(uniqueFilename);
            count++;
        }
        return uniquePath;
    }


    @Override
    public boolean isImage(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {

            // Đọc dữ liệu hình ảnh và video từ InputStream
            BufferedImage image = ImageIO.read(inputStream);
            if (image == null) {
                return false;
            }
            // Kiểm tra xem image có chứa dữ liệu hợp lệ hay không
            // Trong trường hợp này, chúng ta chỉ muốn đảm bảo rằng image/video không rỗng
            return image.getWidth() > 0 && image.getHeight() > 0;
        }
        catch (IOException e) {
            return false;
        }
    }



    @Override
    public String getPhotoURL(String fileName) {
        return "https://awesome-debt-production.up.railway.app/" + fileName;
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new FileException(500, HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new FileException(500, "INTERNAL_SERVER_ERROR",
                    "Could not read file: " + filename + "\n" + e);
        }
    }
}