package com.fy.PermissionMannger.Entity;

import java.io.Serializable;
import java.util.List;

public class SysRole implements Serializable {
    /**
    * ID
    */
    private String id;

    /**
    * 角色名
    */
    private String roleName;

    /**
    * 角色介绍
    */
    private String roleDesc;

    private List<SysUser> sysUsers;

    private List<SysPermission> sysPermissions;

    public List<SysPermission> getSysPermissions() {
        return sysPermissions;
    }

    public void setSysPermissions(List<SysPermission> sysPermissions) {
        this.sysPermissions = sysPermissions;
    }

    public List<SysUser> getSysUsers() {
        return sysUsers;
    }

    public void setSysUsers(List<SysUser> sysUsers) {
        this.sysUsers = sysUsers;
    }

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }
}