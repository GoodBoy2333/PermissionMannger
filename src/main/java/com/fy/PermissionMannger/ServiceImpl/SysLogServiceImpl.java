package com.fy.PermissionMannger.ServiceImpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.fy.PermissionMannger.Entity.SysLog;
import com.fy.PermissionMannger.Mapper.SysLogMapper;
import com.fy.PermissionMannger.Service.SysLogService;

@Service
public class SysLogServiceImpl implements SysLogService {

    @Resource
    private SysLogMapper sysLogMapper;

    @Override
    public int insert(SysLog record) {
        return sysLogMapper.insert(record);
    }

    @Override
    public int insertSelective(SysLog record) {
        return sysLogMapper.insertSelective(record);
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return sysLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public SysLog selectByPrimaryKey(String id) {
        return sysLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SysLog record) {
        return sysLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysLog record) {
        return sysLogMapper.updateByPrimaryKey(record);
    }
}

