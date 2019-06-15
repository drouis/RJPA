package com.rjpa.feign.client;

import model.Result;

public interface UserFeignClientWithFactory extends UserServiceFeignClient {
    @Override
    default Result getUserByUserId(String userId) {
        return null;
    }

    @Override
    default Result getUserByUserName(String userName) {
        return null;
    }

    @Override
    default Result getUserByPhone(String phone) {
        return null;
    }

    @Override
    default Result getUserMenuRightsByUserId(Integer id) {
        return null;
    }

    @Override
    default Result getRoleByUserId(Integer id) {
        return null;
    }

    @Override
    default Result getAdminPermissionUrls() {
        return null;
    }
}
