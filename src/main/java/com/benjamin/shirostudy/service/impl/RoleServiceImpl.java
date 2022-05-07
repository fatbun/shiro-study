package com.benjamin.shirostudy.service.impl;

import com.benjamin.shirostudy.entity.Role;
import com.benjamin.shirostudy.mapper.RoleMapper;
import com.benjamin.shirostudy.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author zjw
 * @description
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;


    @Override
    public Set<Role> findRolesByUid(Integer uid) {
        return roleMapper.findRolesByUid(uid);
    }
}
