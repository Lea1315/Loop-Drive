package com.example.demo.api;

import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api")
public class FileController {
    @Autowired
    private FileService fileService;
    @PostMapping(value = "/file-upload", consumes = "multipart/form-data")
    public void uploadFile(@RequestParam String title,
                           @RequestParam(required = false) String description,
                           @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") Date expiry,
                           @RequestParam Integer maxDownload,
                           @RequestParam(required = false) List<Integer> groupsId,
                           @RequestParam(required = false) List<Integer> usersId,
                           @RequestParam MultipartFile file) throws IOException {
        fileService.uploadFile(title, description, expiry, maxDownload, groupsId, usersId, file);
    }
    @DeleteMapping("/files")
    public void deleteFile(@RequestParam Integer id) {
        fileService.deleteFile(id);
    }

    @PutMapping("/files")
    public void updateFile( @RequestParam Integer fileId,
                            @RequestParam String title,
                            @RequestParam(required = false) String description,
                            @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") Date expiry,
                            @RequestParam Integer maxDownload) {
        fileService.updateFile(fileId, title, description, expiry, maxDownload);
    }

    @PostMapping("files/add-group")
    public void addGroupInFile(@RequestParam Integer fileId, @RequestParam Integer groupId) {
        fileService.addGroupInFile(fileId, groupId);
    }

    @DeleteMapping("files/delete-group")
    public void deleteGroupInFile(@RequestParam Integer fileId, @RequestParam Integer groupId) {
        fileService.deleteGroupInFile(fileId, groupId);
    }

    @PostMapping("files/add-user")
    public void addUserInFile(@RequestParam Integer fileId, @RequestParam Integer userId) {
        fileService.addUserInFile(fileId, userId);
    }

    @DeleteMapping("files/delete-user")
    public void deleteUserInFile(@RequestParam Integer fileId, @RequestParam Integer userId) {
        fileService.deleteUserInFile(fileId, userId);
    }
}
