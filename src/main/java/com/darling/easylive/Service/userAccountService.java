package com.darling.easylive.Service;

import com.darling.easylive.Mapper.userAccountMapper;
import com.darling.easylive.Pojo.dto.ResponseDto;
import com.darling.easylive.Pojo.dto.userDto;
import com.darling.easylive.Pojo.user;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class userAccountService {
    @Resource
    private userAccountMapper userAccountMapper;

    public ResponseDto register(String username,String password,String password2){
        ResponseDto responseDto=new ResponseDto<>();
        String avatar_url="D:\\static\\CatImg\\007\\20240410.jpg";
        String bg_url="D:\\static\\CatImg\\007\\20240410.jpg";
        Date now=new Date();
        user user=new user(
                null,
                username,
                password,
                "默认昵称",
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

        int count=userAccountMapper.register(user);
        if(count>0){
            return responseDto;
        }
        responseDto.setCode(600);
        responseDto.setMessage("注册失败");
        return responseDto;
    }

    public ResponseDto login(String username,String password){

        ResponseDto responseDto=new ResponseDto<>();
        user user=(user)userAccountMapper.getUserInfo(username,password);
        if(user==null){
            responseDto.setCode(600);
            responseDto.setMessage("登录失败");
            return  responseDto;
        }
        userDto userdto=new userDto();
        userdto.setUid(user.getUid());
        userdto.setNickname(user.getNickname());
        userdto.setGender(user.getGender());
        responseDto.setCode(200);
        responseDto.setData(userdto);
        return responseDto;
    }
}
