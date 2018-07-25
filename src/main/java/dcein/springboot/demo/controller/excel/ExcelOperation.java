package dcein.springboot.demo.controller.excel;

import dcein.springboot.demo.service.TrainExcelExportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Collection;

/**
 * @Auther: DingCong
 * @Description: Excel操作相关工具类
 * @@Date: Created in 11:36 2018/7/25
 */
@Slf4j
@Controller
@RequestMapping("excel")
public class ExcelOperation {


    @Autowired
    private TrainExcelExportService trainExcelExportService;

    /**
     * 导出Excel表格
     * @param headers 表格列名
     * @param dataSource 数据集合
     * @param savePlace 储存位置,例：E://supplier.xls
     */
    public void exportExcel(String[] headers , Collection dataSource , String savePlace ){
        log.info("Export excel attribute is savePlace : " + savePlace);
        try {
            //step1.创建读取文件输出流
            OutputStream out = new FileOutputStream(savePlace);

            //step2.调用导出文件方法
            trainExcelExportService.exportExcel(headers ,dataSource ,out);

            //step3.关闭流
            out.close();

            //step4.提示框
            //JOptionPane.showMessageDialog(null, "导出成功!");

        } catch (Exception e) {
            log.error("文档输出流异常");
            e.printStackTrace();
        }
    }

    public void importExcel(){

    }

}
