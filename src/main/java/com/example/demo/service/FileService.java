package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repo.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private FileGroupRepository fileGroupRepository;
    @Autowired
    private FileUserRepository fileUserRepository;
    @Autowired
    private UserGroupRepository userGroupRepository;
    @Autowired
    private FileLogRepository fileLogRepository;
    @Autowired
    private UserRepository userRepository;

    public User getLoggedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        if(user == null) throw new RuntimeException("You are not logged in");
        return user;
    }
    public void uploadFile(String title,
                           String description,
                           LocalDate expiry,
                           Integer maxDownload,
                           List<Integer> groupsId,
                           List<Integer> usersId,
                           MultipartFile file,
                           boolean publicFile) throws IOException {
        //validacija podataka
        if(maxDownload < 1) throw new RuntimeException("Max download number incorrect!");
        if(expiry.isBefore(LocalDate.now())) throw new RuntimeException("Expiry date is older than today");
        File newFile = new File(title, description, expiry, maxDownload);
        newFile.setPublicFile(publicFile);
        newFile.setData(file.getBytes());
        newFile.setFileType(file.getContentType());
        newFile.setDownloadNumber(0);
        fileRepository.save(newFile);

        if(groupsId != null) {
            for (Integer id : groupsId) {
                var fileGroup = new FileGroup(newFile.getId(), id);
                fileGroupRepository.save(fileGroup);
            }
        }

        if(usersId != null) {
            for(Integer id : usersId) {
                var fileUser = new FileUser(newFile.getId(), id);
                fileUserRepository.save(fileUser);
            }
        }

        FileLog fileLog = new FileLog(newFile.getId(), LocalDate.now(), getLoggedUser().getId());
        fileLogRepository.save(fileLog);
    }

    public void deleteFile(Integer id) {
        FileLog file = fileLogRepository.findByFileId(id);
        if(getLoggedUser().getId().equals(file.getUploadUser())) {
            fileLogRepository.deleteByFileId(id);
            fileRepository.deleteById(id);
        }
        else throw new RuntimeException("You can't update this file!");
    }


    public void updateFile(Integer id, String title, String description, LocalDate expiry, Integer max) {
        FileLog fileLog = fileLogRepository.findByFileId(id);
        if(!getLoggedUser().getId().equals(fileLog.getUploadUser())) throw new RuntimeException("You can't update this file");
        Optional<File> file = fileRepository.findById(id);
        if(file.isPresent()) {
            if(title != null) file.get().setTitle(title);
            if(description != null) file.get().setDescription(description);
            if(expiry != null) {
                if(expiry.isBefore(LocalDate.now())) throw new RuntimeException("Expiry date is older than today!");
                file.get().setExpiry(expiry);
            }
            if(max != null) {
                if(max < 1) throw new RuntimeException("Max download number incorrect!");
                file.get().setMaxDownload(max);
            }
            fileRepository.save(file.get());
        }
        else throw new RuntimeException("This file doesn't exist!");
    }

    public void addGroupInFile(Integer fileId, Integer groupId) {
        FileLog fileLog = fileLogRepository.findByFileId(fileId);
        if(!getLoggedUser().getId().equals(fileLog.getUploadUser())) throw new RuntimeException("You can't update this file");
        Optional<File> file = fileRepository.findById(fileId);
        if(file.isPresent()) {
            if(fileGroupRepository.findByFileGroup(fileId, groupId) == (null)) {
                FileGroup fg = new FileGroup(fileId, groupId);
                fileGroupRepository.save(fg);
            }
            else throw new RuntimeException("This group is already allowed to download this file!");
        }
        else throw new RuntimeException("This file doesn't exist!");
    }


    public void deleteGroupInFile(Integer fileId, Integer groupId) {
        FileLog fileLog = fileLogRepository.findByFileId(fileId);
        if(!getLoggedUser().getId().equals(fileLog.getUploadUser())) throw new RuntimeException("You can't update this file");
        Optional<File> file = fileRepository.findById(fileId);
        if(file.isPresent()) {
            if(fileGroupRepository.findByFileGroup(fileId, groupId) != (null)) {
                fileGroupRepository.deleteByFileGroupId(fileId, groupId);
            }
            else throw new RuntimeException("This group is already not allowed to download this file!");
        }
        else throw new RuntimeException("This file doesn't exist!");
    }

    public void addUserInFile(Integer fileId, Integer userId) {
        FileLog fileLog = fileLogRepository.findByFileId(fileId);
        if(!getLoggedUser().getId().equals(fileLog.getUploadUser())) throw new RuntimeException("You can't update this file");
        Optional<File> file = fileRepository.findById(fileId);
        if(file.isPresent()) {
            if(fileUserRepository.findByFileUser(fileId, userId) == (null)) {
                FileUser fu = new FileUser(fileId, userId);
                fileUserRepository.save(fu);
            }
            else throw new RuntimeException("This user is already allowed to download this file!");
        }
        else throw new RuntimeException("This file doesn't exist!");
    }

    public void deleteUserInFile(Integer fileId, Integer userId) {
        FileLog fileLog = fileLogRepository.findByFileId(fileId);
        if(!getLoggedUser().getId().equals(fileLog.getUploadUser())) throw new RuntimeException("You can't update this file");
        Optional<File> file = fileRepository.findById(fileId);
        if(file.isPresent()) {
            if(fileUserRepository.findByFileUser(fileId, userId) != (null)) {
                fileUserRepository.deleteByFileUserId(fileId, userId);
            }
            else throw new RuntimeException("This user is already not allowed to download this file!");
        }
        else throw new RuntimeException("This file doesn't exist!");
    }

    public List<File>getFilesForLoggedInUser() {
        var listOfId = fileUserRepository.findFilesByUser(getLoggedUser().getId());
        List<File> files = new ArrayList<>();
        listOfId.forEach(element -> {
            files.add(fileRepository.findById(element.getFile_id()).get());
        });

        var listOfGroupsId = userGroupRepository.findGroupsByUser(getLoggedUser().getId());
        listOfGroupsId.forEach(element -> {
            var listOfFileId = fileGroupRepository.findFilesByGroup(element.getGroup_id());
            listOfFileId.forEach(f -> files.add(fileRepository.findById(f.getFile_id()).get()));

        });
        return files;
    }

    public List<FileData> getFiles() {
        ModelMapper modelMapper = new ModelMapper();
        var files = getFilesForLoggedInUser();
        List<FileData> fileDataList = modelMapper.map(files, new TypeToken<List<FileData>>() {}.getType());

        return fileDataList;
    }

    public ResponseEntity<byte[]> downloadFile(Integer fileId) {
        Optional<File> fileDB = fileRepository.findById(fileId);
        if(!fileDB.isPresent()) throw new RuntimeException("This file doesn't exist!");
        if(fileDB.get().getDownloadNumber().equals(fileDB.get().getMaxDownload())) {
            fileRepository.deleteById(fileDB.get().getId());
            throw new RuntimeException("This file reached download maximum!");
        }
        fileDB.get().setDownloadNumber((fileDB.get().getDownloadNumber()) + 1);

        fileRepository.save(fileDB.get());
        var listOfFiles = getFilesForLoggedInUser();
        if(!listOfFiles.stream().anyMatch(element -> element.getId().equals(fileId))) throw new RuntimeException("You cannot download this file!");
        Optional<FileLog> fl = Optional.ofNullable(fileLogRepository.findByFileId(fileDB.get().getId()));
        FileLog fileLog = new FileLog();
        fileLog.setFileId(fileDB.get().getId());
        fileLog.setDownloadDate(LocalDate.now());
        fileLog.setDownloadUser(getLoggedUser().getId());
        fileLog.setFileId(fl.get().getFileId());
        fileLog.setUploadUser(fl.get().getUploadUser());
        fileLog.setUploadDate(fl.get().getUploadDate());
        fileLogRepository.save(fileLog);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileDB.get().getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.get().getTitle() + "\"")
                .body(fileDB.get().getData());
    }
}
