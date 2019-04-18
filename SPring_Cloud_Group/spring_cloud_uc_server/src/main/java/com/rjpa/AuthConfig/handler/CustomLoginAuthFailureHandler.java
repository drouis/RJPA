package com.rjpa.AuthConfig.handler;

import model.Result;
import model.utils.SystemConstCode;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆失败处理
 */
    public class CustomLoginAuthFailureHandler implements AuthenticationFailureHandler {
    private String authSuccessUrl = "/login";
    Result errMsg = new Result();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Result responseData = null;
        //密码错误
        if (exception instanceof BadCredentialsException) {
            responseData = Result.error(SystemConstCode.ERROR.getCode(), "密码错误");
        } else if (exception instanceof LockedException) {
            //账户被锁
            responseData = Result.error(SystemConstCode.ERROR.getCode(), "用户被锁定");
        } else if (exception instanceof DisabledException) {
            //账户未启用
            responseData = Result.error(SystemConstCode.ERROR.getCode(), "用户未启用");
        } else if (exception instanceof CredentialsExpiredException) {
            //账户过期
            responseData = Result.error(SystemConstCode.ERROR.getCode(), "用户已过期");
        } else {
            //其他情况
            responseData = Result.error(SystemConstCode.ERROR.getCode(), exception.getMessage());
        }
        request.setAttribute("errMsg", responseData);
        request.getSession().setAttribute("errMsg", responseData);
        response.sendRedirect(request.getContextPath() + authSuccessUrl);
    }
}