package com.mango.shop.spike.mapper;

import com.mango.shop.spike.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author mango
 * @Since 2020/2/28 21:24
 **/
@Mapper
public interface UserMapper {

    @Insert("INSERT INTO tb_user(username) value(#{username})")
    public Integer saveUser(User user);

    @Select("SELECT * FROM tb_user WHERE id = #{param1}")
    public User getUser(Integer id);

}
