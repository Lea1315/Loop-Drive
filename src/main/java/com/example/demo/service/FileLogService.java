package com.example.demo.service;

import com.example.demo.model.FileLog;
import com.example.demo.model.FileLogData;
import com.example.demo.repo.FileLogRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileLogService {
    @Autowired
    private FileLogRepository fileLogRepository;

    public ResponseEntity getFileLogs() throws FileNotFoundException, JRException {

            List<FileLogData> fileLogList = fileLogRepository.findLogs();

            File file = ResourceUtils.getFile("classpath:FileLog.jrxml");

            JasperReport jasperReport = JasperCompileManager
                    .compileReport(file.getAbsolutePath());


            JRBeanCollectionDataSource dataSource = new
                    JRBeanCollectionDataSource(fileLogList);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy","Lea");
            JasperPrint jasperPrint = JasperFillManager
                    .fillReport(jasperReport,parameters,dataSource);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "File_Log" + "\"")
                .body(JasperExportManager.exportReportToPdf(jasperPrint));

    }
}
