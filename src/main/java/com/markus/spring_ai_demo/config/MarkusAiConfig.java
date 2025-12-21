package com.markus.spring_ai_demo.config;

import com.markus.spring_ai_demo.config.ai.ModelConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

/**
 * @author: markus
 * @date: 2025/12/21 15:29
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
@Data
@Component
@Validated
@ConfigurationProperties(prefix = "markus.ai")
public class MarkusAiConfig {
    private Map<String, ModelConfig> models;
}
