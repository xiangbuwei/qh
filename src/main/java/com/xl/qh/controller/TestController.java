package com.xl.qh.controller;

import com.xl.qh.bean.AuxiliaryMeans;
import com.xl.qh.bean.BaseAuxiliaryMean;
import com.xl.qh.bean.Constants;
import com.xl.qh.bean.Entity;
import com.xl.qh.enums.HandleEnum;
import com.xl.qh.util.excel.ReadExcel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiangliang
 * @create 2019-08-27 17:25
 */
@RestController
public class TestController {

    @PostMapping(value="/upload")
    public void upload(MultipartFile file, HttpServletRequest request) throws Exception{
        MultipartRequest multipartRequest = null;
        if(request instanceof MultipartRequest){
            multipartRequest = (MultipartRequest) request;
        }else{
            CommonsMultipartResolver resolver = new CommonsMultipartResolver();
            multipartRequest = resolver.resolveMultipart(request);
        }
        MultipartFile mf = multipartRequest.getFile("file");
        Map<String, String> title = new HashMap<>();
        title.put("日期", "date");
        title.put("时间", "time");
        title.put("开盘", "open");
        title.put("最高", "high");
        title.put("最低", "low");
        title.put("收盘", "close");
        List<Entity> list = ReadExcel.readExcelValuesByTitle(title, ReadExcel.createWorkbook(file));
        System.out.println(list.size());
        System.out.println(list.get(0));
        List<Entity> rtList = AuxiliaryMeans.newKtAuxiliaryMean().generateIndex(list, 10);
        Entity test = new Entity();
        for(Entity e : rtList){
            System.out.println(e);
            if(e.containsKey(Constants.HANDLE_TYPE) && e.containsKey(Constants.START_PRICE) && e.containsKey(Constants.END_PRICE)){
                HandleEnum handleType = (HandleEnum) e.get(Constants.HANDLE_TYPE);
                double startPrice =  e.getDbl(Constants.START_PRICE);
                double endPrice =  e.getDbl(Constants.END_PRICE);
                double m = 0;
                if(handleType == HandleEnum.BUY){
                    m = endPrice-startPrice;
                    if(m>0){
                        test.put("buy+",test.getInt("buy+") +1);
                    }else{
                        test.put("buy-",test.getInt("buy-") +1);
                    }
                    test.put("buySum", m + test.getDbl("buySum"));
                }else{
                    m = startPrice-endPrice;
                    if(m>0){
                        test.put("sell+",test.getInt("sell+") +1);
                    }else{
                        test.put("sell-",test.getInt("sell-") +1);
                    }
                    test.put("sellSum", m + test.getDbl("sellSum"));
                }
            }
        }
        System.out.println(test);
    }
}
