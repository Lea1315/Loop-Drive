package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.Date;

public class FileData {

    private Integer id;
    private String title;
    private String description;
    private Date expiry;
    private Integer maxDownload;
    private byte[] data;
    public FileData() {}

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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
