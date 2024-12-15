package com.darling.easylive.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.darling.easylive.Conponent;
import com.darling.easylive.Mapper.userAccountMapper;
import com.darling.easylive.Mapper.userMapper;
import com.darling.easylive.Pojo.dto.userDto;
import com.darling.easylive.Pojo.loginUser;
import com.darling.easylive.Pojo.vo.userVo;
import com.darling.easylive.Service.userAccountService;
import com.darling.easylive.Utils.JwtUtil;
import com.darling.easylive.Utils.RedisUtil;
import com.darling.easylive.Utils.Result;
import com.darling.easylive.Utils.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class userAccountServiceImpl implements userAccountService {

    @Resource
    private userAccountMapper userAccountMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private userMapper userMapper;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RedisUtil redisUtil;


    @Transactional
    public Result register(String username, String password, String password2){
        Result resultDto =new Result<>();
        QueryWrapper<userDto> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        queryWrapper.ne("state",2);
        userDto userDto1= userMapper.selectOne(queryWrapper);
        if(userDto1!=null){
            return resultDto.build(null,ResultCodeEnum.USER_HAVE_EXIST);
        }

        //TODO 此处返回默认头像和背景图地址
        String avatar_url="D:\\static\\CatImg\\007\\20240410.jpg";
        String bg_url="D:\\static\\CatImg\\007\\20240410.jpg";
        Date now=new Date();
        password=passwordEncoder.encode(password);
        userDto userDto =new userDto(
                null,
                username,
                password,
                "默认昵称"+System.currentTimeMillis(),
                avatar_url,
                bg_url,
                2,
                "这个人很懒，什么都没留下~",
                0,
                (double) 0,
                0,
                0,
                0,
                0,
                null,
                now,
                null
        );
                userMapper.insert(userDto);
        return resultDto.ok(null);

    }

    /*
    *  将用户名和密码封装成UsernamePasswordAuthenticationToken对象，
       因为我们重写了AuthenticationProvendor的认证方法authenticate
       返回了我们的一个UsernamePasswordAuthenticationToken对象
     */
    public Result login(String username, String password){

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                new UsernamePasswordAuthenticationToken(username,password);
        Authentication authentication;
        try{
            authentication= authenticationProvider.authenticate(usernamePasswordAuthenticationToken);
        }catch (Exception exception){
            return new Result().build(null,ResultCodeEnum.LOGIN_FAILED);
        }
        Result resultDto=new Result<>();
        loginUser loginuser = (loginUser) authentication.getPrincipal();
        userDto userDto=loginuser.getUserDao();
        userVo userVo=new userVo();
        userVo.setUid(userDto.getUid());
        userVo.setNickname(userDto.getNickname());
        userVo.setGender(userDto.getGender());
        Map<String,Object> map=new HashMap<>(2);
        String token= jwtUtil.createToken(userDto.getUid().toString(),"user");
        //key值为user:role:userId,可以区分普通用户和管理员
        String key=Conponent.USER_INFO+"user:"+userDto.getUid();
        try{
            redisUtil.setExObjectValue(key,userDto);
        }catch (Exception e){
            log.info("数据存入redis异常");
            throw e;
        }
        map.put("token",token);
        map.put("user",userDto);
       resultDto.setCode(200);
       resultDto.setData(map);
        return resultDto;
    }

}
