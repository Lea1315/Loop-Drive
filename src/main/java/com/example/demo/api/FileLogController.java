package com.example.demo.api;

import com.example.demo.service.FileLogService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RequestMapping("api")
@RestController
public class FileLogController {
    @Autowired
    FileLogService fileLogService;
    @GetMapping("/download-file-logs")
    public ResponseEntity getFileLogs() throws JRException, FileNotFoundException {
        return fileLogService.getFileLogs();
    }
}
