package com.fy.PermissionMannger.Service;
import com.fy.PermissionMannger.Entity.SysUser;

import java.util.List;
public interface SysUserService{


    int deleteByPrimaryKey(String id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);



	List<SysUser> findByAll(SysUser sysUser);

    SysUser selectRoleAndPermissionByPrimaryKey(String id);

    SysUser selectPermissionByPrimaryKey(String id);
}
