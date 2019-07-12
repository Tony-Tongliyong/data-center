package com.tong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.mapper.autoconfigure.MapperAutoConfiguration;

/**
 * @author: tongly
 * @contact:18158190830@163.com
 * @file: Application
 * @time: 2019/3/26 13:58
 * @desc:
 */
@EnableSwagger2
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MapperAutoConfiguration.class})
@ComponentScan(basePackages = "com.tong")
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}