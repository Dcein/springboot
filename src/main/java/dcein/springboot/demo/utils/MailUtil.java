package dcein.springboot.demo.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @Auther: DingCong
 * @Description:
 * @@Date: Created in 15:22 2018/8/20
 */
public class MailUtil {

    static int port = 25;

    /**pop3：pop.qq.com
     smtp：smtp.qq.com**/
    /*smtp.sina.com*/
    static String server = "smtp.sina.com";//邮件服务器mail.cpip.net.cn

    static String from = "Dcein-Mail";//发送者,显示的发件人名字

    static String user = "15737317830@sina.cn";//发送者邮箱地址

    static String password = "dcein.199435";//密码


    public static void sendEmail(String email, String subject, String body) throws UnsupportedEncodingException {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", server);
            props.put("mail.smtp.port", String.valueOf(port));
            props.put("mail.smtp.auth", "true");
            Transport transport = null;
            Session session = Session.getDefaultInstance(props, null);
            transport = session.getTransport("smtp");
            transport.connect(server, user, password);
            MimeMessage msg = new MimeMessage(session);
            msg.setSentDate(new Date());
            InternetAddress fromAddress = new InternetAddress(user,from,"UTF-8");
            msg.setFrom(fromAddress);
            InternetAddress[] toAddress = new InternetAddress[1];
            toAddress[0] = new InternetAddress(email);
            msg.setRecipients(Message.RecipientType.TO, toAddress);
            msg.setSubject(subject, "UTF-8");
            msg.setText(body, "UTF-8");
            msg.saveChanges();
            transport.sendMessage(msg, msg.getAllRecipients());
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws UnsupportedEncodingException{

            sendEmail("lhb9305@163.com","聪哥发来的通知","Good Study ! Good Study ! YEAH !");//收件人
            System.out.println("The mail send sucess !");
    }
}

class Demo1{
    static int i ;
    private int j = 3;//5 //12
    {
        System.out.println(i+++"A");//6 //13
    }
    static {
        System.out.println(++i+"B"); //1
    }
    Demo1(){

        System.out.println(j+++"C");//14
    }
    Demo1(int k){

        System.out.println(i+k+++"D");//7
    }

}
class Demo2 extends Demo1{
    static int i ;
    {
        System.out.println(i--+"A1");//8
    }
    static {
        System.out.println(i+++"B1");//2
    }
    Demo2(){
        super(++i);//4
        System.out.println(--i+"C1");//9
    }
}

class DemoMain{
    public static void main(String[] args) {
        Demo2 d2 = new Demo2(); //3 //10
        Demo1 di = new Demo1();//11 //15
    }
}

