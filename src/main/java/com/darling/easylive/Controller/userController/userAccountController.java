package com.darling.easylive.Controller.userController;


import com.darling.easylive.Pojo.dto.ResponseDto;
import com.darling.easylive.Service.userAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class userAccountController {
    @Autowired
    private userAccountService userAccountService;


    @PostMapping("/user/account/register")
    public ResponseDto register(@RequestBody Map<String,String> usermap){
        String username=usermap.get("username");
        String password=usermap.get("password");
       return userAccountService.register(username,password,password);

    }

    @GetMapping("/user/account/login")
    public ResponseDto login(@RequestBody Map<String,String> usermap){
        String username=usermap.get("username");
        String password=usermap.get("password");
        return userAccountService.login(username,password);
    }

}
