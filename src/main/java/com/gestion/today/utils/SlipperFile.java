package com.gestion.today.utils;

import com.gestion.today.service.interfaces.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class SlipperFile {

    private final FileService fileService;
    @Value("${project.poster}")
    private String  pathFile;

    public String uploadImage(MultipartFile file, String codToday) throws IOException {
        return fileService.uploadFile(pathFile,file, codToday);

    }

    public String updateImage(MultipartFile file, String existingFileName, String codToday) throws  IOException{

        String existingFilePath = pathFile + File.separator + existingFileName;
        File existingFile = new File(existingFilePath);
        if (existingFile.exists()){
            if (!existingFile.delete()){
                throw new IOException("No se pudo eliminar el archivo existente");
            }
        }
        return fileService.uploadFile(pathFile,file,codToday);

    }


}
