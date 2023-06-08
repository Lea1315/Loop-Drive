package com.example.demo.repo;

import com.example.demo.model.FileLog;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FileLogRepository extends JpaRepository<FileLog, Integer> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from file_log where file_id = :fileId")
    void deleteByFileId(Integer fileId);
    @Query(nativeQuery = true, value = "select * from file_log where file_id = :fileId limit 1")
    FileLog findByFileId(Integer fileId);
}
