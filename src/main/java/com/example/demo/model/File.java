package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
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
    private Date expiry;
    @Column(name = "maxDownload")
    private Integer maxDownload;
    @Lob
    @JdbcTypeCode(Types.BINARY)
    @Column(name = "uploadFile")
    private byte[] data;

    public File() {}
    public File(String title, String description, Date expiry, Integer maxDownload) {
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
