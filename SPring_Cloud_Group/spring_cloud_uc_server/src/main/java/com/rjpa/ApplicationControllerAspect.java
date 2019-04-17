package com.rjpa;

import com.google.gson.Gson;
import com.rjpa.AuthConfig.vo.User;
import com.rjpa.controller.IndexMvcController;
import com.rjpa.vo.IndexPageMenuV;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * AOP 切面处理
 * 参考资料：https://blog.csdn.net/fangwenzheng88/article/details/78689327
 */
@Aspect   //定义一个切面
@Component
@Order(1)
public class ApplicationControllerAspect {
    public static final Logger logger = LoggerFactory.getLogger(ApplicationControllerAspect.class);
    private Gson gson = new Gson();

    // 定义切点Pointcut,申明一个切点 里面是 execution表达式
    @Pointcut("execution(public * com.rjpa.*controller.*(..))")
    public void excudeService() {
    }

    /**
     * 前置通知：在连接点之前执行的通知
     * 请求method前打印内容
     * @param joinPoint
     * @throws Throwable
     */
    @Before("excudeService()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //打印请求内容
        logger.info("===============请求内容===============");
        logger.info("请求地址:" + request.getRequestURL().toString());
        logger.info("请求方式:" + request.getMethod());
        logger.info("请求类方法:" + joinPoint.getSignature());
        logger.info("请求类方法参数:" + Arrays.toString(joinPoint.getArgs()));
        logger.info("===============请求内容===============");
    }

    /**
     * 在方法执行完结后打印返回内容
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "excudeService()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("--------------返回内容----------------");
        logger.info("Response内容:" + gson.toJson(ret));
        logger.info("--------------返回内容----------------");
    }

    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        {
            User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<IndexPageMenuV> menuVS = u.getMenuVS();
            request.setAttribute(IndexMvcController.MENU_REDIS_NAME, menuVS);
            request.setCharacterEncoding("UTF-8");
            {
                // 登录名
                System.out.println("Username:" + SecurityContextHolder.getContext().getAuthentication().getName());
                // 登录密码，未加密的
                System.out.println("Credentials:" + SecurityContextHolder.getContext().getAuthentication().getCredentials());
                WebAuthenticationDetails details = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication()
                        .getDetails();
                // 获得访问地址
                System.out.println("RemoteAddress" + details.getRemoteAddress());
                // 获得sessionid
                System.out.println("SessionId" + details.getSessionId());
                // 获得当前用户所拥有的权限
//                List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication()
//                        .getAuthorities();
//                for (GrantedAuthority grantedAuthority : authorities) {
//                    System.out.println("Authority" + grantedAuthority.getAuthority());
//                }
            }
            request.setAttribute("admin", u);
        }
        logger.info("请求开始, 各个参数, url: {}, method: {}, uri: {}, params: {}", url, method, uri, queryString);
        // result的值就是被拦截方法的返回值
        Object result = proceedingJoinPoint.proceed();
        logger.info("请求结束，controller的返回值是 " + gson.toJson(result));
        return result;
    }


}
