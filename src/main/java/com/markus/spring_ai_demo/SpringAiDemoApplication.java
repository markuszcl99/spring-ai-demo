package com.markus.spring_ai_demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class SpringAiDemoApplication implements EnvironmentAware {

    private final ChatClient chatClient;

    public SpringAiDemoApplication(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringAiDemoApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> {
            String content = chatClient.prompt("你是谁？").call().content();
            System.out.println(content);
        };
    }

    @Override
    public void setEnvironment(Environment environment) {
        System.out.println(environment.getProperty("DASHSCOPE_API_KEY"));
    }
}
