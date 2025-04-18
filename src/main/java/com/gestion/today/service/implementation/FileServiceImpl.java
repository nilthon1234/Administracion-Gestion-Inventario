package com.gestion.today.service.implementation;

import com.gestion.today.service.interfaces.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile(String path, MultipartFile file, String codToday) throws IOException {


        String fileName = file.getOriginalFilename();
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));

        String newFileName = codToday + fileExtension;
        String filePath =path + File.separator + newFileName;

        File f =new File(path);
        if (!f.exists()){
            f.mkdir();
        }
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return newFileName;
    }

    @Override
    public InputStream getResourceFile(String path, String filename) throws FileNotFoundException {

        String filePath =path +File.separator +filename;

        return new FileInputStream(filePath);
    }
}
