package com.markus.spring_ai_demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: markus
 * @date: 2025/12/13 16:26
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
@Service
public class MultiModelService {

    private static final Logger logger = LoggerFactory.getLogger(MultiModelService.class);

    @Autowired
    private OpenAiChatModel baseChatModel;

    @Autowired
    private OpenAiApi baseOpenAiApi;

    public void multiClientFlow() {
        try {
            OpenAiApi deepseekApiKey = baseOpenAiApi.mutate()
                    .baseUrl("https://api.deepseek.com/")
                    .apiKey(System.getenv("DEEPSEEK_API_KEY"))
                    .build();

            OpenAiApi qwenApi = baseOpenAiApi.mutate()
                    .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/")
                    .apiKey(System.getenv("DASHSCOPE_API_KEY"))
                    .build();

            OpenAiChatModel deepseekModel = baseChatModel.mutate()
                    .openAiApi(deepseekApiKey)
                    .defaultOptions(OpenAiChatOptions.builder().model("deepseek-chat").temperature(0.5).build())
                    .build();

            OpenAiChatModel qwenModel = baseChatModel.mutate()
                    .openAiApi(qwenApi)
                    .defaultOptions(OpenAiChatOptions.builder().model("qwen-plus").temperature(0.7).build())
                    .build();

            // Simple prompt for both models
            String prompt = "你是谁？";

            String deeppseekResponse = ChatClient.builder(deepseekModel).build().prompt(prompt).call().content();
            String qwenResponse = ChatClient.builder(qwenModel).build().prompt(prompt).call().content();

            logger.info("deepseek response: {}", deeppseekResponse);
            logger.info("qwen response: {}", qwenResponse);
        }
        catch (Exception e) {
            logger.error("Error in multi-client flow", e);
        }
    }
}
