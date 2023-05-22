package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repo.GroupRepository;
import com.example.demo.repo.UserGroupRepository;
import com.example.demo.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserGroupRepository userGroupRepository;

    public void addGroup(String groupName, List<Integer> users) {
        Group newGroup = new Group(groupName);
        groupRepository.save(newGroup);
        Integer groupId = groupRepository.findByName(groupName).getId();
        if(users != null) {
            for (Integer userId : users) {
                var userGroup = new UserGroup(userId, groupId);
                userGroupRepository.save(userGroup);
            }
        }
    }

    public List<GroupData> getGroup() {
        ModelMapper modelMapper = new ModelMapper();

        var result = new ArrayList<GroupData>();
        var groups =  groupRepository.findAll();
        for(var group: groups){
            var groupData = modelMapper.map(group, GroupData.class);

            var users = userRepository.findByGroupId(group.getId());
            List<UserData> userDataList = modelMapper.map(users, new TypeToken<List<UserData>>() {}.getType());
            groupData.setUsers(userDataList);

            result.add(groupData);
        }

        return result;
    }

    public void deleteGroup(Integer id) {
        groupRepository.deleteById(id);
        userGroupRepository.deleteByGroupId(id);
    }


    public void updateGroupName(String groupName, Integer groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        if(group.isPresent()) {
            group.get().setName(groupName);
            groupRepository.save(group.get());
        }
        else throw new RuntimeException("This group doesn't exist!");
    }

    public void addUserInGroup(Integer groupId, Integer userId) {
        Optional<Group> group = groupRepository.findById(groupId);
        if(group.isPresent()) {
            if(userGroupRepository.findByGroupUser(groupId, userId) == (null)) {
                UserGroup ug = new UserGroup(userId, groupId);
                userGroupRepository.save(ug);
            }
        }
        else throw new RuntimeException("This group doesn't exist!");
    }

    public void deleteUserInGroup(Integer groupId, Integer userId) {
        Optional<Group> group = groupRepository.findById(groupId);
        if(group.isPresent()) {
            if(userGroupRepository.findByGroupUser(groupId, userId) != (null)) {
                userGroupRepository.deleteByGroupUserId(groupId, userId);
            }
            else throw new RuntimeException("This user is not in this group!");
        }
        else throw new RuntimeException("This group doesn't exist!");
    }

}
