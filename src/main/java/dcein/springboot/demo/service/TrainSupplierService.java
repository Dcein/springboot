package dcein.springboot.demo.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import dcein.springboot.demo.mybatis.dao.TrainSupplierMapper;
import dcein.springboot.demo.mybatis.entity.TrainSupplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: DingCong
 * @Description:
 * @@Date: Created in 17:26 2018/7/16
 */
@Slf4j
@Service
public class TrainSupplierService {

    @Autowired
    private TrainSupplierMapper trainSupplierMapper;

    /**
     * 分页查询商户列表
     * @param pageNum 起始页
     * @param pageSize 每页显示的信息数量
     * @return
     */
    public Map<String,Object> getSupplierList(Integer pageNum, int pageSize) {
        log.info("执行Mybatis分页查询,pageNum:" + pageNum + ",pageSize:" + pageSize);

        //step1.判断所传页码索引是否为null
        int pageIndex = pageNum == null ? 1 : pageNum;

        //step2.核心分页代码,根据页码索引和分组数据计算出分页对象,同时已经完成mybatis分页
        Page page = PageHelper.startPage(pageIndex, pageSize);

        //step3.执行分页查询,将分页数据查出来
        List<TrainSupplier> trainSuppliers = trainSupplierMapper.selectAll();

        //step4.返回结果容器,包含总页,当前页和数据,提供给前端
        Map ResultMap = new HashMap<String,Object>();
        ResultMap.put("totalPages" , page.getTotal());
        ResultMap.put("nowPage" , pageNum);
        ResultMap.put("data" , trainSuppliers);
        return ResultMap;
    }

}
