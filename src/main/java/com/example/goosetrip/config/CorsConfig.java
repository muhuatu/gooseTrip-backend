package com.example.goosetrip.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
	 @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")
	                .allowedOrigins("http://localhost:4200") // 替換成前端的域名或地址
	                .allowedMethods("GET", "POST", "PUT", "DELETE")
	                .allowCredentials(true) // 允許 Cookie
	                .allowedHeaders("*");
	    }
}
