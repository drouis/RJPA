package anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义权限注解
 * 用来标识控制类的权限在数据库中的配置
 * Created by Drouis on 2018/02/11
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {
    /**
     * 权限值
     * :用来分隔执行权限
     * .用来分隔菜单目录权限
     *
     * @return
     */
    String permissionName();

    /**
     * 权限名称
     */
    String name();

    /**
     * 权限访问地址
     * @return
     */
    String permissionUrl();


}
