package com.rjpa.AuthConfig.endPoint;

import model.utils.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权认证
 */
public class CustomLoginAuthEntryPoint implements AuthenticationEntryPoint {
    private String loginUrl = "/login";
    @Value("${security.logoutSuccessUrl}")
    String logoutSuccessUrl;
    @Value("${security.ignoring}")
    String ignoringUrls;
    private static PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        // 判定白名单内容
        if (logoutSuccessUrl != null) {
            loginUrl = (String) logoutSuccessUrl;
        }
        boolean redirectFlg = true;
        if (ignoringUrls != null) {
            String AUTH_WHITELIST[] = ignoringUrls.split(",");
            AUTH_WHITELIST = StringUtil.clearSpace(AUTH_WHITELIST);
            for (String au : AUTH_WHITELIST) {
                String temp = StringUtil.clearSpace(request.getServletPath());
                boolean result = pathMatcher.match(au, temp);
                if (result) {
                    redirectFlg = false;
                    break;
                }
            }
        }
        if (redirectFlg) {
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

    public static void main(String[] args) {
        PathMatcher matcher = new AntPathMatcher();
        // 完全路径url方式路径匹配
        String requestPath="/agentInfo/regMyBundBankCard";//请求路径
        String patternPath="/agentInfo/**/*";//路径匹配模式
        // 不完整路径uri方式路径匹配
        // String requestPath="/app/pub/login.do";//请求路径
        // String patternPath="/**/login.do";//路径匹配模式
        // 模糊路径方式匹配
        // String requestPath="/app/pub/login.do";//请求路径
        // String patternPath="/**/*.do";//路径匹配模式
        // 包含模糊单字符路径匹配
        //String requestPath = "/app/pub/login.do";// 请求路径
        //String patternPath = "/**/lo?in.do";// 路径匹配模式
        boolean result = matcher.match(patternPath, requestPath);
        System.out.println(result);
    }
}