package com.java.everyboard.config;

import com.amazonaws.HttpMethod;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry
                .addMapping("/**")
                .allowedOrigins("http://localhost:3000",
                                "https://main.d2oi0np8ja85am.amplifyapp.com",
                                "http://localhost:5173",
                                "http://localhost:6379",
                                "http://everyboard.shop",
                                "http://ec2-43-202-32-108.ap-northeast-2.compute.amazonaws.com:8080")
                .allowedOriginPatterns("*")
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.HEAD.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.PATCH.name(),
                        "GET","POST","PUT","PATCH","HEAD","DELETE","OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("*", "Authorization", "Refresh")
                .allowCredentials(true).maxAge(3000);
    }
}
