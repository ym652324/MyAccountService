package com.yang;

import com.yang.ifsp.as.account.openAccount.db.dao.OpenAcctTxnInfoDOMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = {"com.yang.ifsp.as.account.openAccount"})
@MapperScan("com.yang.ifsp.as.account.openAccount.db.dao")
@RestController

public class OpenAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenAccountApplication.class,args);
    }
}
