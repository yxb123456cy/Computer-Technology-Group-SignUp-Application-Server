package com.lemon.computer;

import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableFileStorage
@EnableAsync
@EnableTransactionManagement
@SpringBootApplication
public class ComputerTechnologyGroupSignUpApplicationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComputerTechnologyGroupSignUpApplicationServerApplication.class, args);
    }

}
