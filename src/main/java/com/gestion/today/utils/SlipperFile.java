package com.gestion.today.utils;

import com.gestion.today.service.interfaces.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class SlipperFile {

    private final FileService fileService;
    @Value("${project.poster}")
    private String  pathFile;

    public String uploadImage(MultipartFile file) throws IOException {
        return fileService.uploadFile(pathFile, file);

    }


}
