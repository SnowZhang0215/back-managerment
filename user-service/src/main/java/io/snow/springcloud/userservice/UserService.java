package io.snow.springcloud.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@EnableDiscoveryClient
@SpringBootApplication
//@EnableOAuth2Client
@EnableFeignClients
public class UserService {
    public static void main(String[] args){
        SpringApplication.run(UserService.class,args);
    }
}
