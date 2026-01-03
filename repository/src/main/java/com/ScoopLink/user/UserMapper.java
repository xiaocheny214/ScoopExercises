package com.ScoopLink.user;


import com.ScoopLink.auth.dto.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    /**
     * 根据账号获取用户信息
     * @param account 账号
     * @return 用户信息
     */
    @Select("select * from users where account = #{account}")
    public User getUserByAccount(String account);


    /**
     * 根据ID获取用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    @Select("select * from users where id = #{id}")
    public User getUserById(Long id);



    /**
     * 创建用户
     * @param user 用户信息
     * @return 是否成功
     */
    @Insert("insert into users (account, password, nickName, osIdentifier,createTime,loginTime) values (#{account}, #{password}, #{nickName}, #{osIdentifier}, #{createTime}, #{loginTime})")
    public boolean createUser(User user);


    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 是否成功
     */
    @Update("update users set account = #{account}, password = #{password}, nickName = #{nickName}, osIdentifier = #{osIdentifier}, createTime = #{createTime}, loginTime = #{loginTime} where id = #{id}")
    public boolean updateUser(User user);



    /**
     * 删除用户
     * @param id 用户ID
     * @return 是否成功
     */
    @Delete("delete from users where id = #{id}")
    public boolean deleteUser(Long id);

}
