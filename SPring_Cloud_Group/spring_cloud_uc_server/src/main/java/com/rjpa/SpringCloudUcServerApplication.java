package com.rjpa;

import com.rjpa.service.ISystemMenuService;
import com.rjpa.service.ISystemPermissionService;
import com.rjpa.service.impl.SystemMenuServiceImpl;
import com.rjpa.vo.AdminMenuV;
import com.rjpa.vo.AdminPermissionV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.util.List;


@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
@EnableZuulProxy
@EnableHystrix
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableFeignClients("com.rjpa.feign")
public class SpringCloudUcServerApplication extends SpringBootServletInitializer implements CommandLineRunner {
    protected final static Logger logger = LoggerFactory.getLogger(SpringCloudUcServerApplication.class);

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringCloudUcServerApplication.class);
        springApplication.addListeners(new ApplicationPermissionScanStartup());
        springApplication.run(args);
    }

    @Configuration
    @ImportResource(locations = {"classpath: bootstrap-*.yml"})
    public class CustomPropertySourceLocator {
        //TODO 加入AOP 启动扫描注册全部权限注解类，并加入到权限表
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }

    @Override
    public void run(String... strings) throws Exception {
        // TODO 根据权限列表更新系统权限数据库表
        List<AdminPermissionV> permissionVList = new ApplicationPermissionScanStartup().getPermissionsList();
        for (AdminPermissionV permissionV : permissionVList) {
            // TODO 验证权限列表中的数据是否在DB中存在，并添加不存在的权限。
            boolean check = iSystemPermissionService.checkPermissionExist(permissionV.getName(), permissionV.getPermission());
            if (!check) {
                iSystemPermissionService.addPermission(permissionV);
            } else {
                AdminPermissionV dbData = iSystemPermissionService.getPermissionByPermission(permissionV.getPermission());
                permissionV.setId(dbData.getId());
                iSystemPermissionService.editPermission(permissionV);
            }
        }
        List<AdminMenuV> menuVList = (List) iSystemMenuService.getMenus().getData();
        for (AdminMenuV menuV : menuVList) {
            // TODO 验证权限列表中的数据是否在DB中存在，并添加不存在的权限。
            boolean check = iSystemPermissionService.checkPermissionExist(menuV.getName(), menuV.getPermission());
            AdminPermissionV permissionV = new AdminPermissionV();
            permissionV.setPermissionUrl(menuV.getUrl());
            permissionV.setName(menuV.getName());
            permissionV.setPermission(menuV.getPermission());
            permissionV.setDescription(SystemMenuServiceImpl.MENU_PERMISSION_DESCRIPTION_STR);
            if (!check) {
                iSystemPermissionService.addPermission(permissionV);
            }
        }
    }

    @Autowired
    ISystemPermissionService iSystemPermissionService;
    @Autowired
    ISystemMenuService iSystemMenuService;
}