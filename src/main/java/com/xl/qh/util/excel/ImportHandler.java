package com.xl.qh.util.excel;


import com.xl.qh.bean.Entity;

import java.util.List;

public interface ImportHandler {
	
	Entity handler(List<Entity> list);
}
