package com.fy.PermissionMannger.Service;

import com.fy.PermissionMannger.Entity.SysPermission;

import java.util.List;

public interface SysPermissionService{


    int deleteByPrimaryKey(String id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);

    List<SysPermission> findByAll(SysPermission sysPermission);

}
