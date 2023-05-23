package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "file_users")
public class FileUser {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Id
    private Integer id;
    @Column(name = "file_id")
    private Integer file_id;
    @Column(name = "user_id")
    private Integer user_id;

    public FileUser(Integer file_id, Integer user_id) {
        this.file_id = file_id;
        this.user_id = user_id;
    }

    public FileUser() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFile_id() {
        return file_id;
    }

    public void setFile_id(Integer file_id) {
        this.file_id = file_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
