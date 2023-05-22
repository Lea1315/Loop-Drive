package com.example.demo.service;

import com.example.demo.model.File;
import com.example.demo.model.UploadFile;
import com.example.demo.repo.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;
    public void uploadFile(UploadFile uploadFile,
                           MultipartFile file) throws IOException {
        File newFile = new File(uploadFile.getTitle(), uploadFile.getDescription(), uploadFile.getExpiry(), uploadFile.getMaxDownload());
        newFile.setData(file.getBytes());
        fileRepository.save(newFile);
    }
}
