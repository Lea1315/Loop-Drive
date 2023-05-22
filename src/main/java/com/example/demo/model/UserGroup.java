package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_group")
public class UserGroup {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Id
    private Integer id;
    @Column(name = "user_id")
    private Integer user_id;
    @Column(name = "group_id")
    private Integer group_id;

    public UserGroup() {}
    public UserGroup(Integer user_id, Integer group_id) {
        this.user_id = user_id;
        this.group_id = group_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Integer group_id) {
        this.group_id = group_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
