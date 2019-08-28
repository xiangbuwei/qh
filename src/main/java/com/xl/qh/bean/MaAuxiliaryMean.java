package com.xl.qh.bean;

import java.util.List;

public class MaAuxiliaryMean extends BaseAuxiliaryMean {

	@Override
	public AuxiliaryPoint generateFirstAuxiliaryPoint(List<Entity> list, int cycle) {
		//求第一个MA的值
		int size = list.size();
		double sum = 0;
		for(int i=1; i<cycle; i++){
			Entity entity = list.get(size-i);
			sum += entity.getDbl(Constants.CLOSE_PRICE);
		}
		double firstMA = sum/cycle;
		return AuxiliaryPoints.newMA(cycle, firstMA, new TrendType(0,1));
	}

	@Override
	public AuxiliaryPoint generateAuxiliaryPoint(Entity entity, Entity previousEntity, Entity previousCycle, int cycle) {
		double previousMA = ((AuxiliaryPoint)previousEntity.get(Constants.AUXILIARY_POINT)).getValue();
		double cycleMA = ((AuxiliaryPoint)previousCycle.get(Constants.CLOSE_PRICE)).getValue();
		double cycleSum = (previousMA * cycle - cycleMA)+entity.getDbl(Constants.CLOSE_PRICE);
		double ma = cycleSum/cycle;
		entity.put(Constants.AUXILIARY_POINT, ma);//保存MA的值
		int direction = TrendType.FLAT;
		if(ma > previousMA){
			direction = TrendType.UP;
		}else if(ma < previousMA){
			direction = TrendType.DOWN;
		}
		TrendType trendType = new TrendType();
		trendType.setDirection(direction);
		return AuxiliaryPoints.newMA(cycle, ma, trendType);
	}

}
