package com.benjamin.shirostudy.service.impl;

import com.benjamin.shirostudy.entity.Permission;
import com.benjamin.shirostudy.mapper.PermissionMapper;
import com.benjamin.shirostudy.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author zjw
 * @description
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;


    @Override
    public Set<Permission> findPermsByRoleSet(Set<Integer> roleIdSet) {
        return permissionMapper.findPermsByRoleIdIn(roleIdSet);
    }
}
