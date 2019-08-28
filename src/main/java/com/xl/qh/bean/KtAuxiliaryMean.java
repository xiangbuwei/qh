package com.xl.qh.bean;

import java.util.List;


public class KtAuxiliaryMean extends BaseAuxiliaryMean {

	@Override
	public AuxiliaryPoint generateFirstAuxiliaryPoint(List<Entity> list, int cycle) {
		int size = list.size();
		double sum = 0;
		for(int i=1; i<cycle; i++){
			Entity entity = list.get(i);
			sum += entity.getDbl(Constants.NOUMENON);
		}
		TrendType trendType = new TrendType();
		trendType.setLength(1);
		if(sum == 0){
			trendType.setDirection(0);
		}else{
			int direction = sum>0 ? 1 : -1;
			trendType.setDirection(direction);
		}
		return AuxiliaryPoints.newKT(cycle, sum, trendType);
	}

	@Override
	public AuxiliaryPoint generateAuxiliaryPoint(Entity entity, Entity previousEntity
			, Entity previousCycle, int cycle) throws Exception {
		double previousValue = previousEntity.get(Constants.AUXILIARY_POINT, AuxiliaryPoint.class).getValue();
		double cycleValue = previousCycle.getDbl(Constants.NOUMENON);
		double value = previousValue - cycleValue + entity.getDbl(Constants.NOUMENON);
		TrendType trendType = new TrendType();
		if(value == 0){
			trendType.setDirection(0);
		}else{
			int direction = value>0 ? 1 : -1;
			trendType.setDirection(direction);
		}
		return AuxiliaryPoints.newKT(cycle, value, trendType);
	}

}
