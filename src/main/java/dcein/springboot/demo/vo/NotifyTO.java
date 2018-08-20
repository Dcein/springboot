package dcein.springboot.demo.vo;

import java.util.List;

/**
 * @Auther: DingCong
 * @Description:
 * @@Date: Created in 15:26 2018/8/20
 */
public class NotifyTO {


    /**邮件通知人集合*/
    private List<String> emailList;
    /**短信通知人集合*/
    private List<String> phoneList;
    /**微信通知人集合*/
    private List<String> wechatList;

    public NotifyTO() {
        super();
    }


    public List<String> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<String> emailList) {
        this.emailList = emailList;
    }

    public List<String> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<String> phoneList) {
        this.phoneList = phoneList;
    }

    public List<String> getWechatList() {
        return wechatList;
    }

    public void setWechatList(List<String> wechatList) {
        this.wechatList = wechatList;
    }


    @Override
    public String toString() {
        return "NotifyTO [emailList=" + emailList + ", phoneList=" + phoneList
                + ", wechatList=" + wechatList + "]";
    }
}
