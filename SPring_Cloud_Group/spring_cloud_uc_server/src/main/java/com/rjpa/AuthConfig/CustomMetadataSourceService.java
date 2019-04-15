package com.rjpa.AuthConfig;


import com.google.gson.Gson;
import com.rjpa.feign.client.UserServiceFeignClient;
import com.rjpa.service.ILoginService;
import com.rjpa.vo.AdminPermissionV;
import model.Result;
import model.utils.StringUtil;
import model.vo.LzhAdminPermissionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 获取请求url需要的权限
 */
//@Component
public class CustomMetadataSourceService implements FilterInvocationSecurityMetadataSource {


    @Value("${security.ignoring}")
    String ignoringUrls;
    @Value("${security.intercept}")
    String interceptPaths;

    private PathMatcher matcher = new AntPathMatcher();
    private String indexUrl = "/index.html";


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 获取当前访问url
        String url = ((FilterInvocation) object).getRequestUrl();
        int firstQuestionMarkIndex = url.indexOf("?");
        if (firstQuestionMarkIndex != -1) {
            url = url.substring(0, firstQuestionMarkIndex);
        }
        List<ConfigAttribute> urlConfigAttributes = new ArrayList<>();
        try {
            // 设置不拦截
            if (ignoringUrls != null) {
                String[] paths = ignoringUrls.toString().split(",");
                // 判断是否符合规则
                for (String path : paths) {
                    String temp = StringUtil.clearSpace(path);
                    if (matcher.match(temp, url)) {
                        ConfigAttribute attribute = new SecurityConfig("ROLE_ANONYMOUS");
                        urlConfigAttributes.add(attribute);
                        return urlConfigAttributes;
                    }
                }
            }
            // 拦截特定的过滤请求的url，true标示请求url地址不需要过滤，false需要过滤当前用户权限
            if (isIntercept(url)) {
                ConfigAttribute attribute = new SecurityConfig("ROLE_ANONYMOUS");
                urlConfigAttributes.add(attribute);
                return urlConfigAttributes;
            }
            // 用户登陆后，获取用户相关的所有 权限，菜单，及匹配的权限url
            List<AdminPermissionV> pls = new ArrayList<AdminPermissionV>();
            Result res = loginService.getAdminPermissionUrls();
            // 查询到数据库中的所有权限列表
            if (null != res && 0 < pls.size()) {
                for (AdminPermissionV menuPermis : pls) {
                    //查询拥有该权限的角色列表
                    ConfigAttribute conf = new SecurityConfig(menuPermis.getPermission());
                    urlConfigAttributes.add(conf);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urlConfigAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    /**
     * 判断是否需要过滤
     *
     * @param url
     * @return
     */
    public boolean isIntercept(String url) {
        String[] filterPaths = interceptPaths.split(",");
        for (String filter : filterPaths) {
            if (matcher.match(StringUtil.clearSpace(filter), url) & !matcher.match(indexUrl, url)) {
                return true;
            }
        }
        return false;
    }

    @Autowired
    ILoginService loginService;

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }
}