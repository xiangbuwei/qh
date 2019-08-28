package com.xl.qh.enums;

public enum MethodEnum {
	KT("KT"),MA_10("MA_10)");
	
	private String name;

	private MethodEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
