package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "file")
public class File {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Id
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "expiry")
    private LocalDate expiry;
    @Column(name = "maxDownload")
    private Integer maxDownload;
    @Lob
    @JdbcTypeCode(Types.BINARY)
    @Column(name = "uploadFile")
    private byte[] data;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "public")
    private boolean publicFile;

    @Column(name = "download_number")
    private Integer downloadNumber;

    @Column(name = "link")
    private String link;

    public File() {}
    public File(String title, String description, LocalDate expiry, Integer maxDownload) {
        this.title = title;
        this.description = description;
        this.expiry = expiry;
        this.maxDownload = maxDownload;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getExpiry() {
        return expiry;
    }

    public void setExpiry(LocalDate expiry) {
        this.expiry = expiry;
    }

    public Integer getMaxDownload() {
        return maxDownload;
    }

    public void setMaxDownload(Integer maxDownload) {
        this.maxDownload = maxDownload;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public boolean isPublicFile() {
        return publicFile;
    }

    public void setPublicFile(boolean publicFile) {
        this.publicFile = publicFile;
    }

    public Integer getDownloadNumber() {
        return downloadNumber;
    }

    public void setDownloadNumber(Integer downloadNumber) {
        this.downloadNumber = downloadNumber;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
