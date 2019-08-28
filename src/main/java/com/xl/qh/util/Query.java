package com.xl.qh.util;

import com.xl.qh.bean.Constants;
import com.xl.qh.bean.Entity;
import com.xl.qh.bean.Signal;
import com.xl.qh.enums.QueryTypeEnum;
import com.xl.qh.method.Functions;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;


public class Query {
	
	public static final String URL_ = "http://hq.sinajs.cn/list=MA1909";
	
	public static final String URL_DAY = "http://stock2.finance.sina.com.cn/futures/api/json.php/IndexService.getInnerFuturesDailyKLine?symbol=";
	public static final String URL_M = "http://stock2.finance.sina.com.cn/futures/api/json.php/IndexService.getInnerFuturesMiniKLine";
	//public static final String URL_M1 = "http://stock2.finance.sina.com.cn/futures/api/json.php/IndexService.getInnerFuturesMiniKLine1m";
	public static String code1 = Constants.GOODS_RB + 1910;
	public static String code2 = Constants.GOODS_MA + 2001;
	
	public static void main(String[] args) throws Exception {
		String result1 = queryKt(QueryTypeEnum.M15, code1);
		System.out.println(result1);
		String result2 = queryKt(QueryTypeEnum.M15, code2);
		System.out.println(result2);
	}
	
	
	@SuppressWarnings("unused")
	public static String queryKt(QueryTypeEnum queryType, String code) throws Exception{
		String url = createUrl(queryType,code);
		String result = HttpRequest.sendGet(url, null);
		//System.out.println(result);
		Entity entity = new Entity();
		List<Entity> list = null;
		Signal kt = null;
		switch (queryType) {
			case DAY:
				entity = DataUtils.convertMap(result);
				break;
			default:
				list = DataUtils.convertMapFromArray(result);
				entity = list.get(0);
				int cycle = 10;
				kt = Functions.KT(list, queryType, cycle);
				//System.out.println("KT >>> "+ kt);
				break;
		}
		String price = QueryNew(code);
		
		//System.out.println(entity);
		return kt.getHandle() + "\t" + price;
	}
	
	public static String QueryNew(String code){
		String url = "http://hq.sinajs.cn/list=" + code;
		String result = HttpRequest.sendGet(url, null);
		String[] strs = result.split(",");
		//System.out.println(strs[8]);
		return strs[8];
	}
	
	public static void getNowTime(){
		DateFormat format = DateFormat.getTimeInstance();
		System.out.println(format.format(new Date()));
	}
	
	public static String createUrl(QueryTypeEnum queryType,String code){
		StringBuilder sb = new StringBuilder();
		switch(queryType){
			case M5:
				//http://stock2.finance.sina.com.cn/futures/api/json.php/IndexService.getInnerFuturesMiniKLine5m?symbol=MA1905
				sb.append(URL_M).append(queryType.getVal()).append("?symbol=").append(code);
				break;
			case DAY:
				//http://stock2.finance.sina.com.cn/futures/api/json.php/IndexService.getInnerFuturesDailyKLine?symbol=M0
				sb.append(URL_DAY).append(code);
				break;
			default:
				sb.append(URL_M).append(queryType.getVal()).append("?symbol=").append(code);
				break;
		}
		return sb.toString();
	}
	
}
