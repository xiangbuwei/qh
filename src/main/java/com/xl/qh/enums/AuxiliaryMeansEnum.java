package com.xl.qh.enums;

public enum AuxiliaryMeansEnum {
	
	MA("MA"),K("K");
	
	private String name;
	
	private AuxiliaryMeansEnum(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
