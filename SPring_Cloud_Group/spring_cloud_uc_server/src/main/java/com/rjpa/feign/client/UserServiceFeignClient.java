package com.rjpa.feign.client;

import com.rjpa.feign.hystrix.UserHystrixClientFallBackFactory;
import feign.Headers;
import model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "spring-cloud-micoUSer-server", path = "/micoUSerServer", fallbackFactory = UserHystrixClientFallBackFactory.class)
public interface UserServiceFeignClient {
    public final static String USER_FEIGN_CLIENT_NAME = "spring-cloud-micoUSer-server";

    /**
     * 根据用户ID查询用户
     *
     * @param userId
     * @return
     */
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @RequestMapping(method = RequestMethod.GET, value = "/adminInfo/userId",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Result getUserByUserId(@RequestParam(value = "userId") String userId);

    /**
     * 根据用户名查询用户
     *
     * @param userName
     * @return
     */
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @RequestMapping(method = RequestMethod.GET, value = "/user/name",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Result getUserByUserName(@RequestParam(value = "userName") String userName);

    /**
     * 根据电话号码查询用户
     *
     * @param phone
     * @return
     */
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @RequestMapping(method = RequestMethod.GET, value = "/user/phone",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Result getUserByPhone(@RequestParam(value = "phone") String phone);

    /**
     * 根据用户ID查询操作权限及菜单
     *
     * @param id
     * @return
     */
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @RequestMapping(method = RequestMethod.GET, value = "/user/${id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Result getUserMenuRightsByUserId(@PathVariable(value = "id") Integer id);

    /**
     * 根据用户ID查询角色
     *
     * @param id
     * @return
     */
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @RequestMapping(method = RequestMethod.GET, value = "/user/${id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Result getRoleByUserId(@PathVariable(value = "id") Integer id);

    /**
     * 查询全部操作权限URL
     *
     * @return
     */
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @RequestMapping(method = RequestMethod.GET, value = "/permissionURLs",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Result getAdminPermissionUrls();
}