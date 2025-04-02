package com.teriteri.backend;

import com.teriteri.backend.im.IMServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication
@EnableScheduling   // 启用定时任务
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);

        new Thread(() -> {
            try {
                new IMServer().start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
