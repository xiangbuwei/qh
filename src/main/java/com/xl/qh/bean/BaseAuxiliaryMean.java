package com.xl.qh.bean;

import com.xl.qh.enums.HandleEnum;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAuxiliaryMean {
	
	public List<Entity> generateIndex(List<Entity> list,int cycle) throws Exception{
		List<Entity> testResult = new ArrayList<>();
		for(int i=0; i<list.size(); i++){
			Entity rt = new Entity();
			Entity entity = list.get(i);
			AuxiliaryPoint auxiliaryPoint = null;
			//计算K线实体
			entity.put(Constants.NOUMENON,  entity.getDbl("close") - entity.getDbl("open"));
			if(i == cycle){//第一次
				auxiliaryPoint = generateFirstAuxiliaryPoint(list, cycle);
				entity.put(Constants.AUXILIARY_POINT, auxiliaryPoint);
				recordTest(testResult, auxiliaryPoint, rt, entity);
			}else if(i> cycle){
				Entity previousEntity = list.get(i-1);//上一个
				Entity previousCycle = list.get(i-cycle);//上一个周期最早的指标点
				auxiliaryPoint = generateAuxiliaryPoint(entity, previousEntity, previousCycle, cycle);
				TrendType previousTrendType =((AuxiliaryPoint) previousEntity.get(Constants.AUXILIARY_POINT)).getTrendType();
				TrendType trendType = auxiliaryPoint.getTrendType();
				//如果趋势不变或者无趋势,信号长度累计
				if(trendType.getDirection() == 0 ||
						trendType.getDirection() == previousTrendType.getDirection() ){
					trendType.setLength(previousTrendType.getLength()+1);
				}else{//趋势改变,信号长度重新计算
					trendType.setLength(1);
					recordTest(testResult, auxiliaryPoint, rt, entity);
				}
				entity.put(Constants.AUXILIARY_POINT, auxiliaryPoint);
			}else{
				continue;
			}
		}
		return testResult;
	}

	public void recordTest(List<Entity> testResult, AuxiliaryPoint auxiliaryPoint, Entity rt, Entity entity){
		int direction = auxiliaryPoint.getTrendType().direction;
		if(direction != 0){
			rt.put(Constants.HANDLE_TYPE, direction==1 ? HandleEnum.BUY : HandleEnum.SELL);
			rt.put(Constants.START_PRICE, entity.getDbl("close"));
			rt.put("data", entity);
			if(testResult.size()>0){
				Entity lastRt = testResult.get(testResult.size() - 1);
				lastRt.put(Constants.END_PRICE, entity.getDbl("close"));
			}
			testResult.add(rt);
		}
	}
	
	/**
	 * 生成第一个指标点
	 * @param list
	 * @return
	 */
	public abstract AuxiliaryPoint generateFirstAuxiliaryPoint(List<Entity> list, int cycle);
	
	/**
	 * 生成当前的指标点
	 * @param entity 当前
	 * @param previousEntity 上一个
	 * @param previousCycle 上一周期最早的值
	 * @return
	 * @throws Exception 
	 */
	public abstract AuxiliaryPoint generateAuxiliaryPoint(Entity entity, Entity previousEntity, Entity previousCycle, int cycle) throws Exception;

}
