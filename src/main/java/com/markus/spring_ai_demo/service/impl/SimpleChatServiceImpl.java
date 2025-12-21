package com.markus.spring_ai_demo.service.impl;

import com.markus.spring_ai_demo.service.ChatService;
import com.markus.spring_ai_demo.service.MultiModelService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: markus
 * @date: 2025/12/21 15:59
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
@Service("simpleChatServiceImpl")
public class SimpleChatServiceImpl implements ChatService {

    @Autowired
    private MultiModelService modelService;

    @Override
    public String chat(String modelId, String prompt) {
        ChatModel chatModel = modelService.getChatModel(modelId);
        return ChatClient.builder(chatModel).build().prompt().user(prompt).call().content();
    }
}
