package org.jeecg.modules.redbook.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "redbook.ai")
public class RedbookAiProperties {
    private boolean enabled = false;

    /**
     * 支持：dify / fastgpt / local
     */
    private String provider = "local";

    /**
     * Dify 示例： https://your-dify-host/v1
     * FastGPT 示例： https://your-fastgpt-host/api
     */
    private String baseUrl;

    private String apiKey;

    private Integer timeoutSeconds = 60;

    private String userPrefix = "redbook-agent";
}
