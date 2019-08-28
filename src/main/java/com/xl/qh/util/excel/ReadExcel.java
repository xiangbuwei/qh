package com.xl.qh.util.excel;

import com.xl.qh.bean.Entity;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;


/**
 * @author hewangtong
 */
public class ReadExcel {
    /**
     * 身份证号码
     */
    public static final String SFZHM = "居民证号";

    /**
     * 联系号码
     */
    public static final String PHONE_NO = "个人联系电话";

    /**
     * 暂住地
     */
    public static final String RESIDENCE_ADDR = "街路巷名称";

    // 总行数
    private int totalRows = 0;
    // 总条数
    private int totalCells = 0;
    // 错误信息接收器
    private String errorMsg;

    // 构造方法
    public ReadExcel() {
    }

    // 获取总行数
    public int getTotalRows() {
        return totalRows;
    }

    // 获取总列数
    public int getTotalCells() {
        return totalCells;
    }

    // 获取错误信息
    public String getErrorInfo() {
        return errorMsg;
    }

    /**
     * 读EXCEL文件，获取信息集合
     *
     * @param fielName
     * @return
     */
    public List<Map<String, Object>> getExcelInfo(MultipartFile mFile) {
        String fileName = mFile.getOriginalFilename();// 获取文件名
//        List<Map<String, Object>> userList = new LinkedList<Map<String, Object>>();
        try {
            if (!validateExcel(fileName)) {// 验证文件名是否合格
                return null;
            }
            boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
            if (isExcel2007(fileName)) {
                isExcel2003 = false;
            }
            return createExcel(mFile.getInputStream(), isExcel2003);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据excel里面的内容读取客户信息
     *
     * @param is          输入流
     * @param isExcel2003 excel是2003还是2007版本
     * @return
     * @throws IOException
     */
    public List<Map<String, Object>> createExcel(InputStream is, boolean isExcel2003) {
        try {
            Workbook wb = null;
            if (isExcel2003) {// 当excel是2003时,创建excel2003
                wb = new HSSFWorkbook(is);
            } else {// 当excel是2007时,创建excel2007
                wb = new XSSFWorkbook(is);
            }
            return readExcelValue(wb);// 读取Excel里面客户的信息
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取Excel里面客户的信息
     *
     * @param wb
     * @return
     */
    private List<Map<String, Object>> readExcelValue(Workbook wb) {
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        this.totalRows = sheet.getPhysicalNumberOfRows();
        // 得到Excel的列数(前提是有行数)
        if (totalRows > 1 && sheet.getRow(0) != null) {
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }
        List<Map<String, Object>> cardStack = new ArrayList<Map<String, Object>>();
        // 循环Excel行数
        for (int r = 1; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            // 循环Excel的列
            Map<String, Object> map = new HashMap<String, Object>();
            for (int c = 0; c < this.totalCells; c++) {
                Cell cell = row.getCell(c);
                if (null != cell) {
                    if (c == 0) {
                        // 如果是纯数字,比如你写的是25,cell.getNumericCellValue()获得是25.0,通过截取字符串去掉.0获得25
                        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            String sfzhm = String.valueOf(cell.getNumericCellValue());
                            map.put("sfzhm", sfzhm.substring(0, sfzhm.length() - 2 > 0 ? sfzhm.length() - 2 : 1));// 身份证
                        } else {
                            map.put("sfzhm", cell.getStringCellValue());// 身份证
                        }
                    } else if (c == 1) {
                        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            String phoneNo = String.valueOf(cell.getNumericCellValue());
                            map.put("phoneNo", phoneNo.substring(0, phoneNo.length() - 2 > 0 ? phoneNo.length() - 2 : 1));// 联系方式
                        } else {
                            map.put("phoneNo", cell.getStringCellValue());// 联系方式
                        }
                    }
                }
            }
            // 添加到list
            cardStack.add(map);
        }
        return cardStack;
    }

    /**
     * 验证EXCEL文件
     *
     * @param filePath
     * @return
     */
    public boolean validateExcel(String filePath) {
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
            errorMsg = "文件名不是excel格式";
            return false;
        }
        return true;
    }

    // @描述：是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    // @描述：是否是2007的excel，返回true是2007
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    /**
     * 读取表头
     *
     * @param wb
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> readHeader(MultipartFile file) throws Exception {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        Sheet sheet0 = getSheet(createWorkbook(file));
        Row header = getHeaderRow(sheet0);
        Row row1 = sheet0.getRow(1);
        int cells = header.getPhysicalNumberOfCells();
        for (int i = 0; i < cells; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            Cell cell = header.getCell(i);
            Cell c1 = row1.getCell(i);
            String name = cell.getStringCellValue();
            map.put("name", name);
            String dataType = getCellDataType(c1);
            map.put("type", dataType);
            map.put("remark", "number".equals(dataType) ? 123456789 : "这是一串文本");
            map.put("checkStatus", "居民证号".equals(name) ? true : false);//居民证号默认选中
            resultList.add(map);
        }
        return resultList;
    }

    public static Workbook createWorkbook(MultipartFile file) throws Exception {
        Workbook wb = null;
        String name = file.getOriginalFilename();
        if (name == null) {
            throw new Exception("文件名不能为空");
        } else if (isExcel2003(name)) {
            wb = new HSSFWorkbook(file.getInputStream());
        } else if (isExcel2007(name)) {
            wb = new XSSFWorkbook(file.getInputStream());
        } else {
            System.out.println(name);
            throw new Exception("未知文件类型");
        }
        return wb;
    }

    public static Workbook createWorkbook(File file) throws Exception {
        Workbook wb = null;
        String name = file.getName();
        if (name == null) {
            throw new Exception("文件名不能为空");
        } else if (isExcel2003(name)) {
            wb = new HSSFWorkbook(new FileInputStream(file));
        } else if (isExcel2007(name)) {
            wb = new XSSFWorkbook(new FileInputStream(file));
        } else {
            System.out.println(name);
            throw new Exception("未知文件类型");
        }
        return wb;
    }

    /**
     * 默认返回String类型
     *
     * @param cell
     * @return
     */
    public static String getCellDataType(Cell cell) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        if (pattern.matcher(cell.getStringCellValue()).matches()) {
            return "number";
        }
        return "string";
    }

