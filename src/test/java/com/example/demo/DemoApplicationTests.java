package com.example.demo;

import dcein.springboot.demo.DemoApplication;
import dcein.springboot.demo.controller.supplier.SupplierController;
import dcein.springboot.demo.mybatis.entity.TrainSupplier;
import dcein.springboot.demo.service.TrainExcelExportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {

    @Autowired
    private TrainExcelExportService trainExcelExportService ;

    @Autowired
    private SupplierController supplierController;

	@Test
    public void textExcel() {

	    String[] title = {"商户号","商户名","订单金额","结算日期"} ;

        List list = new ArrayList();
        list.add("123");
        Map<String, Object> supplierList = supplierController.getSupplierList(1, 12);
        List<TrainSupplier>   data = (List<TrainSupplier>) supplierList.get("data");

        supplierController.exportExcel(title,data,"I://ling.xls");

    }

}
