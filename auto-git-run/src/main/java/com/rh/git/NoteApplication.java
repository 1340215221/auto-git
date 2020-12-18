package com.rh.git;

import com.rh.git.main.GitMain;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 笔记软件启动程序
 */
@SpringBootApplication
public class NoteApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(NoteApplication.class);
        ConfigurableApplicationContext app = builder.headless(false).run(args);
        GitMain main = app.getBean(GitMain.class);
        main.init();
    }

}
