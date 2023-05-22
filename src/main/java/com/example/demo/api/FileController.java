package com.example.demo.api;

import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api")
public class FileController {
    @Autowired
    private FileService fileService;
    @PostMapping(value = "/file-upload", consumes = "multipart/form-data")
    public void uploadFile(//@RequestBody UploadFile uploadFile,

                           @RequestParam MultipartFile file) throws IOException {
        fileService.uploadFile(null, file);
    }
}
