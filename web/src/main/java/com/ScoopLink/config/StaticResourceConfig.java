package com.ScoopLink.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RouterFunctions.route;
import static org.springframework.web.servlet.function.RequestPredicates.GET;

@Configuration
public class StaticResourceConfig {

    @Bean
    public RouterFunction<ServerResponse> staticResourceRouter() {
        return route(GET("/assets/{filename}"), request -> {
            String filename = request.pathVariable("filename");
            System.out.println("RouterFunction: Serving asset: " + filename);
            
            try {
                Resource resource = new ClassPathResource("static/assets/" + filename);
                if (resource.exists()) {
                    MediaType mediaType = getMediaType(filename);
                    System.out.println("RouterFunction: MediaType for " + filename + ": " + mediaType);
                    
                    return ServerResponse.ok()
                            .contentType(mediaType)
                            .header(HttpHeaders.CACHE_CONTROL, "no-cache")
                            .body(resource);
                }
            } catch (Exception e) {
                System.err.println("RouterFunction: Error serving " + filename + ": " + e.getMessage());
            }
            return ServerResponse.notFound().build();
        })
        .andRoute(GET("/vite.svg"), request -> {
            System.out.println("RouterFunction: Serving vite.svg");
            try {
                Resource resource = new ClassPathResource("static/vite.svg");
                if (resource.exists()) {
                    return ServerResponse.ok()
                            .contentType(MediaType.valueOf("image/svg+xml"))
                            .header(HttpHeaders.CACHE_CONTROL, "no-cache")
                            .body(resource);
                }
            } catch (Exception e) {
                System.err.println("RouterFunction: Error serving vite.svg: " + e.getMessage());
            }
            return ServerResponse.notFound().build();
        });
    }
    
    private MediaType getMediaType(String filename) {
        String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        switch (extension) {
            case "js":
                return MediaType.valueOf("application/javascript");
            case "css":
                return MediaType.valueOf("text/css");
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "gif":
                return MediaType.IMAGE_GIF;
            case "svg":
                return MediaType.valueOf("image/svg+xml");
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}