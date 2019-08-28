package com.xl.qh.bean;


import com.xl.qh.enums.AuxiliaryMeansEnum;

public class AuxiliaryPoints {
	
	public static AuxiliaryPoint newMA(int cycle,double value,TrendType trendType){
		return new AuxiliaryPoint(AuxiliaryMeansEnum.MA, cycle, value, trendType);
	}
	
	public static AuxiliaryPoint newKT(int cycle,double value,TrendType trendType){
		return new AuxiliaryPoint(AuxiliaryMeansEnum.K, cycle, value, trendType);
	}
}
