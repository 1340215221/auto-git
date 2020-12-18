package com.rh.git.config;

import com.rh.git.model.GitProInfo;
import lombok.Data;
import lombok.experimental.Delegate;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * git项目配置
 */
@Component
@ConfigurationProperties(prefix = "pro")
public class GitProList implements List<GitProList.GitProItem> {
    @Delegate
    private final List<GitProItem> list = new ArrayList<>();
    @Autowired
    private GlobalConfig globalConfig;
    @Autowired
    private AnnotationConfigApplicationContext app;

    /**
     * 添加到spring容器
     */
    @PostConstruct
    public void addToContext() {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(item -> {
            if (StringUtils.isBlank(item.getProPath())) {
                return;
            }
            GitProInfo info = app.getBean(GitProInfo.class, item, globalConfig);
            app.getBeanFactory().registerSingleton("GitProInfo_" + item.getProPath(), info);
        });
    }

    @Data
    public static class GitProItem {
        private String username;
        private String password;
        private String proPath;
        private String proName;
        private List<String> proFile;
    }
}
