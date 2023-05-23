package com.example.demo.model;

import java.util.List;

public class GroupData {

    private Integer id;

    private String name;

    private List<UserData> users;

    public GroupData() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserData> getUsers() {
        return users;
    }

    public void setUsers(List<UserData> users) {
        this.users = users;
    }
}
