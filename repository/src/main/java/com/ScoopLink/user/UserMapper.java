package com.ScoopLink.user;


import com.ScoopLink.auth.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    /**
     * 根据账号获取用户信息
     * @param account 账号
     * @return 用户信息
     */
    User getUserByAccount(String account);


    /**
     * 根据ID获取用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    User getUserById(Long id);



    /**
     * 创建用户
     * @param user 用户信息
     * @return 是否成功
     */
    boolean createUser(User user);


    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 是否成功
     */
    boolean updateUser(User user);



    /**
     * 删除用户
     * @param id 用户ID
     * @return 是否成功
     */
    boolean deleteUser(Long id);
}