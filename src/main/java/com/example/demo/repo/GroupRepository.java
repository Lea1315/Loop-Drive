package com.example.demo.repo;

import com.example.demo.model.Group;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    Group findByName(String name);
    @Query(nativeQuery = true, value = "select * from groups limit 1")
    Group findAny();
}
