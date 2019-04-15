package com.rjpa.AuthConfig.handler;

import model.Result;
import model.utils.AccessAddressUtil;
import model.utils.SystemConstCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * https://blog.csdn.net/zzxzzxhao/article/details/83412648
 * @ClassName:
 * @Description:登陆成功处理,用户登录成功时返回给前端的数据
 * @parm
 * @return
 * @author: drouis
 * @date: 2019/4/15 01:57
 */
public class CustomLoginAuthSuccessHandler implements AuthenticationSuccessHandler {
    private RequestCache requestCache = new HttpSessionRequestCache();
    private String authSuccessUrl = "/welcome";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //获取请求的ip地址
        String ip = AccessAddressUtil.getIpAddress(request);
        Map<String, Object> map = new HashMap<>();
        map.put("ip", ip);
        SelfUserDetails userDetails = (SelfUserDetails) authentication.getPrincipal();



        Map<String, String> data = new HashMap<>();
        data.put("index", request.getContextPath() + authSuccessUrl);
        Result baseMessage = (Result) Result.ok(SystemConstCode.SUCCESS.getCode(), "验证成功", data);
        requestCache.removeRequest(request, response);
        clearAuthenticationAttributes(request);
        response.sendRedirect(authSuccessUrl);
    }

    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }

    public String getAuthSuccessUrl() {
        return authSuccessUrl;
    }

    public void setAuthSuccessUrl(String authSuccessUrl) {
        this.authSuccessUrl = authSuccessUrl;
    }
}
