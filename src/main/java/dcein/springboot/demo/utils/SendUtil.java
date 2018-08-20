package dcein.springboot.demo.utils;

import dcein.springboot.demo.vo.NotifyTO;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @Auther: DingCong
 * @Description:
 * @@Date: Created in 15:21 2018/8/20
 */
public class SendUtil {

    /**
     * 发邮件
     * @param key  当前task任务方法名
     * @param subject  邮件主题
     * @param body 邮件内容
     */
    public static void sendEmail(String key,String subject,String body){

        //根据当前任务配置key返回  任务通知对象
        NotifyTO notifyTO = NotifyXmlUtil.getNotifyMap(key);

        if(notifyTO != null){
            //邮件收件人集合
            List<String> emailList = notifyTO.getEmailList();

            //循环发邮件
            for(String email : emailList){
                try {
                    MailUtil.sendEmail(email, subject, body);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
