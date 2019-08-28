package com.xl.qh.bean;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;


public class Entity extends HashMap<String, Object>{
	/**
	 * 当前页数
	 */
	public static final String PAGE_NO = "pageNo";
	/*
	 * 每页行数
	 */
	public static final String PAGE_SIZE = "pageSize";
	/*
	 * 总记录数
	 */
	public static final String TOTAL_RECORD = "totalRecord";
	/*
	 * 总页数
	 */
	public static final String TOTAL_PAGE = "totalPage";
	/*
	 * 当前页开始行序号
	 */
	public static final String START_NO = "startNo";
	/*
	 * 当前页结束行序号
	 */
	public static final String END_NO = "endNo";
	
	private static final long serialVersionUID = 8436468155422604124L;
	
	public Entity(){
		super();
	}
	
	public Entity(HttpServletRequest request){
		Enumeration<String> enums = request.getParameterNames();
		while(enums.hasMoreElements()){
			String paramName=enums.nextElement();
			String value = request.getParameter(paramName).trim();
			if(StringUtils.isNotEmpty(value)){
				//处理中文乱码
				if(!Charset.forName("GBK").newEncoder().canEncode(value)){
					try {
						value = new String(value.getBytes("ISO8859-1"),"UTF-8");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				this.put(paramName,value);
			}
		}
	}
	
	/**
	 * 
	 * 设置分页参数
	 * @param request
	 */
	public void initPage(HttpServletRequest request){
		String pageNo=request.getParameter(PAGE_NO);
		String pageSize=request.getParameter(PAGE_SIZE);
		if(StringUtils.isNotEmpty(pageNo)){
			this.put(PAGE_NO, Integer.valueOf(pageNo));
		}else{
			this.put(PAGE_NO, 1);
		}
		if(StringUtils.isNotEmpty(pageSize)){
			this.put(PAGE_SIZE, Integer.valueOf(pageSize));
		}else{
			this.put(PAGE_SIZE, 10);
		}
		this.put(START_NO, (this.getInt(PAGE_NO)-1)*this.getInt(PAGE_SIZE)+1);
		this.put(END_NO, this.getInt(PAGE_NO)*this.getInt(PAGE_SIZE));
	}
	
	/**
	 * 
	 * 设置分页参数
	 * @param request
	 * @param pageSize 每页显示数量
	 */
	public void initPage(HttpServletRequest request,int pageSize){
		String pageNo=request.getParameter(PAGE_NO);
		if(StringUtils.isNotEmpty(pageNo)){
			this.put(PAGE_NO, Integer  .valueOf(pageNo));
		}else{
			this.put(PAGE_NO, 1);
		}
		this.put(PAGE_SIZE, pageSize);
		
		this.put(START_NO, (this.getInt(PAGE_NO)-1)*this.getInt(PAGE_SIZE)+1);
		this.put(END_NO, this.getInt(PAGE_NO)*this.getInt(PAGE_SIZE));
	}
	
	/**
	 * 设置总页数,必须在设置总记录数后调用
	 */
	public void setTotalPage(){
		if(this.getInt(PAGE_SIZE)>0){
			if(this.getInt(TOTAL_RECORD)<1){
				this.put(TOTAL_PAGE, 1);
			}else{
				int count = this.getInt(TOTAL_RECORD);
				int size = this.getInt(PAGE_SIZE);
				this.put(TOTAL_PAGE, count%size==0 ? count/size : count/size+1);
			}
		}
	}
	
	@Override
	public Entity put(String field, Object value) {
		if(StringUtils.isBlank(field)||value==null){
			return null;
		}
		if (value instanceof String && StringUtils.isBlank(value.toString())) {
			return null;
		}
		super.put(field, value);
		return this;
	}
	
	public Entity orderBy(String value) {
		if(StringUtils.isBlank(value)){
			return null;
		}
		super.put("orderby", value);
		return this;
	}
	
	public Double getDouble(String field){
		return (Double) super.get(field);
	}
	
	public double getDbl(String field){
		Object value=super.get(field);
		if(value!=null){
			return Double.valueOf(value.toString());
		}
		return 0;
	}
	
	public String getString(String field){
		if(super.get(field)==null){
			return null;
		}else{
			return  super.get(field).toString();
		}
	}
	
	public Date getDate(String field){
		Object obj= super.get(field);
		if(obj instanceof Date){
			return (Date)obj;
		}else if(obj instanceof Timestamp){
			Timestamp timestamp = (Timestamp) obj;
			return new Date(timestamp.getTime());
		}else{
			return null;
		}
	}
	
	public Integer getInteger(String field){
		Object id=super.get(field);
		if(id!=null){
			return Integer.valueOf(id.toString());
		}
		return null;
	}
	
	public int getInt(String field){
		Object value=super.get(field);
		if(value!=null){
			return Double.valueOf(value.toString()).intValue();
		}
		return 0;
	}
	
	@JSONField(format = "yyyy-MM-dd hh:mm:ss")
	public Object get(String field){
		Object obj=super.get(field);
		if(obj instanceof Timestamp){
			return getDate(field);
		}else{
			return obj;
		}
	}

	public <T>T get(String field,Class<T> clazz) throws Exception{
		Object object = super.get(field);
		if(object == null){
			return null;
		}else if(clazz.isInstance(object)){
			return clazz.cast(object);
		}else{
			throw new Exception(clazz.getName()+"类型不匹配");
		}
	}
	
}