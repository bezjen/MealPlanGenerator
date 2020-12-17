package com.bezjen.whattoeat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MvcConfig implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(MvcConfig.class, args);
    }
}