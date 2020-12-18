package com.rh.git.util

import com.rh.git.builder.MainBuilder
import groovy.swing.SwingBuilder
import org.springframework.stereotype.Component;

/**
 * 更新git状态
 */
@Component
class UpdateGitStatusUtil {

    private SwingBuilder swingBuilder = MainBuilder.swingBuilder

    /**
     * 更新状态为失败显示
     */
    void updateToFail(String proPath, Exception e) {
        try {
            swingBuilder."git-pro-status${proPath}".background = MainBuilder.failColor
            swingBuilder."git-pro-status-text${proPath}".text = "<html><body>失败<br>${e.getMessage()}</body></html>"
        } catch (Exception e1) {
        }
    }

}
