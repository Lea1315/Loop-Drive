package com.example.demo.repo;

import com.example.demo.model.FileUser;
import com.example.demo.model.UserGroup;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Integer> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from user_group where group_id = :groupId")
    void deleteByGroupId(Integer groupId);

    @Query(nativeQuery = true, value = "select * from user_group where group_id = :groupId and user_id = :userId")
    UserGroup findByGroupUser(Integer groupId, Integer userId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete  from user_group where group_id = :groupId and user_id = :userId")
    void deleteByGroupUserId(Integer groupId, Integer userId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from user_group where user_id = :userId")
    void deleteByUserId(Integer userId);

    @Query(nativeQuery = true, value = "select * from user_group where user_id = :userId")
    List<UserGroup> findGroupsByUser(Integer userId);
}
