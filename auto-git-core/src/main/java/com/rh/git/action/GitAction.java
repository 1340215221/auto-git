package com.rh.git.action;

import com.rh.git.model.GitParam;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.EmptyCommitException;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Component;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * git操作
 */
@Component
public class GitAction {

    /**
     * 更新
     */
    public void update(GitParam param) {
        if (param == null || StringUtils.isBlank(param.getProPath())) {
            return;
        }

        File file = new File(param.getProPath());
        if (!file.exists() || !file.isDirectory()) {
            throw new RuntimeException();
        }

        try {
            Git git = Git.open(file);
            git.pull().setStrategy(MergeStrategy.SIMPLE_TWO_WAY_IN_CORE).call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("update ok");
    }

    /**
     * 提交
     */
    public void commit(GitParam param) {
        if (param == null || StringUtils.isBlank(param.getProPath()) || CollectionUtils.isEmpty(param.getCommitFileProPath())) {
            return;
        }
        File file = new File(param.getProPath());
        if (!file.exists() || !file.isDirectory()) {
            throw new RuntimeException();
        }
        String commitMsg = "update";
        // 获得git对象
        Git git;
        try {
            git = Git.open(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // add
        try {
            AddCommand add = git.add();
            param.getCommitFileProPath().forEach(add::addFilepattern);
            add.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // commit
        try {
            git.commit().setMessage(commitMsg).setAllowEmpty(false).call();
        } catch (EmptyCommitException e) {
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // push
        try {
            git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(param.getUsername(), param.getPassword())).call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // todo 冲突时, 不会推送, 也不会有异常提示
        System.out.println("commit ok");
    }

}
