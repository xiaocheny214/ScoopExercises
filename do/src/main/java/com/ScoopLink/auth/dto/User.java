package com.ScoopLink.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 操作系统标识符
     */
    private String osIdentifier;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;
}
