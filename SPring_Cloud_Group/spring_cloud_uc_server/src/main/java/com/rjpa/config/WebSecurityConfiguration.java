package com.rjpa.config;

import com.google.gson.Gson;
import com.rjpa.AuthConfig.CustomAccessDecisionManager;
import com.rjpa.AuthConfig.CustomMetadataSourceService;
import com.rjpa.AuthConfig.endPoint.CustomLoginAuthEntryPoint;
import com.rjpa.AuthConfig.filter.CustomLoginFilter;
import com.rjpa.AuthConfig.filter.CustomSecurityInterceptor;
import com.rjpa.AuthConfig.handler.CustomAccessDeniedHandler;
import com.rjpa.AuthConfig.handler.CustomLoginAuthFailureHandler;
import com.rjpa.AuthConfig.handler.CustomLoginAuthSuccessHandler;
import com.rjpa.AuthConfig.handler.CustomLogoutSuccessHandler;
import com.rjpa.AuthConfig.service.MyUserDetailsService;
import com.rjpa.AuthConfig.sessionManager.CustomSessionControlAuthenticationStrategy;
import com.rjpa.AuthConfig.sessionManager.CustomSessionRegistryImpl;
import model.Result;
import model.utils.StringUtil;
import model.utils.SystemConstCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

/**
 * spring-security配置
 * spring security四种实现方式
 * 参考文件：https://blog.csdn.net/bao19901210/article/details/52574340
 * 参考文件：https://blog.csdn.net/sinat_29899265/article/details/80771330
 */
