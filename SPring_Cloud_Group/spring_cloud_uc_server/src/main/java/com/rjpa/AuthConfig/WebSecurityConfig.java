package com.rjpa.AuthConfig;

import com.rjpa.AuthConfig.endPoint.CustomLoginAuthEntryPoint;
import com.rjpa.AuthConfig.filter.CustomLoginFilter;
import com.rjpa.AuthConfig.filter.CustomSecurityInterceptor;
import com.rjpa.AuthConfig.handler.CustomLoginAuthFailureHandler;
import com.rjpa.AuthConfig.handler.CustomLoginAuthSuccessHandler;
import com.rjpa.AuthConfig.handler.CustomLogoutSuccessHandler;
import com.rjpa.AuthConfig.service.MyUserDetailsService;
import model.utils.StringUtil;
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
import org.springframework.stereotype.Component;

/**
 * spring-security配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //  启用方法级别的权限认证
@Component
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    MyUserDetailsService userService;
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
        // http.authorizeRequests().regexMatchers("/assets/*").permitAll().anyRequest().authenticated().and()
        http.httpBasic().authenticationEntryPoint(getCustomLoginAuthEntryPoint())
                .and()
                .addFilterAt(getCustomLoginFilter(), UsernamePasswordAuthenticationFilter.class)// 访问用户权限验证方式拦截器
                .addFilterAt(getCustomSecurityInterceptor(), FilterSecurityInterceptor.class)// 验证访问URL权限拦截器
                .logout().logoutSuccessHandler(getCustomLogoutSuccessHandler())
                .and().csrf().disable()// 阻止CSRF 的攻击
                .authorizeRequests().anyRequest().authenticated()
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
        // 配置自定义数据库访问信息，通过远程用户服务获取用户信息
        provider.setUserDetailsService(userService);
        provider.setHideUserNotFoundExceptions(false);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    /**
     * 登陆
     * @return
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
        return filter;
    }

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

    /**
     * 过滤器
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
}
