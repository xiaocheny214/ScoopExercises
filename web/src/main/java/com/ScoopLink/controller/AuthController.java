package com.ScoopLink.controller;


import com.ScoopLink.auth.dto.LoginRequest;
import com.ScoopLink.auth.dto.ResetPassword;
import com.ScoopLink.auth.dto.User;
import com.ScoopLink.auth.dto.UserInfo;
import com.ScoopLink.auth.server.AuthServer;
import com.ScoopLink.response.CommonResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/v1")
@Slf4j
public class AuthController {

    @Resource
    private AuthServer authServer;

    /**
     * 登录
     * @param loginRequest 登录请求
     * @return 登录成功后的用户信息
     */
    @PostMapping("/login")
    public CommonResponse<User> Login(@RequestBody LoginRequest loginRequest) {
        log.info("Login request: {}", loginRequest);
        User user = authServer.Login(loginRequest.getAccount(), loginRequest.getPassword());
        return CommonResponse.success(user);
    }


    /**
     * 修改用户信息
     * @param user 用户信息
     * @return 修改后的用户信息
     */
    @PostMapping("/changeUserInfo")
    public CommonResponse<User> ChangeUserInfo(@RequestBody UserInfo user){
        log.info("ChangeUserInfo request: {}", user);
        User updatedUser = authServer.ChangeUserInfo(user);
        return CommonResponse.success(updatedUser);
    }


    /**
     * 重置密码
     * @param resetPassword 重置密码请求
     * @return 重置密码成功后的用户信息
     */
    @PostMapping("/resetPassword")
    public CommonResponse<Boolean> ResetPassword(@RequestBody ResetPassword resetPassword){
        log.info("ResetPassword request: {}", resetPassword);
        boolean isReset = authServer.ResetPassword(resetPassword);
        return CommonResponse.success(isReset);
    }
}
