package com.ScoopLink.auth.server;

import com.ScoopLink.auth.dto.ResetPassword;
import com.ScoopLink.auth.dto.User;
import com.ScoopLink.auth.dto.UserInfo;

public interface AuthServer {
    /**
     * 登录
     * @param account 账号
     * @param password 密码
     * @return 用户信息
     */
    public User Login(String account, String password);


    /**
     * 修改用户信息
     * @param user 用户信息
     * @return 是否成功
     */
    public User ChangeUserInfo(UserInfo user);


    /**
     * 重置密码
     * @param resetPassword 重置密码信息
     * @return 是否成功
     */
     public boolean ResetPassword(ResetPassword resetPassword);

}
