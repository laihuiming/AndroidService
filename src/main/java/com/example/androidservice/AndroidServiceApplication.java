package com.example.androidservice;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@SpringBootApplication
@MapperScan("com/example/androidservice/mapper")
@EnableOpenApi
@EnableSwagger2WebMvc
public class AndroidServiceApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(AndroidServiceApplication.class, args);
        System.out.println("---------------------项目启动成功---------------------");
    }

}
