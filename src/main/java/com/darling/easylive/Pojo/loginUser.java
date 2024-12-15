package com.darling.easylive.Pojo;
import com.darling.easylive.Pojo.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/*
*
* springsecurity需要实现UserDetails，作为查询数据库之后返回的对象
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class loginUser implements UserDetails {

    private userDto userDao;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return userDao.getPassword();
    }

    @Override
    public String getUsername() {
        return userDao.getUsername();
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
        return true;
    }
}
