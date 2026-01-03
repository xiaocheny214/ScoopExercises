package com.ScoopLink.user;

import com.ScoopLink.auth.dto.User;
import com.ScoopLink.auth.server.UserServer;
import com.ScoopLink.util.PasswordEncryptUtil;
import com.ScoopLink.util.SnowflakeIdGenerator;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServerImpl implements UserServer {

    @Resource
    private UserMapper userMapper;

    // 初始化雪花算法生成器
    private final SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(1L, 1L);
    @Override
    public User getUserByAccount(String account) {
        return userMapper.getUserByAccount(account);
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.getUserById(id);
    }

    @Override
    @Transactional
    public boolean createUser(User user) {
        // 如果没有设置OS标识符，则使用雪花算法生成
        if (user.getOsIdentifier() == null || user.getOsIdentifier().isEmpty()) {
            user.setOsIdentifier(String.valueOf(idGenerator.nextId()));
        }

        // 对密码进行加密（如果尚未加密）
        if (!user.getPassword().startsWith("$2a$")) { // BCrypt密码通常以$2a$开头
            user.setPassword(PasswordEncryptUtil.encryptPassword(user.getPassword()));
        }
        return userMapper.createUser(user);
    }

    @Override
    @Transactional
    public boolean updateUser(User user) {
        // 如果更新了密码，需要加密
        if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
            user.setPassword(PasswordEncryptUtil.encryptPassword(user.getPassword()));
        }
        return userMapper.updateUser(user);
    }

    @Override
    @Transactional
    public boolean deleteUser(Long id) {
        return userMapper.deleteUser(id);
    }
}
