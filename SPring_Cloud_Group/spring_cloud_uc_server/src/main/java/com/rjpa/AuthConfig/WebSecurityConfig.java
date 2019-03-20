package com.rjpa.AuthConfig;

import com.rjpa.AuthConfig.endPoint.CustomLoginAuthEntryPoint;
import com.rjpa.AuthConfig.filter.CustomLoginFilter;
import com.rjpa.AuthConfig.filter.CustomSecurityInterceptor;
import com.rjpa.AuthConfig.handler.CustomLoginAuthFailureHandler;
import com.rjpa.AuthConfig.handler.CustomLoginAuthSuccessHandler;
import com.rjpa.AuthConfig.handler.CustomLogoutSuccessHandler;
import model.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * spring-security配置
 */
@Configuration
@EnableWebSecurity
@Component
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }

    @Value("${security.ignoring}")
    String ignoringUrls;
    @Value("${security.successUrl}")
    String successUrls;
    @Value("${security.logoutSuccessUrl}")
    String logoutSuccessUrl;
    private static String[] AUTH_WHITELIST = {};
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.debug("权限框架配置");
        //设置不拦截
        if (ignoringUrls != null) {
            AUTH_WHITELIST = ignoringUrls.split(",");
            AUTH_WHITELIST = StringUtil.clearSpace(AUTH_WHITELIST);
        }
        for(String au :AUTH_WHITELIST){
            http.authorizeRequests().regexMatchers(au).permitAll();
        }
        //设置过滤器
        http.authorizeRequests().and()
                .httpBasic().authenticationEntryPoint(getCustomLoginAuthEntryPoint())
                .and().addFilterAt(getCustomLoginFilter(), UsernamePasswordAuthenticationFilter.class).addFilterAt(getCustomSecurityInterceptor(), FilterSecurityInterceptor.class).logout().logoutSuccessHandler(getCustomLogoutSuccessHandler())//用户权限登陆拦截器
                .and().csrf().disable()
                .authorizeRequests().anyRequest().authenticated()
                .and().formLogin().loginProcessingUrl("/login").loginPage("/login.ftl").permitAll()
                .and().logout().logoutUrl("/logout").permitAll();
        logger.debug("配置忽略验证url");
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
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setHideUserNotFoundExceptions(false);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    /**
     * 登陆
     *
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
        filter.setAuthenticationSuccessHandler(getCustomLoginAuthSuccessHandler());
        filter.setAuthenticationFailureHandler(getCustomLoginAuthFailureHandler());
        return filter;
    }

    @Bean
    public CustomLoginAuthSuccessHandler getCustomLoginAuthSuccessHandler() {
        CustomLoginAuthSuccessHandler handler = new CustomLoginAuthSuccessHandler();
        if (successUrls != null) {
            handler.setAuthSuccessUrl(successUrls.toString());
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
