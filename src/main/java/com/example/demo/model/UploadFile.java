package com.example.demo.model;

import jakarta.persistence.Column;

import java.util.Date;

public class UploadFile {
    private String title;
    private String description;
    private Date expiry;
    private Integer maxDownload;

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

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public Integer getMaxDownload() {
        return maxDownload;
    }

    public void setMaxDownload(Integer maxDownload) {
        this.maxDownload = maxDownload;
    }
}
