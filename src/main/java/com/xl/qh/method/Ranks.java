package com.xl.qh.method;


import com.xl.qh.bean.Signal;
import com.xl.qh.enums.HandleEnum;
import com.xl.qh.enums.IndexEnum;
import com.xl.qh.enums.QueryTypeEnum;
import com.xl.qh.enums.RankEnum;

public class Ranks {

	public static Signal RankKT(HandleEnum handle, IndexEnum idex, QueryTypeEnum queryType, int cycle){
		Signal signal = new Signal();
		signal.setHandle(handle);
		if(queryType == QueryTypeEnum.M5){
			signal.setRank(RankEnum.B);
			signal.setScore(40);
		}else{
			signal.setRank(RankEnum.A);
			signal.setScore(60);
		}
		signal.setIndex(idex);
		return signal;
	}
	
	public static Signal RankMA(HandleEnum handle,IndexEnum idex, QueryTypeEnum queryType, int cycle){
		Signal signal = new Signal();
		signal.setHandle(handle);
		if(queryType == QueryTypeEnum.M5){
			signal.setRank(RankEnum.B);
			signal.setScore(40);
		}else{
			signal.setRank(RankEnum.A);
			signal.setScore(60);
		}
		signal.setIndex(idex);
		return signal;
	}
	
}
