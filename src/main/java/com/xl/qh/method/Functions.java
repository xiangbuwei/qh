package com.xl.qh.method;

import com.xl.qh.bean.*;
import com.xl.qh.enums.HandleEnum;
import com.xl.qh.enums.IndexEnum;
import com.xl.qh.enums.QueryTypeEnum;
import com.xl.qh.util.DataUtils;
import com.xl.qh.util.HttpRequest;
import com.xl.qh.util.Query;

import java.util.List;

public class Functions {
	/**
	 * 
	 * @param code 代码+合约
	 * @param queryType 查询周期
	 * @param cycle 指标周期
	 * @return
	 * @throws Exception
	 */
	public static Signal KT(String code, QueryTypeEnum queryType, int cycle) throws Exception{
		String url = Query.createUrl(queryType,code);
		String result = HttpRequest.sendGet(url, null);
		List<Entity> list = DataUtils.convertMapFromArray(result);
		return KT(list, queryType, cycle);
	}
	
	/**
	 * 
	 * @param code 代码+合约
	 * @param queryType 查询周期
	 * @param cycle 指标周期
	 * @return
	 * @throws Exception
	 */
	public static Signal MA(String code, QueryTypeEnum queryType, int cycle) throws Exception{
		String url = Query.createUrl(queryType,code);
		String result = HttpRequest.sendGet(url, null);
		List<Entity> list = DataUtils.convertMapFromArray(result);
		return MA(list, queryType, cycle);
	}
	
	/**
	 * WCKX
	 * @param list 数据集合
	 * @param queryType 查询周期	
	 * @param cycle 指标周期
	 * @return
	 * @throws Exception
	 */
	public static Signal KT(List<Entity> list, QueryTypeEnum queryType, int cycle) throws Exception{
		List<Entity> indexList = AuxiliaryMeans.newKtAuxiliaryMean().generateIndex(list, cycle);
		TrendType trendType = indexList.get(0).get(Constants.AUXILIARY_POINT, AuxiliaryPoint.class).getTrendType();
		HandleEnum handle = null;
		switch(trendType.getDirection()){
			case TrendType.UP:
				handle = HandleEnum.BUY;
				break;
			case TrendType.DOWN:
				handle = HandleEnum.SELL;
				break;
			default:
				handle = HandleEnum.DIRECTIONLESS;
				break;
		}
		return Ranks.RankKT(handle, IndexEnum.K, queryType, cycle);
	}
	
	/**
	 * AVG
	 * @param list 数据集合
	 * @param queryType 查询周期
	 * @param cycle 指标周期
	 * @return
	 * @throws Exception
	 */
	public static Signal MA(List<Entity> list,QueryTypeEnum queryType, int cycle) throws Exception{
		List<Entity> list2 = AuxiliaryMeans.newMaAuxiliaryMean().generateIndex(list, cycle);
		TrendType trendType = ((AuxiliaryPoint)list2.get(0).get(Constants.AUXILIARY_POINT)).getTrendType();
		int direction = trendType.getDirection();
		Entity entity = list.get(0);
		double closePrice = entity.getDbl(Constants.CLOSE_PRICE);
		double indexValue = entity.get(Constants.AUXILIARY_POINT, AuxiliaryPoint.class).getValue();
		if(direction == 1 && closePrice >= indexValue){
			return Ranks.RankMA(HandleEnum.BUY, IndexEnum.MA, queryType, cycle);
		}else if(direction == -1 && closePrice <= indexValue){
			return Ranks.RankMA(HandleEnum.SELL, IndexEnum.MA, queryType, cycle);
		}
		return null;
	}
	
}
