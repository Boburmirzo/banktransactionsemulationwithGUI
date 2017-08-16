package ru.task.bankemulation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Created by bumurzaqov on 08/14/2017.
 */

@SpringBootApplication
@EnableAsync
public class Application{

    public static void main(String[] args) throws Exception{
        SpringApplication.run(Application.class, args);
    }
}
