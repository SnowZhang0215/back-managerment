package io.snow.springcloud.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author snowzhang
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class AuthServer {
    public static void main(String[] args){
        SpringApplication.run(AuthServer.class,args);
    }
}
