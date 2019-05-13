package com.fy.PermissionMannger.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * 〈错误页面配置类〉
 *
 * @author fangyan
 * @create 2019/5/7
 * @since 1.0.0
 */
@Configuration
public class ErrorPageConfig implements ErrorPageRegistrar {

    private  static final Logger logger = LoggerFactory.getLogger(ErrorPageConfig.class);

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {

        ErrorPage error404 = new ErrorPage(HttpStatus.NOT_FOUND,"/error/404");

        ErrorPage error500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/error/500");

        registry.addErrorPages(error404,error500);
    }
}

