package com.drools.model;

import lombok.Data;

@Data
public class FlowInfo {

    private Integer id;
	
	private String name;
	
	private String content;
	
	private Integer conditionId;
}