package com.chenxin.playojbackendquestionservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@MapperScan("com.chenxin.playojbackendquestionservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.chenxin")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.chenxin.playojbackendserviceclient.service")
public class PlayojBackendQuestionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlayojBackendQuestionServiceApplication.class, args);
    }

}
