package com.fy.PermissionMannger.Service;

import com.fy.PermissionMannger.Entity.SysRole;
public interface SysRoleService{


    int deleteByPrimaryKey(String id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

}
