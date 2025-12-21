package com.markus.spring_ai_demo.service.impl;

import com.markus.spring_ai_demo.config.MarkusAiConfig;
import com.markus.spring_ai_demo.config.ai.ModelConfig;
import com.markus.spring_ai_demo.service.MultiModelService;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: markus
 * @date: 2025/12/13 16:26
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
@Service
public class MultiModelServiceImpl implements InitializingBean, MultiModelService {
    private static final Logger logger = LoggerFactory.getLogger(MultiModelServiceImpl.class);

    private static final Map<String, OpenAiChatModel> openApiChatModels = new HashMap<>();

    private final OpenAiChatModel baseChatModel;
    private final OpenAiApi baseOpenAiApi;
    private final MarkusAiConfig markusAiConfig;

    public MultiModelServiceImpl(OpenAiChatModel baseChatModel, OpenAiApi baseOpenAiApi, MarkusAiConfig markusAiConfig) {
        this.baseChatModel = baseChatModel;
        this.baseOpenAiApi = baseOpenAiApi;
        this.markusAiConfig = markusAiConfig;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, ModelConfig> models = markusAiConfig.getModels();
        for (Map.Entry<String, ModelConfig> model : models.entrySet()) {
            String key = model.getKey();
            ModelConfig modelConfig = model.getValue();
            OpenAiApi customOpenAiApi = createCustomOpenAiApi(baseOpenAiApi, modelConfig.getBaseUrl(), modelConfig.getApiKey());
            OpenAiChatModel customOpenAiChatModel = createCustomOpenAiChatModel(baseChatModel,
                    customOpenAiApi,
                    OpenAiChatOptions.builder().model(modelConfig.getModelName()).temperature(modelConfig.getTemperature()).build());
            openApiChatModels.put(key, customOpenAiChatModel);
        }
    }

    private OpenAiApi createCustomOpenAiApi(OpenAiApi baseOpenAiApi, String baseUrl, String apiKey) {
        return baseOpenAiApi.mutate().baseUrl(baseUrl).apiKey(apiKey).build();
    }

    private OpenAiChatModel createCustomOpenAiChatModel(OpenAiChatModel baseChatModel, OpenAiApi openAiApi, OpenAiChatOptions openAiChatOptions) {
        return baseChatModel.mutate().openAiApi(openAiApi).defaultOptions(openAiChatOptions).build();
    }

    @Override
    @Nullable
    public ChatModel getChatModel(String modelId) {
        return openApiChatModels.get(modelId);
    }

    @Override
    public Set<String> getSupportedModels() {
        return openApiChatModels.keySet();
    }
}
