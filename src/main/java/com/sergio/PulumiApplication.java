package com.sergio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PulumiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PulumiApplication.class, args);
    }

}
