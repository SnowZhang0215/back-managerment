package io.snow.springcloud.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author snowzhang
 */
@EnableDiscoveryClient
@SpringBootApplication
public class AuthServer {
    public static void main(String[] args){
        SpringApplication.run(AuthServer.class,args);
    }
}
