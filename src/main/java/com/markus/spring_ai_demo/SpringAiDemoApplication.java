package com.markus.spring_ai_demo;

import com.markus.spring_ai_demo.service.MultiModelService;
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
    private final MultiModelService modelService;

    public SpringAiDemoApplication(ChatClient.Builder chatClientBuilder, MultiModelService modelService) {
        this.chatClient = chatClientBuilder.build();
        this.modelService = modelService;
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringAiDemoApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> {
            String content = chatClient.prompt("你是谁？介绍你的模型等级，如 qwen-plus").call().content();
            System.out.println(content);
            System.out.println("多模型: ");
            modelService.multiClientFlow();
        };
    }

    @Override
    public void setEnvironment(Environment environment) {
        System.out.println(environment.getProperty("DASHSCOPE_API_KEY"));
        System.out.println(environment.getProperty("DEEPSEEK_API_KEY"));
    }
}
