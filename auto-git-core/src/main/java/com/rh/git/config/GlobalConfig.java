package com.rh.git.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 全局配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "global-config")
public class GlobalConfig {
    private String username;
    private String password;
}
