package com.rjpa.AuthConfig.endPoint;

import model.utils.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权认证失败后调用
 */

public class CustomLoginAuthEntryPoint implements AuthenticationEntryPoint {
    private String loginUrl = "/login.html";
    @Value("${security.logoutSuccessUrl}")
    String logoutSuccessUrl;
    @Value("${security.ignoring}")
    String ignoringUrls;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (logoutSuccessUrl != null) {
            loginUrl = (String) logoutSuccessUrl;
        }
        boolean redirectFlg = true;
        if (ignoringUrls != null) {
            String AUTH_WHITELIST[] = ignoringUrls.split(",");
            AUTH_WHITELIST = StringUtil.clearSpace(AUTH_WHITELIST);
            for(String au : AUTH_WHITELIST){
                if(request.getServletPath().matches(au)){
                    redirectFlg = false;
                    break;
                }
            }
        }
        if(redirectFlg){
            if (isAjaxRequest(request)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
            } else {
                response.sendRedirect(loginUrl);
            }
        }
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }
}