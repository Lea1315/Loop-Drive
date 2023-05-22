package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "file_groups")
public class FileGroup {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Id
    private Integer id;
    @Column(name = "file_id")
    private Integer file_id;
    @Column(name = "group_id")
    private Integer group_id;

    public FileGroup(Integer fileId, Integer groupId) {
        this.file_id = fileId;
        this.group_id = groupId;
    }
    public FileGroup() {}

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

    public Integer getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Integer group_id) {
        this.group_id = group_id;
    }
}
