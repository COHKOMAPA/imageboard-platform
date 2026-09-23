package com.cohkomapa.imageboard.board.config;

import com.cohkomapa.imageboard.board.client.MediaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = MediaClient.class)
public class FeignClientsConfig {
}