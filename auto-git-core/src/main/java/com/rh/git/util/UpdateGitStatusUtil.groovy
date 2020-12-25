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

    void update(String proPath, Runnable run) {
        try {
            swingBuilder."git-pro-status${proPath}".background = MainBuilder.operationColor
            swingBuilder."git-pro-status-text${proPath}".text = "<html><body>更新中...</body></html>"
            run.run()
            swingBuilder."git-pro-status${proPath}".background = MainBuilder.okColor
            swingBuilder."git-pro-status-text${proPath}".text = "<html><body>更新成功</body></html>"
        } catch (Exception e) {
            swingBuilder."git-pro-status${proPath}".background = MainBuilder.failColor
            swingBuilder."git-pro-status-text${proPath}".text = "<html><body>失败<br>${e.getMessage()}</body></html>"
            throw e
        }
    }
}
