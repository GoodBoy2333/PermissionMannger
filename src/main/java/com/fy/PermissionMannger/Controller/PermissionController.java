package com.fy.PermissionMannger.Controller;

import com.fy.PermissionMannger.Entity.DatagridResult;
import com.fy.PermissionMannger.Entity.SysPermission;
import com.fy.PermissionMannger.Service.SysPermissionService;
import com.fy.PermissionMannger.Uitls.TreeUtils.TreeUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 〈权限管理〉
 *
 * @author fangyan
 * @create 2019/5/12
 * @since 1.0.0
 */
@Controller
@RequestMapping("/permission/")
public class PermissionController {
    @Autowired
    SysPermissionService sysPermissionService;

    @RequestMapping("permissionList")
    public ModelAndView permissList(HttpServletRequest request, SysPermission sysPermission){
        ModelAndView mv=new ModelAndView("Permission/permissionList");
        List sysperList=sysPermissionService.findByAll(sysPermission);
        if(sysperList!=null&&sysperList.size()>0){
            sysperList= TreeUtils.formatTree(sysperList,false);
        }
        mv.addObject("dataList",sysperList);
        return mv;
    }
    @RequestMapping("findAllPermissionPage")
    @ResponseBody
    public DatagridResult findAllPermissionPage(HttpServletRequest request, SysPermission sysPermission){
        List<SysPermission> sysperList=sysPermissionService.findByAll(sysPermission);
        PageInfo<SysPermission> sysPermissionPageInfo=new PageInfo<>(sysperList);
        DatagridResult datagridResult=new DatagridResult(sysPermissionPageInfo.getTotal(),sysperList);
        return datagridResult;
    }
}
