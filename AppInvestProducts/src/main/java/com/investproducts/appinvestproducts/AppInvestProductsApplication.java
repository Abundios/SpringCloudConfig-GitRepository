package com.investproducts.appinvestproducts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.investproducts.appinvestproducts.service")
public class AppInvestProductsApplication
{
    public static void main(String[] args) {
        SpringApplication.run(AppInvestProductsApplication.class, args);
    }
}
