package com.fy.PermissionMannger;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.fy.PermissionMannger.Mapper")
@EnableScheduling
public class PermissionManngerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PermissionManngerApplication.class, args);
    }
}
