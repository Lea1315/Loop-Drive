package com.example.demo.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Id
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "role")
    private Integer role;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private boolean active;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_group",
    joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id")
    },
    inverseJoinColumns = {
            @JoinColumn(name = "group_id", referencedColumnName = "id")
    }
    )
    private List<Group> groups;

    public User(String username, Integer role, String email) {
        this.username = username;
        this.role = role;
        this.email = email;
    }

    public User() {}

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

   /* public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
    */

}
