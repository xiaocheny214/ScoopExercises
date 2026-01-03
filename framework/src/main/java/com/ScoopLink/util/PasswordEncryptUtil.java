package com.ScoopLink.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码加密工具类
 */
public class PasswordEncryptUtil {
    
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 加密密码
     *
     * @param rawPassword 原始密码
     * @return 加密后的密码
     */
    public static String encryptPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * 验证密码
     *
     * @param rawPassword       原始密码
     * @param encodedPassword   加密后的密码
     * @return 验证结果
     */
    public static boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 检查密码编码是否需要更新
     *
     * @param encodedPassword 加密后的密码
     * @return 是否需要更新编码
     */
    public static boolean isPasswordUpdateNeeded(String encodedPassword) {
        return passwordEncoder.upgradeEncoding(encodedPassword);
    }
}