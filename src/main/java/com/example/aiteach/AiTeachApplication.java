package com.example.aiteach;

import com.example.aiteach.util.AIEvaluateHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
public class AiTeachApplication {

    public static void main(String[] args) {
        AIEvaluateHttpClient aiEvaluateHttpClient = new AIEvaluateHttpClient();
        SpringApplication.run(AiTeachApplication.class, args);
    }

}
