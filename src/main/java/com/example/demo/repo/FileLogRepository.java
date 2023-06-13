package com.example.demo.repo;

import com.example.demo.model.FileLog;
import com.example.demo.model.FileLogData;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileLogRepository extends JpaRepository<FileLog, Integer> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from file_log where file_id = :fileId")
    void deleteByFileId(Integer fileId);
    @Query(nativeQuery = true, value = "select * from file_log where file_id = :fileId limit 1")
    FileLog findByFileId(Integer fileId);

    @Query(nativeQuery = true, value = """
            select file_log.upload_date "uploadDate", file.title, user_created.username "uploadUser", file_log.download_date "downloadDate", user_downloaded.username "downloadUser" 
            from file_log 
            INNER JOIN file ON file_log.file_id=file.id 
            INNER JOIN users user_created ON file_log.upload_user=user_created.id 
            LEFT JOIN users user_downloaded ON file_log.download_user=user_downloaded.id
            """)
    List<FileLogData> findLogs();
}
