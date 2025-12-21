package com.markus.spring_ai_demo.config.ai;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: markus
 * @date: 2025/12/21 15:32
 * @Description: ModelConfig
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
@Data
@Validated
public class ModelConfig {

    @NotBlank(message = "模型ID不能为空")
    private String modelId;

    @NotBlank(message = "Base URL不能为空")
    private String baseUrl;

    @NotBlank(message = "模型名称不能为空")
    private String modelName;

    @NotNull
    private String apiKey;

    private Double temperature = 0.5;

}
