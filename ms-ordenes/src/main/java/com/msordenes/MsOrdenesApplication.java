package com.msordenes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsOrdenesApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsOrdenesApplication.class, args);
    }
}
