package com.ScoopLink.auth.server;

import com.ScoopLink.auth.dto.User;

public interface UserServer {

    /**
     * 根据账号获取用户信息
     * @param account 账号
     * @return 用户信息
     */
    public User getUserByAccount(String account);


    /**
     * 根据ID获取用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    public User getUserById(Long id);



    /**
     * 创建用户
     * @param user 用户信息
     * @return 是否成功
     */
    public boolean createUser(User user);


    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 是否成功
     */
    public boolean updateUser(User user);



     /**
      * 删除用户
      * @param id 用户ID
      * @return 是否成功
      */
    public boolean deleteUser(Long id);



}
