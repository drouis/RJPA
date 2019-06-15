package com.rjpa.AuthConfig.filter;

import model.utils.StringUtil;
import model.utils.SystemConstCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 安全拦截器，访问用户URL进行拦截，如果没有权限返回到登陆界面
 */
public class CustomSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {
    private FilterInvocationSecurityMetadataSource securityMetadataSource;
    @Value("${security.ignoring}")
    String ignoringUrls;
    private static PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        try {
            invoke(fi);
        } catch (Exception e) {
            throw new CredentialsExpiredException(SystemConstCode.USER_LOGIN_TIMEOUT.getMessage());
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    /**
     * 验证当前用户是否有登陆信息，否则跳转到登陆界面
     *
     * @param fi
     * @throws IOException
     */
    public void invoke(FilterInvocation fi) throws IOException {
        /**
         * fi里面有一个被拦截的url
         里面调用 CustomMetadataSourceService 的 loadResourceMatchAuthority() 判定当前访问的URL是否负责拦截条件，
         判定需要拦截的url再调用 CustomerAccessDecisionManager 的 decide 方法来校验fi对应的所有权限和用户的权限
         */
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new AccountExpiredException(SystemConstCode.USER_LOGIN_TIMEOUT.getMessage());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
        return securityMetadataSource;
    }

    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
        this.securityMetadataSource = securityMetadataSource;
    }
}