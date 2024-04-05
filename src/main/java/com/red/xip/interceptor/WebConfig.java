package com.red.xip.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private XipInterceptor xipInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(xipInterceptor)
        		.addPathPatterns("/shop/**") // /shop 경로는 포함
        		.addPathPatterns("/payment/**")
        		.addPathPatterns("/xipengineering/**")
        		.excludePathPatterns("/shop/shopR003") // /shop/shopR003 경로는 인터셉터 적용 제외;
        		.excludePathPatterns("/shop/shopR002"); 
    }

}
