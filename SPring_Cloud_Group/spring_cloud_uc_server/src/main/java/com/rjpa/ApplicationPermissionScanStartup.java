package com.rjpa;

import anno.Permission;
import com.rjpa.vo.AdminPermissionV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * EUREKA启动后扫描所有带权限注解的控制类
 * 指定权限的控制类加入到内存列表中
 * Created by drouis on 2018/4/3 0003.
 */
public class ApplicationPermissionScanStartup implements ApplicationListener<ContextRefreshedEvent> {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    public static ArrayList<AdminPermissionV> permissionsList = new ArrayList<AdminPermissionV>();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        final ApplicationContext app = event.getApplicationContext();
        logger.info("系统参数初始化中....");
        try {
            // TODO 循环所有系统注解类
            Map rcBeans = event.getApplicationContext().getBeansWithAnnotation(RestController.class);
            initDefinePermissionsFromBeans(rcBeans);
            Map cBeans = event.getApplicationContext().getBeansWithAnnotation(Controller.class);
            initDefinePermissionsFromBeans(cBeans);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initDefinePermissionsFromBeans(Map beans) {
        Set<Map.Entry<String, Object>> entries = beans.entrySet();//遍历Bean
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> map = iterator.next();
            Class<?> aClass = map.getValue().getClass();
            Method[] methods = aClass.getMethods();
            boolean permissionChk = false;
            for (Method method : methods) {
                //有无权限注解
                Annotation a = AnnotationUtils.findAnnotation(method, Permission.class);
                if (a != null && a instanceof Permission) {
                    Map annoMap = AnnotationUtils.getAnnotationAttributes(a);
                    AdminPermissionV r = new AdminPermissionV();
                    BeanUtils.instantiate(AdminPermissionV.class);
                    r.setName((String) annoMap.get("name"));
                    r.setPermission((String) annoMap.get("permissionName"));
                    r.setPermissionUrl((String) annoMap.get("permissionUrl"));
                    permissionsList.add(r);
                    permissionChk = true;
                }
                if (permissionChk == false) {
                    logger.warn(method.getName() + ":未作权限限制");
                }
            }
        }
    }

    public ArrayList<AdminPermissionV> getPermissionsList() {
        return permissionsList;
    }
}
