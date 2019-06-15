package com.rjpa.filter;

import Entity.AdminUserV;
import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.rjpa.redis.GateWayRedisTemplateUtils;
import model.utils.RedisConstants;
import model.Result;
import model.utils.StringUtil;
import model.utils.SystemConstCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AccessFilter extends ZuulFilter {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private static PathMatcher pathMatcher = new AntPathMatcher();

    Gson gson = new Gson();

    @Autowired
    GateWayRedisTemplateUtils redisUtils;

    @Value("${security.ignoring}")
    String ignoringUrls;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        Result r = Result.ok(null);
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        logger.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());

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
//            TODO 从HEad中获取登陆的token
//            Object accessToken = request.getParameter("accessToken");
            Object accessToken = request.getHeader("accessToken");
            if (accessToken == null) {
                r = Result.error(SystemConstCode.USER_LOIGN_TOKEN_EMPTY.getCode(), SystemConstCode.USER_LOIGN_TOKEN_EMPTY.getMessage());
                logger.warn("access token is empty");
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(401);
                HttpServletResponse response = requestContext.getResponse();
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                response.setLocale(new java.util.Locale("zh","CN"));
                PrintWriter out= null;
                try {
                    out = response.getWriter();
                    out.print(gson.toJson(r));
                } catch (IOException e) {
                    e.printStackTrace();
                }
//            requestContext.setResponseBody(gson.toJson(r));
                return null;
            } else {
                String ustr = (String) redisUtils.get("cc9b31b1b57445c8bf101e0f0f8414cc", RedisConstants.datebase1);
                if (StringUtils.isEmpty(ustr)) {
                    r = Result.error(SystemConstCode.USER_LOIGN_TOKEN_EMPTY.getCode(), SystemConstCode.USER_LOIGN_TOKEN_EMPTY.getMessage());
                    requestContext.setSendZuulResponse(false);
                    requestContext.setResponseStatusCode(401);
                    HttpServletResponse response = requestContext.getResponse();
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("text/html;charset=UTF-8");
                    response.setLocale(new java.util.Locale("zh","CN"));
                    PrintWriter out= null;
                    try {
                        out = response.getWriter();
                        out.print(gson.toJson(r));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//            requestContext.setResponseBody(gson.toJson(r));
                    return null;
                }
                AdminUserV u = (AdminUserV) gson.fromJson(ustr, AdminUserV.class);
                System.out.println(u);
            }
        }
        return null;
    }
}