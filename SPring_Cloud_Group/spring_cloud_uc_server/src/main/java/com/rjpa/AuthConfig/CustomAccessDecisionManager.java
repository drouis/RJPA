package com.rjpa.AuthConfig;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 验证url需要权限
 */
@Component
public class CustomAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        // TODO 1.1 没有对应的访问权限限制，正常返回
        if (collection == null) {
            return;
        }
        //TODO 2.1 查询当前用户角色下是否有该资源的访问权限
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

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}