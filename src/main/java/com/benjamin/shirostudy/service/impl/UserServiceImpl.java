package com.benjamin.shirostudy.service.impl;

import com.benjamin.shirostudy.entity.User;
import com.benjamin.shirostudy.mapper.UserMapper;
import com.benjamin.shirostudy.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zjw
 * @description
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }
}
