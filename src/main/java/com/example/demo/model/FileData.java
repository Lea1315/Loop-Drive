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
}
