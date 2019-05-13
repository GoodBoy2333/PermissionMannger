package com.fy.PermissionMannger.ServiceImpl;
import com.fy.PermissionMannger.Entity.SysUser;
import com.fy.PermissionMannger.Mapper.SysUserMapper;
import com.fy.PermissionMannger.Service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service
@Transactional
public class SysUserServiceImpl implements SysUserService{

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return sysUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysUser record) {
        return sysUserMapper.insert(record);
    }

    @Override
    public int insertSelective(SysUser record) {
        return sysUserMapper.insertSelective(record);
    }

    @Override
    public SysUser selectByPrimaryKey(String id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SysUser record) {
        return sysUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysUser record) {
        return sysUserMapper.updateByPrimaryKey(record);
    }

	@Override
	public List<SysUser> findByAll(SysUser sysUser){
		 return sysUserMapper.findByAll(sysUser);
	}

    @Override
    public SysUser selectRoleAndPermissionByPrimaryKey(String id) {
        return sysUserMapper.selectRoleAndPermissionByPrimaryKey(id);
    }

    @Override
    public SysUser selectPermissionByPrimaryKey(String id) {
        return sysUserMapper.selectPermissionByPrimaryKey(id);
    }


}
