package com.darling.easylive.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.darling.easylive.Mapper.userMapper;
import com.darling.easylive.Pojo.dto.userDto;
import com.darling.easylive.Pojo.loginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//springsecurity 需要实现UserDetailsService 接口，重写loadUserByUsername(String username)方法，用来获取用户信息
@Service
public class userDetailServiceImpl implements UserDetailsService {
    @Autowired
    private userMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<userDto> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        userDto userDto = userMapper.selectOne(queryWrapper);
        if(userDto==null){
            return null;
        }
        return new loginUser(userDto);
    }
}