    /**
     * 通过 行标题 读取数据
     *
     * @param titles 行标题(表头)
     * @return
     * @throws Exception
     */
    public static List<Entity> readExcelValuesByTitle(Map<String, String> map, Workbook wb) throws Exception {
        List<Entity> resultList = new ArrayList<>();
        Sheet sheet0 = getSheet(wb);
        Row header = getHeaderRow(sheet0);
        Map<Integer, String> cacheMap = new HashMap<Integer, String>();
        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            if (header.getCell(i) == null) {
                break;
            }
            String cellValue = header.getCell(i).getStringCellValue().trim();
            if (cellValue == null) {
                break;
            }
            if(map.containsKey(cellValue)){
                cacheMap.put(i, map.get(cellValue));
            }
        }
        for (int i = 1; i < sheet0.getPhysicalNumberOfRows(); i++) {
            Entity rm = new Entity();
            Row row = sheet0.getRow(i);
            Iterator<Integer> it = cacheMap.keySet().iterator();
            while (it.hasNext()) {
                int key = it.next();
                Cell cell = row.getCell(key);
                if(cell == null){
                	continue;
                }
                Object cellValue = getCellValue(cell);
                rm.put(cacheMap.get(key), cellValue);
            }
            if(!rm.isEmpty()) {
            	resultList.add(rm);
            }
        }
        return resultList;
    }

    private static Object getCellValue(Cell cell){
        Object cellValue = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                cellValue = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellValue = cell.getDateCellValue();
                } else {
                    cellValue = cell.getNumericCellValue();
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_FORMULA:
                cellValue = cell.getCellFormula();
                break;
            case Cell.CELL_TYPE_BLANK:
                System.out.println();
                break;
            default:
                System.out.println();
        }
        return cellValue;
    }

    /**
     * 获取要处理的 sheet
     *
     * @param wb
     * @return
     */
    public static Sheet getSheet(Workbook wb) {
        return wb.getSheetAt(0);
    }

    /**
     * 获取表头行
     *
     * @param sheet
     * @return row
     */
    public static Row getHeaderRow(Sheet sheet) {
        return sheet.getRow(0);
    }

    public static void main(String[] args) {
        Map<String, String> title = new HashMap<>();
        title.put("日期", "date");
        title.put("时间", "time");
        title.put("开盘", "open");
        title.put("最高", "high");
        title.put("最低", "low");
        title.put("收盘", "close");
        List<Entity> list = null;
        try {
           list = readExcelValuesByTitle(title, createWorkbook(new File("D:\\BaiduNetdiskDownload\\30#RBL8.xls")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(Entity e : list){
            System.out.println(e);
        }
    }

}