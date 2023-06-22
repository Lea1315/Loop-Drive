package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface FileLogData {

    LocalDateTime getUploadDate();

    String getTitle();

    String getUploadUser();


    LocalDateTime getDownloadDate();


    String getDownloadUser();


}
