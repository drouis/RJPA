package com.rjpa.AuthConfig.vo;

import com.rjpa.vo.AdminUserV;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class User extends AdminUserV implements UserDetails, Serializable {
    private static final long serialVersionUID = -1587616598610316522L;

    public String tokenStr;

    public void initUser(String userName, String password, List<GrantedAuthority> permissionList) {
        super.setUserName(userName);
        super.setPassword(password);
        super.setAllowPermissionService(permissionList);
    }

    @Override
    public List getAllowPermissionService() {
        return super.getAllowPermissionService();
    }

    @Override
    public void setAllowPermissionService(List allowPermissionService) {
        super.setAllowPermissionService(allowPermissionService);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return super.getAllowPermissionService();
    }

    @Override
    public String getUsername() {
        return super.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (super.getState() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public String getTokenStr() {
        return tokenStr;
    }

    public void setTokenStr(String tokenStr) {
        this.tokenStr = tokenStr;
    }
}
