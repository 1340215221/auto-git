package com.rh.git.model;

import com.rh.git.action.GitAction;
import com.rh.git.config.GitProList;
import com.rh.git.config.GlobalConfig;
import com.rh.git.util.UpdateGitStatusUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.Color;
import java.util.List;

/**
 * git项目信息
 */
@Data
@Component
@Scope("prototype")
public class GitProInfo implements GitParam {
    private String username;
    private String password;
    private String proPath;
    private String proName;
    private String status;
    private Color statusColor;
    private List<String> commitFileProPath;
    @Autowired
    private GitAction gitAction;
    @Autowired
    private UpdateGitStatusUtil util;

    public GitProInfo(GitProList.GitProItem item, GlobalConfig globalConfig) {
        if (item == null || globalConfig == null) {
            throw new RuntimeException();
        }
        username = item.getUsername();
        if (StringUtils.isBlank(username)) {
            username = globalConfig.getUsername();
        }
        password = item.getPassword();
        if (StringUtils.isBlank(password)) {
            password = globalConfig.getPassword();
        }
        proPath = item.getProPath();
        proName = item.getProName();
        status = "无操作";
        statusColor = Color.green;
        commitFileProPath = item.getProFile();
    }

    /**
     * 更新项目
     */
    public void updatePro() {
        try {
            gitAction.update(this);
        } catch (Exception e) {
            util.updateToFail(proPath, e);
            throw e;
        }
    }
}
