package com.example.demo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "file_log")
public class FileLog {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Id
    private Integer id;
    @Column(name = "file_id")
    private Integer fileId;
    @Column(name = "upload_date")
    private LocalDateTime uploadDate;
    @Column(name = "download_date")
    private LocalDateTime downloadDate;
    @Column(name = "upload_user")
    private Integer uploadUser;
    @Column(name = "download_user")
    private Integer downloadUser;

    public FileLog() {}

    public FileLog(Integer fileId, LocalDateTime uploadDate, Integer uploadUser) {
        this.fileId = fileId;
        this.uploadDate = uploadDate;
        this.uploadUser = uploadUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public LocalDateTime getDownloadDate() {
        return downloadDate;
    }

    public void setDownloadDate(LocalDateTime downloadDate) {
        this.downloadDate = downloadDate;
    }

    public Integer getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(Integer uploadUser) {
        this.uploadUser = uploadUser;
    }

    public Integer getDownloadUser() {
        return downloadUser;
    }

    public void setDownloadUser(Integer downloadUser) {
        this.downloadUser = downloadUser;
    }
}
