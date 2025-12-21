package com.markus.spring_ai_demo.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.markus.spring_ai_demo.record.ActorFilms;
import com.markus.spring_ai_demo.service.ChatService;
import com.markus.spring_ai_demo.service.MultiModelService;
import org.springframework.ai.chat.client.AdvisorParams;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: markus
 * @date: 2025/12/21 16:25
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
@Service("structuredOutputChatServiceImpl")
public class StructuredOutputChatServiceImpl implements ChatService {

    @Autowired
    private MultiModelService modelService;

    private static ObjectMapper om = new ObjectMapper();

    @Override
    public String chat(String modelId, String prompt) throws JsonProcessingException {
        ChatModel chatModel = modelService.getChatModel(modelId);
        List<ActorFilms> entity = ChatClient.builder(chatModel).defaultAdvisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT).build().prompt(prompt).call().entity(new ParameterizedTypeReference<List<ActorFilms>>() {
        });
        return om.writeValueAsString(entity);
    }
}
