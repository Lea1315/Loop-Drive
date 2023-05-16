package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {
    @Column(name = "id")
    @Id
    private Integer id;

    @Column(name = "name")
    private String username;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