@Component
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)  //  启用方法级别的权限认证
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    Gson gson = new Gson();
    String successLoginIndexUrls = "/welcome";
    @Value("${security.ignoring}")
    String ignoringUrls;
    @Value("${security.successUrl}")
    String successUrls;
    @Value("${security.logoutSuccessUrl}")
    String logoutSuccessUrl;
    private static String[] AUTH_WHITELIST = {};


    /**
     * 配置验证Url地址和验证方式
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.debug("权限框架配置");
        //设置拦截白名单
        if (ignoringUrls != null) {
            AUTH_WHITELIST = ignoringUrls.split(",");
            AUTH_WHITELIST = StringUtil.clearSpace(AUTH_WHITELIST);
        }
        for (String au : AUTH_WHITELIST) {
            http.authorizeRequests().regexMatchers(au).permitAll();// 采用正则表达式匹配白名单的url地址
        }
        //设置过滤器
        // TODO 解决不允许显示在iframe的问题
        http.headers().frameOptions().disable();
        // TODO 阻止CSRF 的攻击
        http.csrf().disable();
        // TODO 访问用户权限验证方式拦截器
        http.httpBasic().authenticationEntryPoint(getCustomLoginAuthEntryPoint())
                .and().addFilterAt(getCustomLoginFilter(), UsernamePasswordAuthenticationFilter.class);
        // TODO 自定义验证访问URL权限拦截器
        http.addFilterAt(getCustomSecurityInterceptor(), FilterSecurityInterceptor.class);
        // TODO session管理
        // TODO session并发控制过滤器
        http.addFilterAt(new ConcurrentSessionFilter(sessionRegistry, sessionInformationExpiredStrategy()), ConcurrentSessionFilter.class);
        Result r = Result.error(SystemConstCode.USER_LOGIN_TIMEOUT.getCode(), SystemConstCode.USER_LOGIN_TIMEOUT.getMessage());
        //TODO session失效后跳转
        http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(false).expiredUrl("/login?errMsg=" + gson.toJson(r)).sessionRegistry(sessionRegistry);
        http.authorizeRequests().anyRequest().authenticated().and().exceptionHandling().accessDeniedHandler(getCustomAccessDeniedHandler());
        http.logout().logoutSuccessHandler(getCustomLogoutSuccessHandler())
                .and().formLogin().loginProcessingUrl("/Authlogin").loginPage("/login").permitAll()// 设置登陆信息和登陆权限
                .and().logout().logoutUrl("/logout").permitAll(); // 配置登出信息和登出权限


        logger.debug("配置忽略验证url");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().regexMatchers("/assets/*");
    }

    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(getDaoAuthenticationProvider());
        //不删除凭据，以便记住用户
        auth.eraseCredentials(false);
    }

    //session失效跳转
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new SimpleRedirectSessionInformationExpiredStrategy("/login");
    }

    //SpringSecurity内置的session监听器
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    /**
     * spring security 配置
     *
     * @return
     */
    @Bean
    public CustomLoginAuthEntryPoint getCustomLoginAuthEntryPoint() {
        return new CustomLoginAuthEntryPoint();
    }

    /**
     * 用户验证
     *
     * @return
     */
    @Bean
    public DaoAuthenticationProvider getDaoAuthenticationProvider() {
        // 配置用户信息来自于数据库设置
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // 自定义UserDetailsService信息,设置加密算法，通过自定服务获取用户信息
        provider.setUserDetailsService(userService);
        provider.setHideUserNotFoundExceptions(false);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    /**
     * @return
     * @ClassName:
     * @Description: TODO 登陆拦截器 登陆成功后跳转登陆成功CustomLoginAuthSuccessHandler，否则跳转登陆失败CustomLoginAuthFailureHandler
     * @parm
     * @author: drouis
     * @date: 2019/4/16 01:32
     */
    @Bean
    public CustomLoginFilter getCustomLoginFilter() {
        CustomLoginFilter filter = new CustomLoginFilter();
        try {
            filter.setAuthenticationManager(this.authenticationManagerBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 设定登陆成功后的跳转过滤
        filter.setAuthenticationSuccessHandler(getCustomLoginAuthSuccessHandler());
        // 设定登陆失败后的跳转过滤
        filter.setAuthenticationFailureHandler(getCustomLoginAuthFailureHandler());
        filter.setSessionAuthenticationStrategy(sessionControlAuthenticationStrategy);
        return filter;
    }

    /**
     * 登陆成功跳转handler
     *
     * @return
     */
    @Bean
    public CustomLoginAuthSuccessHandler getCustomLoginAuthSuccessHandler() {
        CustomLoginAuthSuccessHandler handler = new CustomLoginAuthSuccessHandler();
        if (successUrls != null) {
            handler.setAuthSuccessUrl(successUrls.toString());
        } else {
            handler.setAuthSuccessUrl(successLoginIndexUrls);
        }
        return handler;
    }

    /**
     * 登陆失败跳转handler
     *
     * @return
     */
    @Bean
    public CustomLoginAuthFailureHandler getCustomLoginAuthFailureHandler() {
        return new CustomLoginAuthFailureHandler();
    }

    /**
     * 登出
     *
     * @return
     */
    @Bean
    public CustomLogoutSuccessHandler getCustomLogoutSuccessHandler() {
        CustomLogoutSuccessHandler handler = new CustomLogoutSuccessHandler();
        if (logoutSuccessUrl != null) {
            handler.setLoginUrl(logoutSuccessUrl.toString());
        }
        return handler;
    }

    public CustomAccessDeniedHandler getCustomAccessDeniedHandler() {
        CustomAccessDeniedHandler handler = new CustomAccessDeniedHandler();
        return handler;
    }

    /**
     * TODO 自定义过滤器，并设置拦截时候的决策类CustomAccessDecisionManager
     *
     * @return
     */
    @Bean
    public CustomSecurityInterceptor getCustomSecurityInterceptor() {
        CustomSecurityInterceptor interceptor = new CustomSecurityInterceptor();
        interceptor.setAccessDecisionManager(getCustomAccessDecisionManager());
        interceptor.setSecurityMetadataSource(getCustomMetadataSourceService());
        try {
            interceptor.setAuthenticationManager(this.authenticationManagerBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return interceptor;
    }

    @Bean
    public CustomAccessDecisionManager getCustomAccessDecisionManager() {
        return new CustomAccessDecisionManager();
    }

    @Bean
    public CustomMetadataSourceService getCustomMetadataSourceService() {
        CustomMetadataSourceService sourceService = new CustomMetadataSourceService();
        if (successUrls != null) {
            sourceService.setIndexUrl(successUrls);
        }
        return sourceService;
    }

    @Autowired
    MyUserDetailsService userService;
    @Autowired
    CustomSessionRegistryImpl sessionRegistry;
    @Autowired
    CustomSessionControlAuthenticationStrategy sessionControlAuthenticationStrategy;
}