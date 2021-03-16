package com.drools.model;

import lombok.Data;

@Data
public class OrderInfo {

    private String reverseAngle;

    private String packageNo;
	
	private String skinCover;
	
	private String tag;
	
	private String skinColor;
	
	private String steelEle;
	
	private String orginPlace;


    public OrderInfo(String reverseAngle, String packageNo,String skinCover,
    		String tag, String skinColor,String steelEle,String orginPlace) {
        this.reverseAngle = reverseAngle;
        this.packageNo = packageNo;
		this.skinCover = skinCover;
		this.tag = tag;
		this.skinColor = skinColor;
		this.steelEle = steelEle;
		this.orginPlace = orginPlace;
    }
}

