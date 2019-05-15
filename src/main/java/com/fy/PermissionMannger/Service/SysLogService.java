package com.fy.PermissionMannger.Service;

import com.fy.PermissionMannger.Entity.SysLog;

public interface SysLogService {


    int insert(SysLog record);

    int insertSelective(SysLog record);

    int deleteByPrimaryKey(String id);

    SysLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);
}

