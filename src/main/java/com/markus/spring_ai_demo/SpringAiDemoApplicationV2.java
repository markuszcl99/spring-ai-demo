package com.markus.spring_ai_demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.markus.spring_ai_demo.record.ActorFilms;
import com.markus.spring_ai_demo.service.ChatService;
import com.markus.spring_ai_demo.service.MultiModelService;
import com.markus.spring_ai_demo.service.impl.MultiModelServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class SpringAiDemoApplicationV2 {

    private static final Logger logger = LoggerFactory.getLogger(SpringAiDemoApplicationV2.class);

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MultiModelService modelService;
    @Autowired
    @Qualifier("simpleChatServiceImpl")
    private ChatService chatService;
    @Autowired
    @Qualifier("structuredOutputChatServiceImpl")
    private ChatService structuredOutputChatService;
    @Autowired
    @Qualifier("streamResponseChatServiceImpl")
    private ChatService streamResponseChatServiceImpl;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringAiDemoApplicationV2.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> {
            logger.info("当前平台支持的模型包括: {}", om.writeValueAsString(modelService.getSupportedModels()));
            Scanner scanner = new Scanner(System.in);
            System.out.println("请选择你要对话的模型: ");
            while (scanner.hasNext()) {
                String modelId = scanner.next();
                System.out.println("请输入你的问题: ");
                String prompt = scanner.next();
//                System.out.println(chatService.chat(modelId, prompt));
//                System.out.println(structuredOutputChatService.chat(modelId, prompt));
                System.out.println(streamResponseChatServiceImpl.chat(modelId, prompt));
                System.out.println("请选择你要对话的模型: ");
            }
        };
    }
}
