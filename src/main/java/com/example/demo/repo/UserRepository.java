package com.example.demo.repo;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
     User findByUsername(String username);

     User findUserByEmail(String email);

     @Query(nativeQuery = true, value = "select u.* from users u join user_group ug on ug.user_id = u.id where ug.group_id = :groupId")
     List<User> findByGroupId(Integer groupId);
}
