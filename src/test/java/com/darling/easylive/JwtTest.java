package com.darling.easylive;

import com.darling.easylive.Utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class JwtTest {
    @Autowired
    private JwtUtil jwtUtil;
     @Test
    public void test(){
         String token = jwtUtil.createToken("3", "1");
         System.out.println(token);

     }

     @Test
    public  void encodertest(){
         BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
         String password=bCryptPasswordEncoder.encode("1234");
         System.out.println(password);
         String password1=bCryptPasswordEncoder.encode("1234");
         System.out.println(password1);
         //比较密码
         System.out.println(bCryptPasswordEncoder.matches("1234",
                 "$2a$10$9VD80NO8jBtnmseeAmvGzeRomsz2w4qj00X/q7I2jolq5d8A7LoWC"  ));
     }
}
