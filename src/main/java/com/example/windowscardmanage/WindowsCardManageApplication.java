package com.example.windowscardmanage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;

@SpringBootApplication
@MapperScan(annotationClass = Repository.class,basePackages = {"com.example.*"})
public class WindowsCardManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(WindowsCardManageApplication.class, args);
    }

}
