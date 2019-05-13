package com.fy.PermissionMannger.Security;

import com.fy.PermissionMannger.Entity.SysPermission;
import com.fy.PermissionMannger.Entity.SysRole;
import com.fy.PermissionMannger.Entity.SysUser;
import com.fy.PermissionMannger.Service.SysUserService;
import com.google.code.kaptcha.Constants;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.concurrent.TimeUnit;

/**
 * 〈权限信息的验证〉
 *
 * @author fangyan
 * @create 2019/5/7
 * @since 1.0.0
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpSession session;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("用户认证----------MyShiroRealm.doGetAuthenticationInfo");
        String tokenUserName = (String) token.getPrincipal();
        String kap=request.getParameter("captchaCode");
        String realKap= (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);

//        String tokenUserPassword = new String((char[]) token.getCredentials());
        SysUser sysUser = sysUserService.selectByPrimaryKey(tokenUserName);
        if (sysUser == null) {
            throw new UnknownAccountException("用户不存在");
        }
        if (sysUser.getStatus().trim().equals("0")) {
            throw new LockedAccountException("用户被锁定");
        }
        if(StringUtils.isEmpty(kap)){
            request.setAttribute("loginFailure","验证码为空");
            throw new RuntimeException("验证码为空");
        }else if(!kap.equals(realKap)){
            request.setAttribute("loginFailure","验证码错误");
            throw new RuntimeException("验证码错误");
        }
        //ByteSource.Util.bytes(sysUser.getUserName()) 计算账户盐值
        return new SimpleAuthenticationInfo(sysUser, sysUser.getUserPwd(), ByteSource.Util.bytes(sysUser.getUserName()), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUser user = (SysUser) principals.getPrimaryPrincipal();
        if(redisTemplate.hasKey("authorizationInfo:"+user.getId())){
            Object lastHours=redisTemplate.getExpire("authorizationInfo:"+user.getId(),TimeUnit.HOURS);
            System.out.println("redis中读取权限配置，剩余过期时间："+lastHours+"小时，From:MyShiroRealm.doGetAuthorizationInfo");
            AuthorizationInfo authorizationInfoForRedis= (AuthorizationInfo) redisTemplate.opsForValue().get("authorizationInfo:"+user.getId());
            return authorizationInfoForRedis;
        }

        System.out.println("权限配置----------MyShiroRealm.doGetAuthorizationInfo");

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        SysUser sysUser = sysUserService.selectRoleAndPermissionByPrimaryKey(user.getId());

        Collection<String> roles=new LinkedHashSet<>();
        Collection<String> permissions=new LinkedHashSet<>();
        Collection<SysPermission> permissionObjects=new LinkedHashSet<>();

        for (SysRole role : sysUser.getSysRole()) {
            roles.add(role.getRoleName());
            for (SysPermission p : role.getSysPermissions()) {
                permissions.add(p.getPermission());
                //系统菜单
                permissionObjects.add(p);
            }
        }
        authorizationInfo.addRoles(roles);
        authorizationInfo.addStringPermissions(permissions);

        //第一次查询赋值
        redisTemplate.opsForValue().set("authorizationInfo:"+user.getId(),authorizationInfo,24, TimeUnit.HOURS);

        return authorizationInfo;
    }


}
