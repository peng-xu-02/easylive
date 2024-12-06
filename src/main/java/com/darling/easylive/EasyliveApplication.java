package com.darling.easylive;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.darling.easylive.Mapper")
public class EasyliveApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyliveApplication.class,args);
    }
}
