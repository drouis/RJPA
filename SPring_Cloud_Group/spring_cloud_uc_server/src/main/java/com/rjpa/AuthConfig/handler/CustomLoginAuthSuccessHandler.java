package com.rjpa.AuthConfig.handler;

import com.rjpa.AuthConfig.vo.User;
import com.rjpa.repository.Entity.LzhAdminLoginLogEntity;
import com.rjpa.repository.LzhAdminLoginLogRepository;
import model.Result;
import model.utils.AccessAddressUtil;
import model.utils.SystemConstCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @ClassName:
 * @Description:登陆成功处理,用户登录成功时返回给前端的数据
 * @parm
 * @return
 * @author: drouis
 * @date: 2019/4/15 01:57
 */
public class CustomLoginAuthSuccessHandler implements AuthenticationSuccessHandler {
    public final static String LOGIN_NAME_PREFIX = "shiro_login_count_";
    private RequestCache requestCache = new HttpSessionRequestCache();
    private String authSuccessUrl = "/welcome";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Map<String, Object> data = new HashMap<>();
        // TODO 1 获取请求的真实ip地址
        String ip = AccessAddressUtil.getIpAddress(request);
        data.put("ip", ip);
        data.put("index", request.getContextPath() + authSuccessUrl);
        // TODO 2 取得历史登陆数据
        LzhAdminLoginLogEntity historyLoginData = adminLoginLogRepository.getLzhAdminLoginLogEntityByLoginnameEquals(LOGIN_NAME_PREFIX + ((User) authentication.getPrincipal()).getUsername());
        LzhAdminLoginLogEntity loginData = BeanUtils.instantiateClass(LzhAdminLoginLogEntity.class);
        Timestamp t = new Timestamp(new Date().getTime());

        if (null == historyLoginData || 0 == historyLoginData.getId()) {
            loginData = new LzhAdminLoginLogEntity();
            loginData.setLoginname(LOGIN_NAME_PREFIX + ((User) authentication.getPrincipal()).getUsername());
            loginData.setLogintime(t);
            loginData.setLogincount(1);
            loginData.setMemo1(ip);
            loginData.setMemo2(request.getContextPath() + authSuccessUrl);
        } else {
            BeanUtils.copyProperties(historyLoginData, loginData);
            loginData.setLogintime(t);
            loginData.setLogincount(loginData.getLogincount() + 1);
            loginData.setMemo1(ip);
            loginData.setMemo2(request.getContextPath() + authSuccessUrl);
        }
        adminLoginLogRepository.save(loginData);
        Result baseMessage = Result.ok(SystemConstCode.SUCCESS.getCode(), "验证成功", data);
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("errMsg", baseMessage);
        requestCache.removeRequest(request, response);
        clearAuthenticationAttributes(request);
        response.setHeader("userAuthonToken", ((User) authentication.getPrincipal()).getTokenStr());
        response.sendRedirect(request.getContextPath() + authSuccessUrl);
    }

    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    @Autowired
    LzhAdminLoginLogRepository adminLoginLogRepository;

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
