package com.rjpa.feign.hystrix;

import com.rjpa.feign.client.UserFeignClientWithFactory;
import com.rjpa.feign.client.UserServiceFeignClient;
import feign.hystrix.FallbackFactory;
import model.Result;
import model.utils.SystemConstCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserHystrixClientFallBackFactory implements FallbackFactory<UserServiceFeignClient> {
    private static final Logger logger = LoggerFactory.getLogger(UserHystrixClientFallBackFactory.class);

    public static final String ERROR_FALLBACK_STR = "来自 HystrixFallBackFactory 的系统消息:";

    @Override
    public UserServiceFeignClient create(Throwable throwable) {
        UserHystrixClientFallBackFactory.logger.info("fallback reason was: {} ", throwable.getMessage());
        return new UserFeignClientWithFactory() {

            @Override
            public Result getUserByUserId(Integer id) {
                return Result.error(SystemConstCode.USER_NOT_FOUND.getCode(), ERROR_FALLBACK_STR + SystemConstCode.USER_NOT_FOUND.getMessage());
            }

            @Override
            public Result getUserByUserName(String userName) {
                return Result.error(SystemConstCode.USER_NOT_FOUND.getCode(), ERROR_FALLBACK_STR + SystemConstCode.USER_NOT_FOUND.getMessage());
            }

            @Override
            public Result getUserByPhone(String phone) {
                return Result.error(SystemConstCode.USER_NOT_FOUND.getCode(), ERROR_FALLBACK_STR + SystemConstCode.USER_NOT_FOUND.getMessage());
            }

            @Override
            public Result getUserMenuRightsByUserId(Integer id) {
                return Result.error(SystemConstCode.USERRIGNT_NOT_FOUND.getCode(), ERROR_FALLBACK_STR + SystemConstCode.USERRIGNT_NOT_FOUND.getMessage());
            }

            @Override
            public Result getRoleByUserId(Integer id) {
                return Result.error(SystemConstCode.USERROLE_NOT_FOUND.getCode(), ERROR_FALLBACK_STR + SystemConstCode.USERROLE_NOT_FOUND.getMessage());
            }

            @Override
            public Result getAdminPermissionUrls() {
                return Result.error(SystemConstCode.ERROR.getCode(), ERROR_FALLBACK_STR + SystemConstCode.ERROR.getMessage());
            }
        };
    }
}
