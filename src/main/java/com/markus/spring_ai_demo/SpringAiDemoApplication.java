package com.markus.spring_ai_demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.markus.spring_ai_demo.record.ActorFilms;
import com.markus.spring_ai_demo.service.impl.MultiModelServiceImpl;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;

import java.util.List;

//@SpringBootApplication
public class SpringAiDemoApplication implements EnvironmentAware {

    private final ChatClient chatClient;
    private final MultiModelServiceImpl modelService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SpringAiDemoApplication(ChatClient.Builder chatClientBuilder, MultiModelServiceImpl modelService) {
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
//            String content = chatClient.prompt("你是谁？介绍你的模型等级，如 qwen-plus").call().content();
//            System.out.println(content);
//            System.out.println("多模型: ");
//            modelService.multiClientFlow();

            // ai 对话的元数据
//            ChatResponse chatResponse = chatClient.prompt("给我讲一个笑话!").call().chatResponse();
//            System.out.println(chatResponse);

            // record 用户体验，spring ai 会将 ai 返回的响应信息通过转换器转换成目标类型 entity
//            ActorFilms actorFilms = chatClient.prompt("Generate the filmography for a random actor.").call().entity(ActorFilms.class);
//            System.out.println(actorFilms);
            // entity method overloaded, 允许指定参数类型
            List<ActorFilms> actorFilms = chatClient.prompt()
                    .user("Generate the filmography of 5 movies for Tom Hanks and Bill Murray.")
                    .call()
                    .entity(new ParameterizedTypeReference<List<ActorFilms>>() {
                    });
            System.out.println(objectMapper.writeValueAsString(actorFilms));
        };
    }

    @Override
    public void setEnvironment(Environment environment) {
        System.out.println(environment.getProperty("DASHSCOPE_API_KEY"));
        System.out.println(environment.getProperty("DEEPSEEK_API_KEY"));
    }
}
