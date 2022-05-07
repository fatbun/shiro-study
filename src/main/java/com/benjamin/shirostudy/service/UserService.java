package com.benjamin.shirostudy.service;


import com.benjamin.shirostudy.entity.User;

/**
 * @author zjw
 * @description
 */
public interface UserService {

    User findByUsername(String username);

}
