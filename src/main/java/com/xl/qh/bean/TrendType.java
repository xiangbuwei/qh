package com.xl.qh.bean;

public class TrendType {
	public static final int UP = 1;
	public static final int DOWN = -1;
	public static final int FLAT = 0;

	/**方向(1= ↑,0= -, -1= ↓)**/
	public int direction;
	/**连续次数**/
	public int length;
	
	public TrendType(){
		
	}
	
	public TrendType(int direction, int length) {
		this.direction = direction;
		this.length = length;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return "TrendType [direction=" + direction + ", length=" + length + "]";
	}
	
}

