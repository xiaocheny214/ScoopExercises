package com.ScoopLink.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码加密工具类
 */
public class PasswordEncryptUtil {
    
    // 使用延迟初始化避免潜在的FactoryBean问题
    private static PasswordEncoder passwordEncoder;

    private static PasswordEncoder getPasswordEncoder() {
        if (passwordEncoder == null) {
            passwordEncoder = new BCryptPasswordEncoder();
        }
        return passwordEncoder;
    }

    /**
     * 加密密码
     *
     * @param rawPassword 原始密码
     * @return 加密后的密码
     */
    public static String encryptPassword(String rawPassword) {
        return getPasswordEncoder().encode(rawPassword);
    }

    /**
     * 验证密码
     *
     * @param rawPassword       原始密码
     * @param encodedPassword   加密后的密码
     * @return 验证结果
     */
    public static boolean verifyPassword(String rawPassword, String encodedPassword) {
        return getPasswordEncoder().matches(rawPassword, encodedPassword);
    }

    /**
     * 检查密码编码是否需要更新
     *
     * @param encodedPassword 加密后的密码
     * @return 是否需要更新编码
     */
    public static boolean isPasswordUpdateNeeded(String encodedPassword) {
        return getPasswordEncoder().upgradeEncoding(encodedPassword);
    }
}