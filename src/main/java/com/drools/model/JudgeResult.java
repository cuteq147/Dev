package com.drools.model;

import lombok.Data;

@Data
public class JudgeResult {

    private String resultTag;
    
    private String anTag = "Nothing";


    public JudgeResult(String resultTag) {
        this.resultTag = resultTag;
    }
}

