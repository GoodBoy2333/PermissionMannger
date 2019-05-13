package com.fy.PermissionMannger.BaseController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 〈错误页面配置〉
 *
 * @author fangyan
 * @create 2019/5/7
 * @since 1.0.0
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping(value = "/404")
    public String error404(){
        return "Error/404";
    }

    @GetMapping(value = "/500")
    public String error500(){
        return "Error/500";
    }

    @GetMapping(value = "/403")
    public String unauthorizedRole(){
        System.out.println("--------------------------shrio拦截，原因【没有权限】--------------------------");
        return "Error/403";
    }
}
