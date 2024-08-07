package com.example.java_board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@SpringBootApplication
@EnableJpaRepositories(basePackages ={
        "com.example.java_board.domain.user.repository",
        "com.example.java_board.domain.board.repository"
})
public class JavaBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaBoardApplication.class, args);
    }

}