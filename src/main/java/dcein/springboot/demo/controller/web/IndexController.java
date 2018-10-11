package dcein.springboot.demo.controller.web;

import dcein.springboot.demo.controller.BaseController;
import dcein.springboot.demo.interceptor.LoginRequired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletResponse;

/***
  * @Description: 页面入口
  * @Author:      DingCong
  * @CreateDate:  2018/6/14 11:06
  */
@Slf4j
@Controller
public class IndexController extends BaseController {


    @RequestMapping("/index")
    public String jumpIndex(){
      log.info("进入主页...");
        return "index";
    }

    @RequestMapping(value="/login",method= {RequestMethod.GET,RequestMethod.POST})
    public String login(HttpServletResponse response) {
        log.info("进入登陆页面...");
        return "login";
    }

    @RequestMapping(value="/register",method=RequestMethod.GET)
    public String register() {
        log.info("进入注册页面...");
        return "register";
    }

    @RequestMapping(value = "/getHome")
    public String getHome(){
        log.info("进入主页...");
        return "home";
    }

    @RequestMapping(value = "/getUserCenter")
    public String getUserCenter(){
        log.info("进入照片墙...");
        return "user";
    }

    @RequestMapping(value = "/getPlaying")
    public String getPlaying(){
        log.info("进入娱乐世界...");
        return "playing";
    }

    @RequestMapping(value = "/getTraveling")
    public String getTraveling(){
        log.info("进入旅行世界...");
        return "traveling";
    }

    @RequestMapping(value = "/getReading")
    public String getReading(){
        log.info("进入阅读世界...");
        return "reading";
    }

//    @LoginRequired
    @RequestMapping(value = "/getRecoding")
    public String getRecoding(){
        log.info("进入记录世界...");
        return "recoding";
    }

    @RequestMapping(value = "/audio")
    public String audio(){
        log.info("进入audio...");
        return "audio";
    }































    /*    *//**
     * 获取机构列表
     * @param model
     * @return
     *//*
    @RequestMapping("/getOrg")
    public String getOrg(Model model){
        List<Org> allOrg = ctmon.getAllOrg();
        for (int i= 0;i<allOrg.size();i++){
            log.info("第"+i+"个机构:"+allOrg.get(i).getOrgName());
        }
        model.addAttribute("list",allOrg);
        return "ok";
    }

    *//**
     * ajax获取银行列表，返回为responseBody
     * @return
     *//*
    @RequestMapping("/getBank")
    @ResponseBody
    public List<Bank>  getBank(){
        log.info("开始查询银行列表...");
        List<Bank> banks = bankDao.selectBank();
        return banks;
    }

    *//**
     * 分页显示订单列表(引入pageHelper分页插件)
     *//*
    @RequestMapping("/getAllOrder")
    @ResponseBody
    public Map<String,Object> getOrderList(String currentPage, Order order , HttpServletRequest request){
        log.info("开始获取订单列表...currentPage:"+currentPage);
        //step1.设置当前页
        int curr = 1;
        //step2.如果传入页码不为空,则是指传入页数为当前页
        if(currentPage!=null && !"".equals(currentPage)){
            curr = Integer.parseInt(currentPage);
        }
        //step3.启用分页插件,设置当前页和每页显示的条数
        Page page = PageHelper.startPage(curr,10);
        log.info("getPages:"+page.getPages()+",getPageSize:"+page.getPageSize());
        //step4.新建容器
        Map map = new HashMap();
        //step5.容器中放入订单列表
        List<Order> orderList = ctmon.getOrder();
        map.put("orderList",orderList);
        //step6.容器中放入分页信息
        map.put("page",page.getPages());

        return map;
    }*/

}
