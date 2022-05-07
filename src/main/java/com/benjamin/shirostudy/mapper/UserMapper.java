package com.benjamin.shirostudy.mapper;

import com.benjamin.shirostudy.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author zjw
 * @description
 */
public interface UserMapper {

    @Select("select * from tb_user where username = #{username}")
    User findUserByUsername(@Param("username") String username);

}
