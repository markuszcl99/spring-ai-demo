package com.markus.spring_ai_demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.markus.spring_ai_demo.record.ActorFilms;
import com.markus.spring_ai_demo.service.ChatService;
import com.markus.spring_ai_demo.service.MultiModelService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: markus
 * @date: 2025/12/21 17:10
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
@Service("streamResponseChatServiceImpl")
public class StreamResponseChatServiceImpl implements ChatService {

    @Autowired
    private MultiModelService modelService;

    private static ObjectMapper om = new ObjectMapper();

    @Override
    public String chat(String modelId, String prompt) throws Exception {
        ChatModel chatModel = modelService.getChatModel(modelId);

        var converter = new BeanOutputConverter<>(new ParameterizedTypeReference<List<ActorFilms>>() {
        });

        Flux<String> flux = ChatClient.builder(chatModel).build().prompt()
                .user(u -> u.text(prompt +
                                        """
                                          {format}
                                        """)
                        .param("format", converter.getFormat()))
                .stream()
                .content();

        String content = flux.collectList().block().stream().collect(Collectors.joining());

        List<ActorFilms> actorFilms = converter.convert(content);
        return om.writeValueAsString(actorFilms);
    }
}
