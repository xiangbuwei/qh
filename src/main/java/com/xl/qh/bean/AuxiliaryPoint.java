package com.xl.qh.bean;


import com.xl.qh.enums.AuxiliaryMeansEnum;

public class AuxiliaryPoint{
	/**类型**/
	public AuxiliaryMeansEnum type;
	/**周期**/
	public int cycle;
	/**指标值**/
	public double value;
	/**方向类型**/
	public TrendType trendType;
	
	public AuxiliaryPoint() {
		super();
	}

	public AuxiliaryPoint(AuxiliaryMeansEnum type, int cycle, double value, TrendType trendType) {
		super();
		this.type = type;
		this.cycle = cycle;
		this.value = value;
		this.trendType = trendType;
	}

	public AuxiliaryMeansEnum getType() {
		return type;
	}

	public void setType(AuxiliaryMeansEnum type) {
		this.type = type;
	}

	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public TrendType getTrendType() {
		return trendType;
	}

	public void setTrendType(TrendType trendType) {
		this.trendType = trendType;
	}

	@Override
	public String toString() {
		return "AuxiliaryPoint [type=" + type + ", cycle=" + cycle + ", value=" + value + ", trendType=" + trendType
				+ "]";
	}
	
}
