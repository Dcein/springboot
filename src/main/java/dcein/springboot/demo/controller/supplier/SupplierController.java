package dcein.springboot.demo.controller.supplier;

import dcein.springboot.demo.service.TrainExcelExportService;
import dcein.springboot.demo.service.TrainSupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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





}
