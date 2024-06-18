package com.poscodx.mysite.config;

import com.poscodx.mysite.config.app.DBConfig;
import com.poscodx.mysite.config.app.MyBatisConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DBConfig.class, MyBatisConfig.class})
public class AppConfig {
}
