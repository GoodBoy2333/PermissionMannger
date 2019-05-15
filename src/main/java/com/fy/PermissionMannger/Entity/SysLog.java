package com.fy.PermissionMannger.Entity;

public class SysLog {
    /**
     * ID
     */
    private String id;

    /**
     * 请求IP
     */
    private String qqip;

    /**
     * 请求路径
     */
    private String qqlj;

    /**
     * 请求方式
     */
    private String qqfs;

    /**
     * 方法描述
     */
    private String ffms;

    /**
     * 请求参数
     */
    private String qqcs;

    /**
     * 执行时间
     */
    private String zxsj;

    /**
     * 返回值
     */
    private String fhz;

    /**
     * 用户id
     */
    private String userid;

    /**
     * 用户名
     */
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQqip() {
        return qqip;
    }

    public void setQqip(String qqip) {
        this.qqip = qqip;
    }

    public String getQqlj() {
        return qqlj;
    }

    public void setQqlj(String qqlj) {
        this.qqlj = qqlj;
    }

    public String getQqfs() {
        return qqfs;
    }

    public void setQqfs(String qqfs) {
        this.qqfs = qqfs;
    }

    public String getFfms() {
        return ffms;
    }

    public void setFfms(String ffms) {
        this.ffms = ffms;
    }

    public String getQqcs() {
        return qqcs;
    }

    public void setQqcs(String qqcs) {
        this.qqcs = qqcs;
    }

    public String getZxsj() {
        return zxsj;
    }

    public void setZxsj(String zxsj) {
        this.zxsj = zxsj;
    }

    public String getFhz() {
        return fhz;
    }

    public void setFhz(String fhz) {
        this.fhz = fhz;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}