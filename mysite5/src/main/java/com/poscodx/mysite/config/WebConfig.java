package com.poscodx.mysite.config;

import com.poscodx.mysite.config.web.FileUploadConfig;
import com.poscodx.mysite.config.web.LocaleConfig;
import com.poscodx.mysite.config.web.MvcConfig;
import com.poscodx.mysite.config.web.SecurityConfig;
import com.poscodx.mysite.event.ApplicationContextEventListener;
import com.poscodx.mysite.interceptor.SiteInterceptor;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableAspectJAutoProxy
@Import({MvcConfig.class, LocaleConfig.class, SecurityConfig.class, FileUploadConfig.class})
@ComponentScan({"com.poscodx.mysite.controller", "com.poscodx.mysite.exception"})
public class WebConfig implements WebMvcConfigurer {

    // Site Interceptor
    @Bean
    public HandlerInterceptor siteInterceptor() {
        return new SiteInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(siteInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/assets/**");
    }

    // ApplicationContext Event Listener
    @Bean
    public ApplicationContextEventListener applicationContextEventListener() {
        return new ApplicationContextEventListener();
    }
}