package com.lgc.user_service;
import com.lgc.user_service.utils.ScanAllBook;
import com.lgc.user_service.utils.Util;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@ServletComponentScan("com.lgc.user_service.filter")
@SpringBootApplication
@ComponentScan(basePackages={"com.lgc"})
public class LibraryApplication {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(LibraryApplication.class,args);


    }
}
