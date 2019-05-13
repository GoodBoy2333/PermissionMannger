package com.fy.PermissionMannger.Controller;

import com.fy.PermissionMannger.Entity.SysUser;
import com.fy.PermissionMannger.Service.SysPermissionService;
import com.fy.PermissionMannger.Service.SysUserService;
import com.fy.PermissionMannger.Uitls.TreeUtils.TreeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 〈用户信息管理〉
 *
 * @author fangyan
 * @create 2019/5/8
 * @since 1.0.0
 */
@Controller
public class HomeController {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysPermissionService sysPermissionService;

    @RequestMapping({"/","/mainHome"})
    public ModelAndView mainHome() {
        ModelAndView mv = new ModelAndView("Home/index");
        SysUser sysUser= (SysUser) SecurityUtils.getSubject().getPrincipal();
        boolean hasPermissionObjects= SecurityUtils.getSubject().getSession().getAttribute("userPermissions")==null;
        if (sysUser!=null&&!StringUtils.isEmpty(sysUser.getId())&&hasPermissionObjects) {
            System.out.println("-----------------初始化菜单--------------------");
            setUserResources(sysUser);
        }
        return mv;
    }

    @RequestMapping(value = "login")
    public String login(HttpServletRequest request, Map<String, Object> map) {
        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        String exception = (String) request.getAttribute("shiroLoginFailure");
        System.out.println("exception=" + exception);
        String msg = "";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                System.out.println("UnknownAccountException -- > 账号不存在");
                msg = "账号不存在：";
            } else if (AccountException.class.getName().equals(exception)) {
                System.out.println("AccountException -- > 用户不存在：");
                msg = "用户不存在：";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                System.out.println("IncorrectCredentialsException -- > 用户名或密码不正确：");
                msg = "用户名或密码不正确：";
            } else {
                exception=(String) request.getAttribute("loginFailure");
                msg = exception;
                System.out.println(exception);
            }
        }
        map.put("error_msg", msg);
        // 此方法不处理登录成功,由shiro进行处理
        return "login";
    }

    public void setUserResources(SysUser sysUser){
        SysUser user=sysUserService.selectPermissionByPrimaryKey(sysUser.getId());
        List per=user.getSysPermissions();
        if(per==null||per.size()==0){
            return ;
        }
        List treelist = TreeUtils.formatTree(per, false);
        if(treelist==null||treelist.size()==0){
            return ;
        }
        user.setSysPermissions(treelist);
        SecurityUtils.getSubject().getSession().setAttribute("userPermissions", user);
//        JSONArray permissions= (JSONArray) JSONArray.toJSON(user.getSysPermissions());
    }
}
