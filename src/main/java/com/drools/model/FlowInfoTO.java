package com.drools.model;

import lombok.Data;

@Data
public class FlowInfoTO {

    private Integer id;
	
	private String name;
	
	private Integer conditionId;

	private Integer actionId;

	private String actionContent;
	
	private String conditionContent;
	
	private Integer salienceNo;
}