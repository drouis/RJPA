package com.rjpa.AuthConfig;


import com.rjpa.service.ILoginService;
import com.rjpa.vo.AdminPermissionV;
import model.Result;
import model.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
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

/**
 * 获取请求url需要的权限
 */
@Component
public class CustomMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    @Value("${security.ignoring}")
    String ignoringUrls;
    @Value("${security.intercept}")
    String interceptPaths;

    private PathMatcher matcher = new AntPathMatcher();
    private String indexUrl = "/index.html";


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // TODO 获取当前访问url
        String url = ((FilterInvocation) object).getRequestUrl();
        int firstQuestionMarkIndex = url.indexOf("?");
        if (firstQuestionMarkIndex != -1) {
            url = url.substring(0, firstQuestionMarkIndex);
        }
        List<ConfigAttribute> result = new ArrayList<>();
        try {
            // 设置不拦截
            if (ignoringUrls != null) {
                String[] paths = ignoringUrls.toString().split(",");
                // 判断是否符合规则
                for (String path : paths) {
                    String temp = StringUtil.clearSpace(path);
                    if (matcher.match(temp, url)) {
                        ConfigAttribute attribute = new SecurityConfig("ROLE_ANONYMOUS");
                        result.add(attribute);
                        return result;
                    }
                }
            }
            // 拦截特定的过滤请求的url，true标示请求url地址不需要过滤，false需要过滤当前用户权限
            if (isIntercept(url)) {
                ConfigAttribute attribute = new SecurityConfig("ROLE_ANONYMOUS");
                result.add(attribute);
                return result;
            }
            if(url.indexOf("_")>0){
                url = url.substring(0,url.indexOf("_")+1);
            }
            // TODO 通过URl判定用户角色及访问权限
            List<AdminPermissionV> res = (List)loginService.getAdminPermissionByUrl(url).getData();
            // 查询到数据库中的所有权限列表
            if (null != res && 0 < res.size()) {
                for (AdminPermissionV menuPermis : res) {
                    ConfigAttribute conf = new SecurityConfig(menuPermis.getPermission());
                    result.add(conf);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result; // CustomAccessDecisionManager
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