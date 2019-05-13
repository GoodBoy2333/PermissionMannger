package com.fy.PermissionMannger.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SysUser implements Serializable {
    /**
    * ID
    */
    private String id;

    /**
    * 用户名
    */
    private String userName;

    /**
    * 密码
    */
    private String userPwd;

    /**
    * 电话
    */
    private String tel;

    /**
    * 邮箱
    */
    private String mail;

    /**
    * 性别
    */
    private String sex;

    /**
    * 创建时间
    */
    private Date createDate;

    /**
    * 登录时间
    */
    private Date loginDate;

    /**
    * 状态
    */
    private String status;

    private String salt;

    private List<SysRole> sysRole;

    private List<SysPermission> sysPermissions;

    public List<SysPermission> getSysPermissions() {
        return sysPermissions;
    }

    public void setSysPermissions(List<SysPermission> sysPermissions) {
        this.sysPermissions = sysPermissions;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<SysRole> getSysRole() {
        return sysRole;
    }

    public void setSysRole(List<SysRole> sysRole) {
        this.sysRole = sysRole;
    }

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}