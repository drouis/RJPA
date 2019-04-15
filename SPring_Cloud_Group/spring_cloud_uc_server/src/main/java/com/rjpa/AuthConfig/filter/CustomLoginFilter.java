package com.rjpa.AuthConfig.filter;

import com.rjpa.AuthConfig.vo.User;
import com.rjpa.redis.RedisDao;
import model.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * 用户登陆
 */
public class CustomLoginFilter extends AbstractAuthenticationProcessingFilter {
    private static final String SPRING_SECURITY_RESTFUL_PHONENUMBER_KEY = "phoneNumber";
    private static final String SPRING_SECURITY_RESTFUL_USERNAME_KEY = "username";
    private static final String SPRING_SECURITY_RESTFUL_PASSWORD_KEY = "password";
    private static final String SPRING_SECURITY_RESTFUL_LOGIN_URL = "/Authlogin";

    private String usernameParameter = SPRING_SECURITY_RESTFUL_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_RESTFUL_PASSWORD_KEY;
    private boolean postOnly = true;

    public CustomLoginFilter() {
        super(new AntPathRequestMatcher(SPRING_SECURITY_RESTFUL_LOGIN_URL, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        Authentication authentication = null;
        if (postOnly && !httpServletRequest.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + httpServletRequest.getMethod());
        }
        String username = obtainUsername(httpServletRequest);
        String password = obtainPassword(httpServletRequest);
        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }
        username = username.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);
        // Allow subclasses to set the "details" property
        setDetails(httpServletRequest, authRequest);
        // TODO 进入WebSecurityConfig 中配置的authenticationProvider，MyUserDetailsService
        try {
            authentication = this.getAuthenticationManager().authenticate(authRequest);
            if (authentication != null) {
                // TODO 用户数据加入REDIS缓存
//                String token = JwtTokenUtil.generateToken(username, 3600);
                String tokenStr = UUID.randomUUID().toString().replaceAll("-", "");
                ((User) authentication.getPrincipal()).setTokenStr(tokenStr); //TODO MyUserDetailsService.loadUserByUsername 用户信息
                redisDao.setCacheObject(tokenStr, JwtTokenUtil.TOKEN_HEADER + username);
//                sessionRegistry.registerNewSession(httpServletRequest.getSession().getId(), authRequest.getPrincipal());
            }
        } catch (LockedException e1) {
            throw e1;
        } catch (DisabledException e2) {
            throw e2;
        } catch (AccountExpiredException e3) {
            throw e3;
        }
        return authentication;
    }

    /**
     * 登陆验证成功时候调用的方法
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 查看源代码会发现调用getPrincipal()方法会返回一个实现了`UserDetails`接口的对象
        User user = (User) authResult.getPrincipal();
        System.out.println("jwtUser:" + user.toString());
        response.setHeader("token", JwtTokenUtil.TOKEN_HEADER + user.getTokenStr());
        super.successfulAuthentication(request, response, chain, authResult);
    }

    /**
     * 登陆验证失败时候调用的方法
     *
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }

    @Autowired
    RedisDao redisDao;
//    @Resource
//    SessionRegistry sessionRegistry;


    private void setDetails(HttpServletRequest request,
                            UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    private String obtainPassword(HttpServletRequest request) {
        return request.getParameter(passwordParameter);
    }

    private String obtainUsername(HttpServletRequest request) {
        return request.getParameter(usernameParameter);
    }

    public String getUsernameParameter() {
        return usernameParameter;
    }

    public void setUsernameParameter(String usernameParameter) {
        this.usernameParameter = usernameParameter;
    }

    public String getPasswordParameter() {
        return passwordParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        this.passwordParameter = passwordParameter;
    }

    public boolean isPostOnly() {
        return postOnly;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
}