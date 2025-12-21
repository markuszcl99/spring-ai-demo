package com.markus.spring_ai_demo.service.impl;

import com.markus.spring_ai_demo.record.ActorFilms;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.stereotype.Service;

/**
 * @author: markus
 * @date: 2025/12/21 23:01
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
@Service("callReturnValueChatServiceImpl")
public class CallReturnValueChatServiceImpl extends AbstractChatServiceImpl {
    /**
     * 对官网上的文档做额外的补充说明：
     * 1. ChatResponse 是 AI 模型返回信息的封装
     * 2. ChatClientResponse 是 ChatClient 响应信息的封装，包括 ChatResponse 和 ChatClient 执行过程中上下文信息
     */
    @Override
    public String chat(String modelId, String prompt) throws Exception {
        ChatModel chatModel = getModelService().getChatModel(modelId);
        ChatClient.CallResponseSpec responseSpec = ChatClient.builder(chatModel)
                .build()
                .prompt()
                .user(u -> u.text("告诉我三部由 <actor> 主演的电影").param("actor", "沈腾"))
                .templateRenderer(StTemplateRenderer.builder().startDelimiterToken('<').endDelimiterToken('>').build())
                .call();
        ActorFilms actorFilms = responseSpec.entity(ActorFilms.class);
        return write(actorFilms);
    }
}
