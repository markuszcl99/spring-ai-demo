package com.markus.spring_ai_demo.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.markus.spring_ai_demo.service.ChatService;
import com.markus.spring_ai_demo.service.MultiModelService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: markus
 * @date: 2025/12/21 22:06
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public abstract class AbstractChatServiceImpl implements ChatService {
    @Autowired
    private MultiModelService modelService;

    private static final ObjectMapper om = new ObjectMapper();


    public MultiModelService getModelService() {
        return modelService;
    }

    public void setModelService(MultiModelService modelService) {
        this.modelService = modelService;
    }

    public String write(Object obj) {
        try {
            return om.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return "序列化失败";
        }
    }
}
