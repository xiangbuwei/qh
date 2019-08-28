package com.xl.qh.enums;

public enum QueryTypeEnum {
	M1("1m"),M5("5m"),M15("15m"),HOUR("60m"),DAY("day");
	
	private String val;
	
	private QueryTypeEnum(String val){
		this.val = val;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}
}
