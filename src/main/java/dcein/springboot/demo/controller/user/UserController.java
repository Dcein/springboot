package dcein.springboot.demo.controller.user;

import dcein.springboot.demo.constants.ModelConstants;
import dcein.springboot.demo.controller.BaseController;
import dcein.springboot.demo.mybatis.entity.TrainUser;
import dcein.springboot.demo.response.ExceptionMsg;
import dcein.springboot.demo.response.ResponseData;
import dcein.springboot.demo.service.TrainUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;

/***
 * @Description: 用户入口
 * @Author: DingCong
 * @CreateDate: 2018/6/14 11:05
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private TrainUserService trainUserService ;

    /**
     * 登陆：这里用对象接收值,前端页面使用vue.js中的vue-resource.min.js,通过post方式来请求后台控制器
     * @param user
     * @param response
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseData login(TrainUser user, HttpServletResponse response) {
        log.info("用户登陆....");
        //step1.先判断参数是否为空
        if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.gePassWord())) {
            log.error(ModelConstants.PARAM_ERROR.getMsg());
            return new ResponseData(ExceptionMsg.USER_NOT_PERMIT_NULL);
        }

        //step2.判断用户名或密码是否正确
        boolean checkResult = trainUserService.userLoginCheck(user);
        if (!checkResult){
            log.error(ExceptionMsg.USERNAME_OR_PASSWORD_ERROR.getMsg());
            return new ResponseData(ExceptionMsg.USERNAME_OR_PASSWORD_ERROR);
        }

        //step3.获取跳转链接
        String location = "getHome";
        return new ResponseData(ExceptionMsg.SUCCESS,location);
    }



}
