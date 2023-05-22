package com.example.demo.service;

import com.example.demo.model.File;
import com.example.demo.model.FileGroup;
import com.example.demo.model.Group;
import com.example.demo.model.UserGroup;
import com.example.demo.repo.FileGroupRepository;
import com.example.demo.repo.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private FileGroupRepository fileGroupRepository;
    public void uploadFile(String title,
                           String description,
                           Date expiry,
                           Integer maxDownload,
                           List<Integer> groupsId,
                           List<Integer> usersId,
                           MultipartFile file) throws IOException {
        File newFile = new File(title, description, expiry, maxDownload);
        newFile.setData(file.getBytes());
        fileRepository.save(newFile);

        if(groupsId != null) {
            for (Integer id : groupsId) {
                var fileGroup = new FileGroup(newFile.getId(), id);
                fileGroupRepository.save(fileGroup);
            }
        }
    }

    public void deleteFile(Integer id) {
        fileRepository.deleteById(id);
    }


    public void updateFile(Integer id, String title, String description, Date expiry, Integer max) {
        Optional<File> file = fileRepository.findById(id);
        if(file.isPresent()) {
            file.get().setTitle(title);
            file.get().setDescription(description);
            file.get().setExpiry(expiry);
            file.get().setMaxDownload(max);
            fileRepository.save(file.get());
        }
        else throw new RuntimeException("This file doesn't exist!");
    }

    public void addGroupInFile(Integer fileId, Integer groupId) {
        Optional<File> file = fileRepository.findById(fileId);
        if(file.isPresent()) {
            if(fileGroupRepository.findByFileGroup(fileId, groupId) == (null)) {
                FileGroup fg = new FileGroup(fileId, groupId);
                fileGroupRepository.save(fg);
            }
        }
        else throw new RuntimeException("This file doesn't exist!");
    }


    public void deleteGroupInFile(Integer fileId, Integer groupId) {
        Optional<File> file = fileRepository.findById(fileId);
        if(file.isPresent()) {
            if(fileGroupRepository.findByFileGroup(fileId, groupId) != (null)) {
                fileGroupRepository.deleteByFileGroupId(fileId, groupId);
            }
            else throw new RuntimeException("This group is not allowed to download this file!");
        }
        else throw new RuntimeException("This file doesn't exist!");
    }
}
