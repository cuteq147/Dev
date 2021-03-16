package com.drools.model;

import lombok.Data;

@Data
public class MessageResult {

    private String resultMsg;


    public MessageResult(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}

