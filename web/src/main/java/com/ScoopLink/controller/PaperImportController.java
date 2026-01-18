package com.ScoopLink.controller;

import com.ScoopLink.manageQuestion.papers.dto.PaperImportTemplate;
import com.ScoopLink.manageQuestion.papers.server.PaperImportServer;
import com.ScoopLink.response.CommonResponse;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/paper/v1/import")
public class PaperImportController {
    
    @Resource
    private PaperImportServer paperImportServer;
    
    /**
     * 通过JSON模板导入试卷
     */
    @PostMapping("/json")
    public CommonResponse<Boolean> importPaperFromJson(@RequestBody PaperImportTemplate importTemplate) {
        boolean success = paperImportServer.importPaper(importTemplate);
        return CommonResponse.success(success);
    }
    
    /**
     * 通过上传Excel文件导入试卷
     */
    @PostMapping("/excel")
    public CommonResponse<Boolean> importPaperFromExcel(@RequestParam("file") MultipartFile file) {
        try {
            // 保存上传的文件到临时位置，然后调用导入服务
            String tempFilePath = saveTempFile(file);
            boolean success = paperImportServer.importPaperFromExcel(tempFilePath);
            
            // 删除临时文件
            deleteTempFile(tempFilePath);
            
            return CommonResponse.success(success);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.error("导入失败");
        }
    }
    
    private String saveTempFile(MultipartFile file) throws IOException {
        // 创建临时文件
        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        
        String tempFileName = "temp_" + UUID.randomUUID().toString() + fileExtension;
        Path tempPath = Paths.get(System.getProperty("java.io.tmpdir"), tempFileName);
        
        // 将上传的文件保存到临时位置
        Files.write(tempPath, file.getBytes());
        
        return tempPath.toString();
    }
    
    private void deleteTempFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}