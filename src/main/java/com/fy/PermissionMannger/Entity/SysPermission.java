package com.fy.PermissionMannger.Entity;

public class SysPermission extends BaseTreeGrid{
    private String id;

    /**
    * 名称
    */
    private String name;

    /**
    * 是否可见
    */
    private String available;

    /**
    * 父id
    */
    private String parentId;

    /**
    * 父id列表
    */
    private String parentIds;

    /**
    * 权限字符串，menu例子：role:*，button例子：role:create
    */
    private String permission;

    /**
    * 资源类型
    */
    private String resourceType;

    /**
    * 资源路径
    */
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}