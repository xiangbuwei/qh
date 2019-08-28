 package com.xl.qh.util.excel;

import java.net.URLEncoder;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import com.xl.qh.bean.Entity;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExportExcelUtil {  
  
    private static int NUM = 50000;// 一个sheet的记录数  
  
    /** 
     * @param title 
     *            每个Sheet里的顶部大标题 
     * @param column 
     *            单个sheet里每行数据的列对应的对象属性名称  
     *            column ="rule_name,cityName,specName,ivrName,contactGroup,specName,RulestCont".split(","); 
     * @param data 
     *            数据 
     * @param fileName 
     *            文件名 
     */  
    public void getOutputFile(LinkedHashMap<String, Entity> linkMap, Entity sheetTitle, Map<String,List<Entity>> map, String fileName, HttpServletResponse response) {
        if (map == null || map.isEmpty()) {  
            System.out.println("没有定义导出数据集合");  
        }  
        if (fileName == null || fileName.equals("")) {  
            System.out.println("没有定义输出文件名");  
        }  
  
        HSSFWorkbook workbook = new HSSFWorkbook();// 创建Excel   
  
        try {  
        	Set<String> set = map.keySet();
        	for(Iterator<String> iter=set.iterator();iter.hasNext();) {
            	String sheetName = iter.next();
            	setWorkBook(linkMap, sheetTitle, map, workbook, sheetName);
        	}
        	/*设置response头信息
    		response.reset();
    		response.setContentType("application/x-msdownload"); 
    		response.setHeader("Content-disposition", "attachment; filename=" + URLE) + "记录报表.xls");*/
        	
        	response.reset();
        	response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));
        	response.setHeader("Connection", "close");
        	response.setHeader("Content-Type", "application/octet-stream");
            // 写入流  
            workbook.write(response.getOutputStream());  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
    }  
    
    public void setWorkBook(LinkedHashMap<String, Entity> linkMap , Entity sheetTitle, Map<String,List<Entity>> map, HSSFWorkbook workbook, String sheetName) {
        HSSFSheet sheet = null; // 工作表  
        HSSFRow row = null; // 行  
        HSSFCell cell = null; // 行--列  


        // 字体  
        HSSFFont font = workbook.createFont();  
        font.setColor(HSSFColor.BLACK.index);  
        font.setFontHeightInPoints((short) 10);  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  

        // 父告警标题样式  
        HSSFCellStyle pStyle = workbook.createCellStyle();  
        pStyle.setFont(font);  
        pStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中 
        pStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中

        // 子告警标题样式  
        // HSSFCellStyle sStyle = workbook.createCellStyle();  
        // sStyle.setFont(font);  
        // sStyle.setFillBackgroundColor((short) 0x3399CC);  

        // 告警样式  
        HSSFCellStyle level1Style = workbook.createCellStyle();  
        HSSFPalette palette = workbook.getCustomPalette();  
        palette.setColorAtIndex((short) 9, (byte) (0xFF), (byte) (0x00),  
                (byte) (0x00));  
        palette.setColorAtIndex((short) 10, (byte) (0xFF), (byte) (0xA5),  
                (byte) (0x00));  
        palette.setColorAtIndex((short) 11, (byte) (0xFF), (byte) (0xFF),  
                (byte) (0x00));  
        palette.setColorAtIndex((short) 12, (byte) (0x41), (byte) (0x69),  
                (byte) (0xE1));  
        level1Style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        level1Style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);  
        level1Style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中 
        level1Style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        level1Style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        level1Style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        level1Style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        level1Style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        HSSFCellStyle level2Style = workbook.createCellStyle();  
        level2Style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        level2Style.setFillForegroundColor((short) 10);  
        level2Style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中 
        level2Style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        HSSFCellStyle level3Style = workbook.createCellStyle();  
        level3Style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        level3Style.setFillForegroundColor((short) 11);  
        level3Style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中 
        level3Style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        HSSFCellStyle level4Style = workbook.createCellStyle();  
        level4Style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        level4Style.setFillForegroundColor((short) 12);  
        level4Style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中 
        level4Style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
    	List<Entity> data = map.get(sheetName);
    	Iterator<Entity> it = data.iterator();  
    	int i = 0; //行数 
    	
    	sheet = workbook.createSheet(sheetName); // 工作簿  
    	int columnNameRow = 0;
    	if(sheetTitle != null){
    		CellRangeAddress cra = new CellRangeAddress(0, 0, 0, linkMap.size()-1);        
    	    //在sheet里增加合并单元格  
    	    sheet.addMergedRegion(cra);  
    		row = sheet.createRow(0);
    		sheet.setColumnWidth(0, sheetTitle.getInt("width") * 256);
    		cell = row.createCell(0);
    		cell.setCellValue(new HSSFRichTextString(sheetTitle.getString("title")));
    		cell.setCellStyle(pStyle);
    		columnNameRow ++;
    	}
    	row = sheet.createRow(columnNameRow);  
    	
    	// 在每一页的第一行输入标题  
    	for (int j = 0; j < linkMap.entrySet().size(); j++) {  
    		Entity e = (Entity) linkMap.values().toArray()[j];
    		sheet.setColumnWidth(j, e.getInt("width") * 256);
    		cell = row.createCell(j);  
    		cell.setCellValue(new HSSFRichTextString(e.getString("title")));  
    		cell.setCellStyle(pStyle);  
    	}  
    	
    	// 逐行添加数据  
    	int k = 0;//sheet数  
    	while (it.hasNext()) {  
    		if (i / NUM > k) { // 每50000条记录分一页  
    			k = i / NUM;  
    			sheet = workbook.createSheet("Sheet" + k);  
    			row = sheet.createRow(0);  
    			for (int j = 0; j < linkMap.entrySet().size(); j++) {  
    				cell = row.createCell(j);  
    				cell.setCellValue(new HSSFRichTextString((String) (linkMap.values().toArray()[i])));  
    			}  
    		}  
    		
    		Map<String, Object> dataMap = (Map<String, Object>) it.next();  
    		row = sheet.createRow(i - NUM * k + columnNameRow + 1);  
    		
    		// 输出数据  
    		for (int j = 0; j < linkMap.keySet().size(); j++) {  
    			cell = row.createCell(j);  
    			// 按字段取值  
    			String columnName = (String) linkMap.keySet().toArray()[j];  //取值的key                      
    			String s = String.valueOf(dataMap.get(columnName));
    			if("null".equals(s) ){
    				cell.setCellValue("");
    			}else{
    				cell.setCellValue(new HSSFRichTextString(String.valueOf(dataMap.get(columnName))));
    			}
    			String value = String.valueOf(dataMap.get(columnName));  
    			if (StringUtils.isNotEmpty(value) && !value.equals("0")) { 
    				String level = String.valueOf(dataMap.get(columnName)  
    						+ "_level");  
					switch (level) {
					case "1":
						cell.setCellStyle(level1Style);  
						break;
					case "2":
						cell.setCellStyle(level2Style); 
						break;
					case "3":
						cell.setCellStyle(level3Style); 
						break;
					case "4":
						cell.setCellStyle(level4Style); 
						break;
					default:
						cell.setCellStyle(level1Style); 
						break;
					}
    			}else{
    				cell.setCellStyle(level1Style); 
    			}
    		}  
    		i++;  
    	}  
    }
}
