package com.rjpa.AuthConfig.filter;

import com.rjpa.AuthConfig.vo.User;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 验证url需要权限
 */
//@Component
public class CustomAccessDecisionManager implements AccessDecisionManager {

    /**
     * 通过传递的参数来决定用户是否有访问对应受保护对象的权限
     *
     * @param authentication 当前正在请求受包含对象的Authentication
     * @param o              受保护对象，其可以是一个MethodInvocation、JoinPoint或FilterInvocation。
     * @param collection     与正在请求的受保护对象相关联的配置属性
     **/
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        // TODO 1.1 没有对应的访问权限限制，正常返回
        if (collection == null) {
            return;
        }
        try {
            //TODO 2.1 查询当前用户角色下是否有该资源的访问权限
            if (authentication.getPrincipal().getClass() == User.class) {
                User u = (User) authentication.getPrincipal();
                if (u.isAdminFlg()) {
                    return;
                }
            }
        } catch (Exception e) {

        }
        for (ConfigAttribute configAttribute : collection) {
            // TODO 2.2 访问URL对应的权限列表
            String needRole = configAttribute.getAttribute();
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                // TODO 2.3 访问用户对应的权限列表
                if (needRole.trim().equals(ga.getAuthority().trim()) || needRole.trim().equals("ROLE_ANONYMOUS")) {
                    // TODO 2.4 如果找到权限则正常返回
                    return;
                }
            }
        }
        throw new AccessDeniedException("无权限！");
    }

    //表示当前AccessDecisionManager是否支持对应的ConfigAttribute
    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    //表示当前AccessDecisionManager是否支持对应的受保护对象类型
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}