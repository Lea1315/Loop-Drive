package com.example.demo.api;

import com.example.demo.model.GroupData;
import com.example.demo.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api")
@RestController
public class GroupController {
    @Autowired
    private GroupService groupService;
    @PostMapping("/groups")
    public void addGroup(@RequestParam String groupName, @RequestParam(required = false) List<Integer> usersId) {
        System.out.println(usersId);
        groupService.addGroup(groupName, usersId);
    }

    @PutMapping("/groups")
    public void updateGroupName(@RequestParam Integer groupId, @RequestParam String groupName) {
        groupService.updateGroupName(groupName, groupId);
    }

    @GetMapping("/groups")
    public List<GroupData> getGroup() {
        return groupService.getGroup();
    }

    @DeleteMapping("/groups")
    public void deleteGroupById(@RequestParam("id") Integer groupId) {
        groupService.deleteGroup(groupId);
    }

    @PostMapping("/groups/{id}")
    public void addUserInGroup(@RequestParam Integer groupId, @RequestParam Integer userId) {
        groupService.addUserInGroup(groupId, userId);
    }

    @DeleteMapping("/groups/{id}")
    public void deleteUserInGroup(@RequestParam Integer groupId, @RequestParam Integer userId) {
        groupService.deleteUserInGroup(groupId, userId);
    }
}
