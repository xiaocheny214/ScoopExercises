package com.ScoopLink.auth.dto;

import lombok.Data;

@Data
public class ResetPassword {
    /**
     * 账号
     */
    private String account;
    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 旧密码
     */
    private String oldPassword;
}
