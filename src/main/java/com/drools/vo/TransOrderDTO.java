package com.drools.vo;

import com.drools.model.JudgeResult;
import com.drools.model.ProductSteel;

import lombok.Data;

@Data
public class TransOrderDTO {
	private ProductSteel pt;
	
	private JudgeResult jr;

}
