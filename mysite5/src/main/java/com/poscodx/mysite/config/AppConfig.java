package com.poscodx.mysite.config;

import com.poscodx.mysite.config.app.DBConfig;
import com.poscodx.mysite.config.app.MyBatisConfig;
import com.poscodx.mysite.config.app.SecurityConfig;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@ComponentScan({"com.poscodx.mysite.service", "com.poscodx.mysite.repository", "com.poscodx.mysite.aspect"})
@Import({DBConfig.class, MyBatisConfig.class, SecurityConfig.class})
public class AppConfig {

}
