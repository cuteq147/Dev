package com.drools.model;

import lombok.Data;

@Data
public class ProductSteel {
	
	private String type;

    private String reverseAngle;

    private String packageNo;
	
	private String skinCover;
	
	private String tag;
	
	private String skinColor;
	
	private String steelEle;
	
	private String orginPlace;


    public ProductSteel(String type,
    		String reverseAngle, String packageNo,String skinCover,
    		String tag, String skinColor,String steelEle,String orginPlace) {
    	this.type = type;
    	this.reverseAngle = reverseAngle;
        this.packageNo = packageNo;
		this.skinCover = skinCover;
		this.tag = tag;
		this.skinColor = skinColor;
		this.steelEle = steelEle;
		this.orginPlace = orginPlace;
    }
}

