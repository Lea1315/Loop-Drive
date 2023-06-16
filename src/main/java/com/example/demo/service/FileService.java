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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
    @Autowired
    private EmailSenderService senderService;

    public String generateLinkURL() {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String num = "0123456789";
        String combination = upper + lower + num;
        int len = 12;
        String url = new String();
        Random r = new Random();
        for(int i = 0; i < len; i++) {
            url += combination.charAt(r.nextInt(combination.length()));
        }
        return url;
    }
    public User getLoggedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        if(user == null) return null;
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
        String fileTitle = title + "." + file.getOriginalFilename().split("\\.")[1];
        File newFile = new File(fileTitle, description, expiry, maxDownload);
        newFile.setPublicFile(publicFile);
        newFile.setData(file.getBytes());
        newFile.setFileType(file.getContentType());
        newFile.setDownloadNumber(0);
        newFile.setLink(generateLinkURL());
        fileRepository.save(newFile);

        if(groupsId != null) {
            for (Integer id : groupsId) {
                var fileGroup = new FileGroup(newFile.getId(), id);
                fileGroupRepository.save(fileGroup);
                //NAĐI SVE LJUDE IZ GRUPA I SALJI MAIL
                var users = userGroupRepository.findUsersByGroup(id);
                for(UserGroup userFromGroup : users) {
                    var userFromGroupId = userFromGroup.getUser_id();
                    senderService.sendEmail(userRepository.findById(userFromGroupId).get().getEmail(), "File name: " + newFile.getTitle(), "New file has been shared with you");
                }
            }
        }

        if(usersId != null) {
            for(Integer id : usersId) {
                var fileUser = new FileUser(newFile.getId(), id);
                fileUserRepository.save(fileUser);
                //ŠALJI MAILOVE
                senderService.sendEmail(userRepository.findById(id).get().getEmail(), "File name: " + newFile.getTitle(), "New file has been shared with you");
            }
        }

        FileLog fileLog = new FileLog(newFile.getId(), LocalDateTime.now(), getLoggedUser().getId());
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
        if(fileLog == null) throw new RuntimeException("This file doesn't exist");
        if(!getLoggedUser().getId().equals(fileLog.getUploadUser())) throw new RuntimeException("You can't update this file");
        Optional<File> file = fileRepository.findById(fileId);
        if(file.isPresent()) {
            if(fileGroupRepository.findByFileGroup(fileId, groupId) == (null)) {
                FileGroup fg = new FileGroup(fileId, groupId);
                fileGroupRepository.save(fg);
                var users = userGroupRepository.findUsersByGroup(groupId);
                for(UserGroup userFromGroup : users) {
                    var userFromGroupId = userFromGroup.getUser_id();
                    senderService.sendEmail(userRepository.findById(userFromGroupId).get().getEmail(), "File name: " + fileRepository.findById(fileId).get().getTitle(), "New file has been shared with you");
                }
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
                senderService.sendEmail(userRepository.findById(userId).get().getEmail(), "File name: " + fileRepository.findById(fileId).get().getTitle(), "New file has been shared with you");
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

    public void upisiLogUBazu(Integer fileId) {
        Optional<FileLog> fl = Optional.ofNullable(fileLogRepository.findByFileId(fileId));
        User user = getLoggedUser();
        FileLog fileLog = new FileLog();
        fileLog.setFileId(fileId);
        fileLog.setDownloadDate(LocalDateTime.now());
        if(user != null) fileLog.setDownloadUser(user.getId());
        else fileLog.setDownloadUser(null);
        fileLog.setUploadUser(fl.get().getUploadUser());
        fileLog.setUploadDate(fl.get().getUploadDate());
        fileLogRepository.save(fileLog);
    }

    public ResponseEntity<byte[]> downloadFile(Integer fileId) {
        Optional<File> fileDB = fileRepository.findById(fileId);
        if(!fileDB.isPresent()) throw new RuntimeException("This file doesn't exist!");
        if(fileDB.get().getDownloadNumber().equals(fileDB.get().getMaxDownload())) {
            //PREKO TASK SCHEDULERA URADITI
            fileRepository.deleteById(fileDB.get().getId());
            throw new RuntimeException("This file reached download maximum!");
        }
        if(fileDB.get().isPublicFile() || getLoggedUser().getRole().equals(1)) {
            fileDB.get().setDownloadNumber((fileDB.get().getDownloadNumber()) + 1);
            fileRepository.save(fileDB.get());

            upisiLogUBazu(fileDB.get().getId());
        }
        else {
            var listOfFiles = getFilesForLoggedInUser();
            if (!listOfFiles.stream().anyMatch(element -> element.getId().equals(fileId))) throw new RuntimeException("You cannot download this file!");

            fileDB.get().setDownloadNumber((fileDB.get().getDownloadNumber()) + 1);
            fileRepository.save(fileDB.get());

            upisiLogUBazu(fileDB.get().getId());
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileDB.get().getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.get().getTitle() + "\"")
                .body(fileDB.get().getData());
    }

    public ResponseEntity<byte[]> linkDownload(String url) {
        File file = fileRepository.findFileByLink(url);
        upisiLogUBazu(file.getId());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getTitle() + "\"")
                .body(file.getData());
    }
}
