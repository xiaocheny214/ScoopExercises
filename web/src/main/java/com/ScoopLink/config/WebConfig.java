package com.ScoopLink.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // 完全禁用WebConfig的资源处理，让PriorityStaticResourceController处理所有静态资源
}