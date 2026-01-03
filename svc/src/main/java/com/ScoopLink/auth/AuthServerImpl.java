package com.ScoopLink.auth;

import com.ScoopLink.auth.dto.ResetPassword;
import com.ScoopLink.auth.dto.User;
import com.ScoopLink.auth.server.AuthServer;
import com.ScoopLink.auth.server.UserServer;
import com.ScoopLink.util.PasswordEncryptUtil;
import com.ScoopLink.util.RandomChineseNameGenerator;
import com.ScoopLink.util.SnowflakeIdGenerator;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;



@Service
public class AuthServerImpl implements AuthServer {

    @Resource
    private UserServer userServer;

    // 初始化雪花算法生成器，使用默认的工作节点ID和数据中心ID
    private final SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(1L, 1L);
    @Override
    public User Login(String account, String password) {

        //校验账号密码是否为空
        if (account == null || password == null) {
            throw new IllegalArgumentException("账号或密码不能为空");
        }
        //判断密码是否为强密码
        if (!isStrongPassword(password)) {
            throw new IllegalArgumentException("密码必须包含大写字母、小写字母、数字和特殊字符");
        }
        //查询是否存在该账号
        User user = userServer.getUserByAccount(account);
        if (user == null) {
            //判定为用户第一次登录，即创建用户
            RandomChineseNameGenerator generator =  RandomChineseNameGenerator.INSTANCE;
            user.setAccount(account);
            user.setPassword(PasswordEncryptUtil.encryptPassword(password));
            user.setNickName(generator.generateUniqueName());
            user.setOsIdentifier(String.valueOf(idGenerator.nextId()));
            user.setCreateTime(LocalDateTime.now());
            user.setLoginTime(LocalDateTime.now());

            userServer.createUser(user);
        }else {
            //校验密码是否正确
            if (!PasswordEncryptUtil.verifyPassword(password, user.getPassword())) {
                throw new IllegalArgumentException("密码错误");
            }
            //更新登录时间
            user.setLoginTime(LocalDateTime.now());
            userServer.updateUser(user);
        }


        return user;
    }



    @Override
    public boolean ChangeUserInfo(User user) {
        //校验是否存在该账号
        User existingUser = userServer.getUserByAccount(user.getAccount());
        if (existingUser == null) {
            throw new IllegalArgumentException("账号不存在");
        }
        //更新用户信息
        existingUser.setNickName(user.getNickName());
        return userServer.updateUser(existingUser);
    }

    @Override
    public boolean ResetPassword(ResetPassword resetPassword) {
        //校验账号密码是否为空
        if (resetPassword.getAccount() == null || resetPassword.getNewPassword() == null) {
            throw new IllegalArgumentException("账号或密码不能为空");
        }
        //判断密码是否为强密码
        if (!isStrongPassword(resetPassword.getNewPassword())) {
            throw new IllegalArgumentException("密码必须包含大写字母、小写字母、数字和特殊字符");
        }
        //校验是否存在该账号
        User existingUser = userServer.getUserByAccount(resetPassword.getAccount());
        if (existingUser == null) {
            throw new IllegalArgumentException("账号不存在");
        }
        //校验密码是否正确
        if (!PasswordEncryptUtil.verifyPassword(resetPassword.getOldPassword(), existingUser.getPassword())) {
            throw new IllegalArgumentException("旧密码错误");
        }
        //更新密码
        existingUser.setPassword(PasswordEncryptUtil.encryptPassword(resetPassword.getNewPassword()));
        return userServer.updateUser(existingUser);
    }

    private boolean isStrongPassword(String password) {
        // 密码正则表达式，至少包含一个大写字母、一个小写字母、一个数字和一个特殊字符
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$";
        return password.matches(passwordRegex);
    }
}
