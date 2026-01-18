package com.ScoopLink.controller;

import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 高优先级静态资源控制器
 * 使用@Order确保优先级高于其他控制器
 */
@RestController
@Order(1) // 最高优先级
public class PriorityStaticResourceController {

    @GetMapping("/assets/{filename:.+}")
    public ResponseEntity<Resource> serveAsset(@PathVariable String filename) {
        System.out.println("PriorityStaticResourceController: Serving " + filename);
        
        try {
            Resource resource = new ClassPathResource("static/assets/" + filename);
            if (resource.exists() && resource.isReadable()) {
                String contentType = determineContentType(filename);
                System.out.println("PriorityStaticResourceController: " + filename + " -> " + contentType);
                
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, contentType)
                        .header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
                        .header(HttpHeaders.PRAGMA, "no-cache")
                        .header(HttpHeaders.EXPIRES, "0")
                        .body(resource);
            }
        } catch (Exception e) {
            System.err.println("PriorityStaticResourceController: Error serving " + filename + ": " + e.getMessage());
            e.printStackTrace();
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/vite.svg")
    public ResponseEntity<Resource> serveViteSvg() {
        System.out.println("PriorityStaticResourceController: Serving vite.svg");
        
        try {
            Resource resource = new ClassPathResource("static/vite.svg");
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "image/svg+xml")
                        .header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
                        .header(HttpHeaders.PRAGMA, "no-cache")
                        .header(HttpHeaders.EXPIRES, "0")
                        .body(resource);
            }
        } catch (Exception e) {
            System.err.println("PriorityStaticResourceController: Error serving vite.svg: " + e.getMessage());
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/index.html")
    public ResponseEntity<Resource> serveIndexHtml() {
        System.out.println("PriorityStaticResourceController: Serving index.html");
        
        try {
            Resource resource = new ClassPathResource("static/index.html");
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "text/html; charset=utf-8")
                        .header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
                        .header(HttpHeaders.PRAGMA, "no-cache")
                        .header(HttpHeaders.EXPIRES, "0")
                        .body(resource);
            }
        } catch (Exception e) {
            System.err.println("PriorityStaticResourceController: Error serving index.html: " + e.getMessage());
        }
        
        return ResponseEntity.notFound().build();
    }
    
    private String determineContentType(String filename) {
        String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        switch (extension) {
            case "js":
                return "application/javascript; charset=utf-8";
            case "css":
                return "text/css; charset=utf-8";
            case "png":
                return "image/png";
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "gif":
                return "image/gif";
            case "svg":
                return "image/svg+xml";
            case "ico":
                return "image/x-icon";
            case "woff":
                return "font/woff";
            case "woff2":
                return "font/woff2";
            default:
                return "application/octet-stream";
        }
    }
}