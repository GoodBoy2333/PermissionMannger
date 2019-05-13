package com.fy.PermissionMannger.BaseController;

import com.fy.PermissionMannger.Exception.MyException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈异常处理控制器〉
 *
 * @author fangyan
 * @create 2019/5/6
 * @since 1.0.0
 */
@ControllerAdvice
public class ExceptionController {

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    /**
     * 全局异常捕捉处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Map errorHandler(Exception ex) {
        System.out.println("全局异常捕捉处理");
        Map res = new HashMap();
        res.put("error_msg", ex.getMessage());
//        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
//        request.setAttribute("error_msg",ex.getMessage());
        return res;
    }
    /**
     * 拦截捕捉自定义异常 MyException.class
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = MyException.class)
    public ModelAndView myErrorHandler(MyException ex) {
        ModelAndView mv = new ModelAndView("Error/Exception");
        mv.addObject("code", ex.getCode());
        mv.addObject("msg", ex.getMsg());
        return mv;
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ModelAndView handleShiroException(Exception ex) {
        System.out.println("-------------UnauthorizedException异常，原因【无权限】--------");
        return new ModelAndView("Error/403");
    }
}
