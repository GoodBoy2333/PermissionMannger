package com.fy.PermissionMannger.Security;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 〈解决登录成功后跳转至session保存的urlBug〉
 *
 * @author fangyan
 * @create 2019/5/9
 * @since 1.0.0
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        if (!StringUtils.isEmpty(getSuccessUrl())) {
            // getSession(false)：如果当前session为null,则返回null,而不是创建一个新的session
            Session session = subject.getSession(false);
            if (session != null) {
                //以下代码主要是为了解决跳转到/favicon.ico的bug
                SavedRequest savedRequest= (SavedRequest) session.getAttribute("shiroSavedRequest");
                boolean method=true;
                boolean uri=true;
                if(savedRequest!=null&&savedRequest.getMethod()!=null&&savedRequest.getRequestURI()!=null){
                    method=savedRequest.getMethod().equalsIgnoreCase("get");
                    uri=savedRequest.getRequestURI().equals("/favicon.ico");
                    if(method&&uri){
                        session.removeAttribute("shiroSavedRequest");
                    }
                }
                //session.removeAttribute("shiroSavedRequest");
            }
        }
        return super.onLoginSuccess(token, subject, request, response);
    }
}
