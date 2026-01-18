package com.ScoopLink.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 主页控制器，用于处理前端路由
 */
@Controller
public class IndexController {

    /**
     * 处理前端SPA路由，避免与静态资源冲突
     */
    @GetMapping(value = {"/", "/home", "/question", "/profile", "/exam", "/login", "/register"})
    public String spa() {
        return "forward:/index.html";
    }
}