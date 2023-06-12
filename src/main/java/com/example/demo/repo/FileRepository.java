package com.example.demo.repo;

import com.example.demo.model.File;
import com.example.demo.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {
    @Query(nativeQuery = true, value = "select * from file where link = :fileLink")
    File findFileByLink(String fileLink);
}
