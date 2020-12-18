package com.rh.git.model;

import java.util.List;

public interface GitParam {
    String getUsername();
    String getPassword();
    String getProPath();
    List<String> getCommitFileProPath();
}
