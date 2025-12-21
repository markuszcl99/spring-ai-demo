package com.markus.spring_ai_demo.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;

import java.util.List;
import java.util.Set;

/**
 * @author: markus
 * @date: 2025/12/21 16:00
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public interface MultiModelService {
    ChatModel getChatModel(String modelId);
    Set<String> getSupportedModels();
}
