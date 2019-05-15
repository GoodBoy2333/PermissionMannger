package com.fy.PermissionMannger.Aop;

import com.alibaba.fastjson.JSONObject;
import com.fy.PermissionMannger.Entity.SysLog;
import com.fy.PermissionMannger.Entity.SysUser;
import com.fy.PermissionMannger.Service.SysLogService;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈日志记录〉
 *
 * @author fangyan
 * @create 2019/5/15
 * @since 1.0.0
 */
@Aspect
@Configuration
public class ParamLogger {

    @Autowired
    SysLogService sysLogService;

    @Pointcut("execution(* com.fy.PermissionMannger.Controller..*.*(..))")
    public void controller() {
    }

    @Pointcut("execution(* com.fy.PermissionMannger.Service..*.*(..))")
    public void service() {
    }

    @Pointcut("execution(* com.fy.PermissionMannger.Mapper..*.*(..))")
    public void repository() {
    }

    @Before("controller()")
    public void controller(JoinPoint point) {

    }

    @Around("controller() && !execution(* com.fy.PermissionMannger.Controller..*.login(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        SysLog sysLog=new SysLog();
        sysLog.setId(new Date().toString());

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //设置请求ip
        sysLog.setQqip(request.getRemoteAddr());
        //设置请求路径
        sysLog.setQqlj(request.getRequestURL().toString());
        //设置请求方式
        sysLog.setQqfs(request.getMethod());
        //设置方法描述
        sysLog.setFfms(getMethodInfo(point));
        //设置请求参数
        sysLog.setQqcs(JSONObject.toJSONString(request.getParameterMap()));

        long startTime = System.currentTimeMillis();
        Object[] args = point.getArgs();
        Object retVal = point.proceed(args);
        long endTime = System.currentTimeMillis();

        SysUser user= (SysUser) SecurityUtils.getSubject().getPrincipal();
        sysLog.setUserid(user.getId());
        sysLog.setUsername(user.getUserName());
        //设置执行时间
        sysLog.setZxsj(String.valueOf(endTime - startTime));
        //设置返回值
        //sysLog.setFhz(JSONObject.toJSONString(retVal));
        //插入日志
        try {
            sysLogService.insertSelective(sysLog);
        }catch (Exception e){
            e.printStackTrace();
        }
        return retVal;
    }

    @Before("service()")
    public void service(JoinPoint point) {
        System.out.println("service method:"+getMethodInfo(point));
    }

    @Before("repository()")
    public void repository(JoinPoint point) {
        System.out.println("repository method:"+getMethodInfo(point));
    }

    private String getMethodInfo(JoinPoint point) {
        ConcurrentHashMap<String, Object> info = new ConcurrentHashMap<>(3);

        info.put("class", point.getTarget().getClass().getSimpleName());
        info.put("method", point.getSignature().getName());

        String[] parameterNames = ((MethodSignature) point.getSignature()).getParameterNames();
        ConcurrentHashMap<String, String> args = null;

        if (Objects.nonNull(parameterNames)) {
            args = new ConcurrentHashMap<>(parameterNames.length);
            for (int i = 0; i < parameterNames.length; i++) {
                String value = point.getArgs()[i] != null ? point.getArgs()[i].toString() : "null";
                args.put(parameterNames[i], value);
            }
            info.put("args", args);
        }

        return JSONObject.toJSONString(info);
    }

}
