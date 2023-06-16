package com.example.demo.schedules;

import com.example.demo.model.File;
import com.example.demo.repo.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ScheduledTasks {
    @Autowired
    FileRepository fileRepository;


    @Scheduled(cron="0 15 8 ? * *")
    public void eraseExpiredFiles() {
        List<File> fileList =  fileRepository.findAll();
        fileList.forEach(file -> {
            if(file.getExpiry().isBefore(LocalDate.now())) fileRepository.deleteById(file.getId());
        });
    }

    @Scheduled(cron="0 15 10 ? * *")
    public void eraseMaxDownloadedFiles() {
        List<File> fileList =  fileRepository.findAll();
        fileList.forEach(file -> {
            if(file.getDownloadNumber().equals(file.getMaxDownload())) fileRepository.deleteById(file.getId());
        });
    }
}
