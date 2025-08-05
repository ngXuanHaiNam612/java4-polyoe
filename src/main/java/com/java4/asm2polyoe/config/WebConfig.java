package com.java4.asm2polyoe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Cho phép tất cả đường dẫn
                .allowedOrigins("http://localhost:3000") // Cho phép frontend gọi
                .allowedMethods("*") // Cho tất cả các method: GET, POST, PUT, DELETE,...
                .allowedHeaders("*")
                .allowCredentials(true);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Đăng ký thư mục 'avatars' trong 'src/main/resources/static'
        // để có thể truy cập qua URL '/avatars/**'
        registry.addResourceHandler("/avatars/**")
                .addResourceLocations("file:src/main/resources/static/avatars/");
    }
}

