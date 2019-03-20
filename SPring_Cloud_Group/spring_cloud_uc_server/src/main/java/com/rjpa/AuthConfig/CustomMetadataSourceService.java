package com.rjpa.AuthConfig;


import com.google.gson.Gson;
import com.rjpa.feign.client.UserServiceFeignClient;
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

@Component

public class CustomMetadataSourceService implements FilterInvocationSecurityMetadataSource {
    @Autowired
    UserServiceFeignClient userServiceFeignClient;

    @Value("${security.ignoring}")
    String ignoringUrls;
    @Value("${security.intercept}")
    String interceptPaths;

    private PathMatcher matcher = new AntPathMatcher();
    private String indexUrl = "/index.jsp";


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
            // 如果不是拦截列表里的
            if (!isIntercept(url)) {
                ConfigAttribute attribute = new SecurityConfig("ROLE_ANONYMOUS");
                urlConfigAttributes.add(attribute);
                return urlConfigAttributes;
            }
            // 用户登陆后，获取用户相关的所有 权限，菜单，及匹配的权限url
            List<LzhAdminPermissionEntity> pls = new ArrayList<LzhAdminPermissionEntity>();
            Result res = userServiceFeignClient.getAdminPermissionUrls();
            // 查询到数据库中的所有权限列表
            if (null != res.getData() && 0 < ((ArrayList) res.getData()).size()) {
                Gson gson = new Gson();
                for (Map linkedHashMap : (ArrayList<Map>) res.getData()) {
                    LzhAdminPermissionEntity permissionEntity = new LzhAdminPermissionEntity();
                    permissionEntity = (LzhAdminPermissionEntity) gson.fromJson(gson.toJson(linkedHashMap), LzhAdminPermissionEntity.class);
                    pls.add(permissionEntity);
                }
            }
            if (null != res && 0 < pls.size()) {
                for (LzhAdminPermissionEntity menuPermis : pls) {
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

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }
}