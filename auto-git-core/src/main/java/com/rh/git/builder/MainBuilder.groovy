package com.rh.git.builder

import com.rh.git.action.GitAction
import com.rh.git.config.GitProList
import com.rh.git.config.GlobalConfig
import com.rh.git.model.GitProInfo
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

import javax.annotation.Resource
import javax.swing.JFrame
import javax.swing.JLabel
import java.awt.BorderLayout
import java.awt.Color
import java.awt.GridLayout

@Component
class MainBuilder {

    @Autowired
    private ApplicationContext app
    @Autowired
    private GlobalConfig globalConfig
    @Resource(name = "GitProList")
    private GitProList gitProList
    @Autowired
    private GitAction gitAction
    public static final SwingBuilder swingBuilder = new SwingBuilder()
    /**
     * 操作中
     */
    public static Color operationColor = Color.gray
    /**
     * 成功颜色
     */
    public static Color okColor = Color.green
    /**
     * 失败颜色
     */
    public static Color failColor = Color.red

    void init() {
        swingBuilder.frame(id: 'frame',
                pack: true,
                defaultCloseOperation: JFrame.DO_NOTHING_ON_CLOSE,
                windowClosing: {
                    swingBuilder.doOutside {
                        def map = app.getBeansOfType(GitProInfo, false, true)
                        map.each {key, bean ->
                            try {
                                swingBuilder."git-pro-status${bean.getProPath()}".background = operationColor
                                swingBuilder."git-pro-status-text${bean.getProPath()}".text = "<html><body>提交中...</body></html>"
                                gitAction.commit(bean)
                                swingBuilder."git-pro-status${bean.getProPath()}".background = okColor
                                swingBuilder."git-pro-status-text${bean.getProPath()}".text = "<html><body>提交成功</body></html>"
                            } catch (Exception e) {
                                swingBuilder."git-pro-status${bean.getProPath()}".background = failColor
                                swingBuilder."git-pro-status-text${bean.getProPath()}".text = "<html><body>提交失败<br>${e.getMessage()}</body></html>"
                                throw e
                            }
                        }
                        System.exit(0)
                    }
                },
        ) {
            swingBuilder.panel(id: 'root',
                    layout: new BorderLayout(),
                    preferredSize: [500, 350],
            ) {
                swingBuilder.scrollPane(id: 'scroll-pane',
                ) {
                    swingBuilder.panel(layout: new GridLayout(gitProList.size(), 1, 0, 1),
                            preferredSize: [0, gitProList.size() * 75],
                            background: Color.gray,
                    ) {
                        for (i in 0..<gitProList.size()) {
                            def proItem = gitProList.get(i)

                            swingBuilder.panel(id: "list-item-${i}",
                                    layout: new GridLayout(1, 2),
                            ) {
                                // git项目列表
                                swingBuilder.panel(id: "git-pro-info${i}",
                                        layout: new BorderLayout(),
                                ) {
                                    swingBuilder.label(text: "<html><body>${proItem.getProName()}<br>${proItem.getProPath()}</body></html>")
                                }
                                swingBuilder.panel(layout: new GridLayout(1, 2)) {
                                    def bean = app.getBean("GitProInfo_${proItem.getProPath()}", GitProInfo)
                                    // git项目状态
                                    swingBuilder.panel(id: "git-pro-status${bean.getProPath()}",
                                            layout: new BorderLayout(),
                                            background: operationColor,
                                    ) {
                                        try {
                                            swingBuilder.label(id: "git-pro-status-text${bean.getProPath()}",
                                                    text: "待更新",
                                                    horizontalAlignment: JLabel.CENTER,
                                            )
                                        } catch (Exception e) {
                                        }
                                    }
                                    // 操作按钮
                                    swingBuilder.panel(id: "git-pro-operation${i}",
                                            layout: new GridLayout(1, 2),
                                    ) {
                                        swingBuilder.button(text: '提交',
                                                mouseClicked: {
                                                    swingBuilder.doOutside {
                                                        try {
                                                            swingBuilder."git-pro-status${bean.getProPath()}".background = operationColor
                                                            swingBuilder."git-pro-status-text${bean.getProPath()}".text = "<html><body>提交中...</body></html>"
                                                            gitAction.commit(bean)
                                                            swingBuilder."git-pro-status${bean.getProPath()}".background = okColor
                                                            swingBuilder."git-pro-status-text${bean.getProPath()}".text = "<html><body>提交成功</body></html>"
                                                        } catch (Exception e) {
                                                            swingBuilder."git-pro-status${bean.getProPath()}".background = failColor
                                                            swingBuilder."git-pro-status-text${bean.getProPath()}".text = "<html><body>提交失败<br>${e.getMessage()}</body></html>"
                                                            throw e
                                                        }
                                                    }
                                                }
                                        )
                                        swingBuilder.button(text: '更新',
                                                mouseClicked: {
                                                    swingBuilder.doOutside {
                                                        try {
                                                            swingBuilder."git-pro-status${bean.getProPath()}".background = operationColor
                                                            swingBuilder."git-pro-status-text${bean.getProPath()}".text = "<html><body>更新中...</body></html>"
                                                            gitAction.update(bean)
                                                            swingBuilder."git-pro-status${bean.getProPath()}".background = okColor
                                                            swingBuilder."git-pro-status-text${bean.getProPath()}".text = "<html><body>更新成功</body></html>"
                                                        } catch (Exception e) {
                                                            swingBuilder."git-pro-status${bean.getProPath()}".background = failColor
                                                            swingBuilder."git-pro-status-text${bean.getProPath()}".text = "<html><body>失败<br>${e.getMessage()}</body></html>"
                                                            throw e
                                                        }
                                                    }
                                                }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        swingBuilder.frame.locationRelativeTo = null
        swingBuilder.frame.visible = true
    }
}
