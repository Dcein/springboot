package dcein.springboot.demo.controller.user;

import com.alibaba.fastjson.JSON;
import dcein.springboot.demo.constants.ModelConstants;
import dcein.springboot.demo.controller.BaseController;
import dcein.springboot.demo.mybatis.entity.TrainUser;
import dcein.springboot.demo.response.ExceptionMsg;
import dcein.springboot.demo.response.ResponseData;
import dcein.springboot.demo.service.TrainUserService;
import dcein.springboot.demo.utils.CommonUtils;
import dcein.springboot.demo.utils.RSA;
import dcein.springboot.demo.utils.Tools;
import dcein.springboot.demo.vo.Greeting;
import dcein.springboot.demo.vo.SipNotifyVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

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
    private RestTemplate restTemplate;

    @Autowired
    private TrainUserService trainUserService;


    /**
     * 登陆：这里用对象接收值,前端页面使用vue.js中的vue-resource.min.js,通过post方式来请求后台控制器
     * @param user
     * @param response
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseData login(TrainUser user, HttpServletResponse response, HttpServletRequest request) {
        log.info("用户登陆....");
        //step1.先判断参数是否为空
        if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.gePassWord())) {
            log.error(ModelConstants.PARAM_ERROR.getMsg());
            return new ResponseData(ExceptionMsg.USER_NOT_PERMIT_NULL);
        }

        //step2.判断用户名或密码是否正确
        boolean checkResult = trainUserService.userLoginCheck(user);
        if (!checkResult) {
            log.error(ExceptionMsg.USERNAME_OR_PASSWORD_ERROR.getMsg());
            return new ResponseData(ExceptionMsg.USERNAME_OR_PASSWORD_ERROR);
        }

        //step3.判断session中是否存在用户唯一令牌,存在延长时长
        if (StringUtils.isNotBlank((String) request.getSession().getAttribute("token"))) {
            log.info("session存在token" + (String) request.getSession().getAttribute("token"));
            request.getSession().setMaxInactiveInterval(30);
            String location = "getHome";
            return new ResponseData(ExceptionMsg.SUCCESS, location);
        }

        //step4.将用户信息放入session,session会话时长为30秒
        log.info("session不存在token");
        String s = Tools.generateToken();
        HttpSession session = request.getSession();
        session.setAttribute("token", s);
        session.setMaxInactiveInterval(30);

        //step3.获取跳转链接
        String location = "getHome";
        return new ResponseData(ExceptionMsg.SUCCESS, location);
    }


    @PostMapping("/greeting")
    public String greetingSubmit(@ModelAttribute Greeting greeting) {
        log.info("打印前段请求:"+greeting);
        String org = "20180525O15682349";

        //step1.封装充值请求参数
        greeting.setTimestamp(CommonUtils.getFormatDate(new Date(), "yyyyMMddHHmmssSSS") + CommonUtils.getCharAndNum(8));
        greeting.setMobileNo("15737317830");
        //step2.准备key
        String spiPub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDT9mLiCHKr+WdCfs3JFzdBEWSHICqXKjElkFVsk0HUZGUbrU/MjIX9Ppyv3Ej4/G1TwBT8a6dINJZYw8CujYCH3o59t40FlObnjuYr9+uw+tdadV+0JryjEXK+4YzvyboZogaLW+a2pxN3qz9QwDtocXDs4g9zCkU2PuJ4Xj5AJQIDAQAB";
        String orgPri = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJA7TbmtmAeBBtd7uPgpCbU8UX1Xru1YM1cgFYIIPblYQetNK63OvDb7tlNhQXNLaDuhNAASFl+bONvnbB70Kqo0eSnQFtN6XzgYUzuysTleGn0oL61H6Owwk6y/AJSwzwa2sPXa4YGg3s5Bp1mYR4FisAOjA27uByUkgZJimXcJAgMBAAECgYAQNxqd7W5SKadjQn2hKLv2rDldJiZ8eJYNDYZdgB+wXsPKeHqYp81xQQtWhHCfTbMCQTVfbKknRoQ19Oaq9Dh3jvxOjmpT7wlaFSTq4utP7nfEcPiiOzzuxRhLviX7ljPVJ/mxQ3EezZAjhYW5JDfd4p/PYaArzp9JsQiW8/YsAQJBAMniQ7GBSYo9Pcf0Oezw7Vd53YQ4oEHv5NhjH+fVGCSZcGaApdLm8Hh4NE9IHyNQa1ylb5MPUyfZzJaEfFeszFECQQC25NAQ7vW9FSceGGeC02CIcdNQrdYhhQzVTbTrrcnz6HnUQlY+7olrtB05fHXfY2q25zU6fy1T1Bw59z/bNyk5AkAvCEuE+v3K+uF9x+QG00o36ug0eYy8T8scrlssg3SQcj7BYo8/RxYWuspU/hRjAcVBXDlWJWfWDOOg3VSqiFNBAkBZ42dniyp3vfe10OS+5ciiBt+G3YlKfCvsgf6TfpytJesKqXfeAjrEFofugv8jxabjMgt1LqBGfQ9KZfyabyfxAkEArM99WIsAQO7XSXn+S0zROEKJ9DhtHbaYdwHLedQBXRBmPfIhYHDeGR5WLZVyJN102e77wZIfvjjdhC3DM5pkOw==";

        String decrypts = null;
        //step3.执行加密
        try{

            String url = "http://10.193.17.86:46959/recharge/recharge/getRecharge";
            RSAPublicKey sipPublicKey = RSA.getPemRSAPublicKey(spiPub);
            String data = RSA.encrypt(sipPublicKey, JSON.toJSONString(greeting));
            System.out.println(data);

            RSAPrivateKey hjfPrivateKey = RSA.getPemRSAPrivateKey(orgPri);
            String sign = RSA.sign(JSON.toJSONString(greeting), hjfPrivateKey);
            System.out.println(sign);

            String param = "org="+org+"&data="+data+"&sign="+sign;
            log.info("封装请求数据:"+param);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<String> entity = new HttpEntity<String>(param, headers);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);
            String jsonStr = responseEntity.getBody();
            log.info("响应消息："+jsonStr);


            String[] regx = jsonStr.split("&sign=");
            String SipCiphertext = regx[0].replace("data=", "");
            String decode = URLDecoder.decode(SipCiphertext, "utf-8");
            log.info("data数据:" + SipCiphertext);
             decrypts = RSA.decrypt(hjfPrivateKey, decode.replace(RSA.SPACE, "+"));
            log.info("响应信息："+decrypts);

        }catch(Exception e){
            log.error("请求出现异常:" , e);
        }
        return decrypts;
    }


    @RequestMapping("/notify")
    public String testA(@RequestBody SipNotifyVo sipNotifyVo) throws Exception {
        log.info("平台发来通知...");
        String orgPri = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJA7TbmtmAeBBtd7uPgpCbU8UX1Xru1YM1cgFYIIPblYQetNK63OvDb7tlNhQXNLaDuhNAASFl+bONvnbB70Kqo0eSnQFtN6XzgYUzuysTleGn0oL61H6Owwk6y/AJSwzwa2sPXa4YGg3s5Bp1mYR4FisAOjA27uByUkgZJimXcJAgMBAAECgYAQNxqd7W5SKadjQn2hKLv2rDldJiZ8eJYNDYZdgB+wXsPKeHqYp81xQQtWhHCfTbMCQTVfbKknRoQ19Oaq9Dh3jvxOjmpT7wlaFSTq4utP7nfEcPiiOzzuxRhLviX7ljPVJ/mxQ3EezZAjhYW5JDfd4p/PYaArzp9JsQiW8/YsAQJBAMniQ7GBSYo9Pcf0Oezw7Vd53YQ4oEHv5NhjH+fVGCSZcGaApdLm8Hh4NE9IHyNQa1ylb5MPUyfZzJaEfFeszFECQQC25NAQ7vW9FSceGGeC02CIcdNQrdYhhQzVTbTrrcnz6HnUQlY+7olrtB05fHXfY2q25zU6fy1T1Bw59z/bNyk5AkAvCEuE+v3K+uF9x+QG00o36ug0eYy8T8scrlssg3SQcj7BYo8/RxYWuspU/hRjAcVBXDlWJWfWDOOg3VSqiFNBAkBZ42dniyp3vfe10OS+5ciiBt+G3YlKfCvsgf6TfpytJesKqXfeAjrEFofugv8jxabjMgt1LqBGfQ9KZfyabyfxAkEArM99WIsAQO7XSXn+S0zROEKJ9DhtHbaYdwHLedQBXRBmPfIhYHDeGR5WLZVyJN102e77wZIfvjjdhC3DM5pkOw==";
        RSAPrivateKey hjfPrivateKey = RSA.getPemRSAPrivateKey(orgPri);
        String decode = URLDecoder.decode(sipNotifyVo.getMessage(), "utf-8");
        String decrypt = RSA.decrypt(hjfPrivateKey, decode.replace(RSA.SPACE, "+"));
        log.info("接收来的消息:" + decrypt );
        return "SUCCESS" ;
    }
}
