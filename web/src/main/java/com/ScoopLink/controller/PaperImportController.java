package com.ScoopLink.controller;

import com.ScoopLink.manageQuestion.papers.dto.PaperImportTemplate;
import com.ScoopLink.manageQuestion.papers.server.PaperImportServer;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/paper/v1/import")
public class PaperImportController {
    
    @Resource
    private PaperImportServer paperImportServer;
    
    /**
     * 通过JSON模板导入试卷
     */
    @PostMapping("/json")
    public ResponseEntity<Boolean> importPaperFromJson(@RequestBody PaperImportTemplate importTemplate) {
        boolean success = paperImportServer.importPaper(importTemplate);
        return ResponseEntity.ok(success);
    }
    
    /**
     * 通过上传Excel文件导入试卷
     */
    @PostMapping("/excel")
    public ResponseEntity<Boolean> importPaperFromExcel(@RequestParam("file") MultipartFile file) {
        try {
            // 保存上传的文件到临时位置，然后调用导入服务
            String tempFilePath = saveTempFile(file);
            boolean success = paperImportServer.importPaperFromExcel(tempFilePath);
            return ResponseEntity.ok(success);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    
    private String saveTempFile(MultipartFile file) {
        // 实现文件保存逻辑
        return null; // 临时返回
    }
}