package com.fy.PermissionMannger.Aop;

import com.alibaba.druid.util.StringUtils;
import com.fy.PermissionMannger.Entity.SysUser;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 〈分页设置〉
 *
 * @author fangyan
 * @create 2019/5/5
 * @since 1.0.0
 */
@Aspect
@Configuration
public class Page {

    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut(value = "execution(* com.fy.PermissionMannger.Controller.*.*(..))")
    public void setPage() {
    }

    @Before("execution(* com.fy.PermissionMannger.Controller.*.*Page(..))")
    public void setStartPage(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        Integer page = 1;
        Integer rows = 10;
        if (!StringUtils.isEmpty(request.getParameter("page"))) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        if (!StringUtils.isEmpty(request.getParameter("rows"))) {
            rows = Integer.parseInt(request.getParameter("rows"));
        }
        /*if (redisTemplate.opsForHash().hasKey("userList", "userList:" + sysUser.getId() + "page:" + page)) {
            StringBuilder msg = new StringBuilder();
            msg.append("----------------");
            msg.append("Redis存在用户缓存数据，未设置分页。距离Key：userList过期还有：");
            msg.append(redisTemplate.getExpire("userList", TimeUnit.DAYS) + "天 ");
            msg.append(redisTemplate.getExpire("userList", TimeUnit.HOURS) + "小时 ");
            msg.append(redisTemplate.getExpire("userList", TimeUnit.MINUTES) + "分钟 ");
            msg.append("----------------");
            System.out.println(msg);
            return;
        }*/
        System.out.println("----------------PageHelper设置：" + page + "页，每页：" + rows + "条---------------");
        PageHelper.startPage(page, rows);
    }
}
