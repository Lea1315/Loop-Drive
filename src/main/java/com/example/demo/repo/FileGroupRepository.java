package com.example.demo.repo;

import com.example.demo.model.FileGroup;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FileGroupRepository extends JpaRepository<FileGroup, Integer> {

    @Query(nativeQuery = true, value = "select * from file_groups where group_id = :groupId and file_id = :fileId")
    FileGroup findByFileGroup(Integer fileId, Integer groupId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from file_groups where group_id = :groupId and file_id = :fileId")
    void deleteByFileGroupId(Integer fileId, Integer groupId);
}
