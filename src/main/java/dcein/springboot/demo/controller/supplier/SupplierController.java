package dcein.springboot.demo.controller.supplier;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import dcein.springboot.demo.mybatis.dao.TrainSupplierMapper;
import dcein.springboot.demo.mybatis.entity.TrainSupplier;
import dcein.springboot.demo.service.TrainExcelExportService;
import dcein.springboot.demo.service.TrainSupplierService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: DingCong
 * @Description: 展示商户列表
 * @@Date: Created in 15:53 2018/7/16
 */
@Slf4j
@Controller
@RequestMapping("/supplierController")
public class SupplierController {

    @Autowired
    private TrainSupplierService trainSupplierService ;

    @Autowired
    private TrainExcelExportService trainExcelExportService;

    /**
     * 分页输出数据,整合pageHelper
     * @param startPage 当前页
     * @param pageSize 本页显示的数据条数
     * @return
     */
    @ResponseBody
    @RequestMapping("getSupplierList")
    public Map<String,Object> getSupplierList(int startPage ,int pageSize){
        log.info("分页查找数据,startPage:" + startPage + ",pageSize:" + pageSize );
        return trainSupplierService.getSupplierList(startPage,pageSize) ;
    }


    /**
     * 导出Excel表格
     * @param headers 表格列名
     * @param dataSource 数据集合
     * @param savePlace 储存位置,例：E://supplier.xls
     */
    public void exportExcel(String[] headers , Collection<T> dataSource , String savePlace ){
        log.info("Export excel attribute is savePlace : " + savePlace);
        try {
            //step1.创建读取文件输出流
            OutputStream out = new FileOutputStream(savePlace);

            //step2.调用导出文件方法
            trainExcelExportService.exportExcel(headers ,dataSource ,out);

            //step3.关闭流
            out.close();

            //step4.提示框
            JOptionPane.showMessageDialog(null, "导出成功!");

        } catch (Exception e) {
            log.error("文档输出流异常");
            e.printStackTrace();
        }
    }


}
