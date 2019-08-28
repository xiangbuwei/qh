package com.xl.qh.util;

import com.xl.qh.bean.Constants;
import com.xl.qh.bean.Entity;

import java.util.*;

public class DataUtils {
	
	public static List<Entity> convertMapFromArray(String data){
		List<Entity> list = new ArrayList<Entity>();
		//去掉首尾[[]]
		String[] strs = data.replace("[[", "").replace("]]", "").replace("\"", "").split("\\],\\[");
		for(String str : strs){
			Entity entity = new Entity();
			str.replace("\"", "");
			String[] array = str.split(",");
			entity.put(Constants.DATE_TIME, array[0]);
			entity.put(Constants.OPENT_PRICE, array[1]);
			entity.put(Constants.HIGH_PRICE, array[2]);
			entity.put(Constants.LOW_PRICE, array[3]);
			entity.put(Constants.CLOSE_PRICE, array[4]);
			entity.put(Constants.NOUMENON, Double.parseDouble(array[4]) - Double.parseDouble(array[1]));
			list.add(entity);
		}
		return list;
	}
	
	public static Entity convertMap(String data){
		Entity entity = new Entity();
		int index = data.indexOf("\"")+1;
		String dataStr = data.substring(index, data.indexOf("\"",index+1));
		//System.out.println(dataStr);
		String[] strs = dataStr.split(",");
		Map<String,Integer> keyMap = getKeyMap();
		Iterator<String> it = keyMap.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			int val = keyMap.get(key);
			entity.put(key,strs[val]);
		}
		return entity;
	}
	
	
	
	//var hq_str_MA1905="郑醇1905,145959,2317.00,2353.00,2317.00,2317.00,2346.00,2347.00,2346.00,2336.00,2353.00,1,747,1067152,1516532,郑,郑醇,2018-12-26,1,2505.000,2306.000,2525.000,2306.000,2603.000,2306.000,3220.000,2306.000,75.734";
	
	//收盘后
	public static Map<String,Integer> getKeyMap(){
		Map<String,Integer> resultMap = new HashMap<>();
		resultMap.put(Constants.DATE, 17);
		resultMap.put(Constants.TIME,1);
		resultMap.put(Constants.OPENT_PRICE, 2);
		resultMap.put(Constants.HIGH_PRICE, 3);
		resultMap.put(Constants.LOW_PRICE, 4);
		resultMap.put(Constants.CLOSE_PRICE, 8);
		return resultMap;
	}
}
