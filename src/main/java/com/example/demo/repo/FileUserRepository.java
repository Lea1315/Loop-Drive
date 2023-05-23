package com.example.demo.repo;

import com.example.demo.model.FileUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUserRepository extends JpaRepository<FileUser, Integer> {

    @Query(nativeQuery = true, value = "select * from file_users where user_id = :userId and file_id = :fileId")
    FileUser findByFileUser(Integer fileId, Integer userId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from file_users where user_id = :userId and file_id = :fileId")
    void deleteByFileUserId(Integer fileId, Integer userId);
}
