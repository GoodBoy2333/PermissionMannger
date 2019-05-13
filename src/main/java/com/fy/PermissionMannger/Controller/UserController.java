package com.fy.PermissionMannger.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fy.PermissionMannger.Entity.DatagridResult;
import com.fy.PermissionMannger.Entity.SysUser;
import com.fy.PermissionMannger.Service.SysUserService;
import com.fy.PermissionMannger.Uitls.SecurityUtils.ShiroSaltUtil;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 〈usercontroller〉
 *
 * @author fangyan
 * @create 2019/5/1
 * @since 1.0.0
 */
@Controller
@RequestMapping("/userInfo")
public class UserController {
    @Autowired
    SysUserService sysUserService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/userList")
    public String userList() {
        return "User/userList";
    }

    /**
     * 查找所有用户
     */
    @RequestMapping("/findAllUsersPage")
    @ResponseBody
//    @Cacheable("userList") 缓存
//    @RequiresPermissions("userInfo:view") 权限注解
    public DatagridResult findAllUsersPage(@RequestParam(defaultValue = "1") String page){
        //WebUtils.getPathWithinApplication(WebUtils.toHttp(request)) 获得请求url
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        DatagridResult datagridResult;
        //redisKey:userList-->userList+userId+page
        if (redisTemplate.opsForHash().hasKey("userList", "userList:" + sysUser.getId() + "page:" + page)) {
            String temp = (String) redisTemplate.opsForHash().get("userList", "userList:" + sysUser.getId() + "page:" + page);
            datagridResult = JSON.toJavaObject(JSON.parseObject(temp), DatagridResult.class);
        } else {
            List<SysUser> users = sysUserService.findByAll(null);
            //PageInfo封装了数据的所有的分页信息
            PageInfo<SysUser> pageInfo = new PageInfo<>(users);
            datagridResult = new DatagridResult(pageInfo.getTotal(), users);
            redisTemplate.opsForHash().put("userList", "userList:" + sysUser.getId() + "page:" + page, JSONObject.toJSON(datagridResult).toString());
            redisTemplate.expire("userList", 1, TimeUnit.HOURS);
        }
        return datagridResult;
    }

    @RequestMapping("/insert")
    public ModelAndView insert(SysUser user) {
        if(user==null|| org.springframework.util.StringUtils.isEmpty(user.getUserPwd())){
            throw new RuntimeException("用户数据为空");
        }
        //待完善
        ModelAndView mv = new ModelAndView("success");
        String password = ShiroSaltUtil.getPasswordSalt(user.getUserPwd(), user.getUserName(), 2);
        if (!org.springframework.util.StringUtils.isEmpty(password)) {
            user.setUserPwd(password);
        }
        int res = sysUserService.insertSelective(user);
        mv.addObject("res", res);
        return mv;
    }

    @RequestMapping("/findById/{id}")
    public ModelAndView findById(@PathVariable String id) {
        //待完善
        ModelAndView mv = new ModelAndView("showUser");
        if (StringUtils.isEmpty(id)) {
            return mv;
        }
        SysUser sysUser = sysUserService.selectByPrimaryKey(id);
        mv.addObject("user", sysUser);
        return mv;
    }

    @RequestMapping("/getUserJsonById/{id}")
    @ResponseBody
    public SysUser getUserJsonById(@PathVariable String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        SysUser sysUser = sysUserService.selectByPrimaryKey(id);
        return sysUser;
    }

    @RequestMapping("/deleteById/{id}")
    @ResponseBody
    public int deleteById(@PathVariable String id) {
        if (StringUtils.isEmpty(id)) {
            return 0;
        }
        int i = sysUserService.deleteByPrimaryKey(id);
        return i;
    }

    @RequestMapping("/updateById")
    @ResponseBody
    public int updateById(SysUser user) {
        if (StringUtils.isEmpty(user.getId())) {
            return 0;
        }
        int i = sysUserService.updateByPrimaryKey(user);
        return i;
    }

}
