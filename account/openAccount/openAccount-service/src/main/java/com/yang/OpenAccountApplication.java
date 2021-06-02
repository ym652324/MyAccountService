package com.yang;

import org.mybatis.spring.annotation.MapperScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = {"com.yang.ifsp.common","com.yang.ifsp.as"})
@MapperScan("com.yang.ifsp.as.account.openAccount.db.dao")
@RestController

public class OpenAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenAccountApplication.class,args);
    }
}
