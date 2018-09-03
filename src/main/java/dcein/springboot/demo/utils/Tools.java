package dcein.springboot.demo.utils;

import dcein.springboot.demo.constants.SystemConstants;
import sun.misc.BASE64Encoder;
import java.util.Random;

/**
 * @Auther: DingCong
 * @Description: 工具类
 * @@Date: Created in 9:38 2018/7/19
 */
public class Tools {

    /**
     * 随机生成一个token，用来唯一标识用户
     * 生成方法:
     * 1.取当前时间的毫秒数
     * 2.取0~999999999之间随机数
     * 3.毫秒数和随机数相加
     * 4.MD5加密该数之和
     * 5.进行base64编码
     * @return
     */
    public static String  generateToken(){
        String token = System.currentTimeMillis() + new Random().nextInt(999999999) + "";
        try {
            String s = MD5Util.encryption(token, SystemConstants.MD5);
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(s.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
