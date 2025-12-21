package com.markus.spring_ai_demo.service.impl;

import com.markus.spring_ai_demo.record.ActorFilms;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

/**
 * @author: markus
 * @date: 2025/12/21 22:04
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
@Service("promptTemplatesChatServiceImpl")
public class PromptTemplatesChatServiceImpl extends AbstractChatServiceImpl {
    @Override
    public String chat(String modelId, String prompt) throws Exception {
        ChatModel chatModel = getModelService().getChatModel(modelId);
        ActorFilms actorFilms = ChatClient.builder(chatModel).build().prompt().user(u -> u.text("告诉我三部由 {actor} 主演的电影").param("actor", "沈腾")).call().entity(ActorFilms.class);
        return write(actorFilms);
    }
}
