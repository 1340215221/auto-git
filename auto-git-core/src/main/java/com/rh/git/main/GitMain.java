package com.rh.git.main;

import com.rh.git.builder.MainBuilder;
import com.rh.git.model.GitProInfo;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GitMain {

    @Autowired
    private MainBuilder builder;
    @Autowired
    private AnnotationConfigApplicationContext app;

    public void init() {
        builder.init();
        updatePro();
    }

    private void updatePro() {
        Map<String, GitProInfo> beanMap = app.getBeansOfType(GitProInfo.class, false, true);
        if (MapUtils.isEmpty(beanMap)) {
            return;
        }
        beanMap.forEach((key, value) -> {
            value.updatePro();
        });
    }

}
