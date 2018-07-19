package dcein.springboot.demo.mybatis.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "train_user")
public class TrainUser implements Serializable {
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "NICK_NAME")
    private String nickName;

    @Column(name = "USER_ACCOUNT")
    private String userAccount;

    @Column(name = "USER_PASSWORD")
    private String passWord;

    @Column(name = "IS_LOGIN")
    private String isLogin;

    @Column(name = "LOGIN_DATA")
    private Date loginData;

    private static final long serialVersionUID = 1L;

    /**
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return USER_NAME
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return NICK_NAME
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * @param nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * @return USER_ACCOUNT
     */
    public String getUserAccount() {
        return userAccount;
    }

    /**
     * @param userAccount
     */
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * @return USER_PASSWORD
     */
    public String gePassWord() {
        return passWord;
    }

    /**
     * @param passWord
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    /**
     * @return IS_LOGIN
     */
    public String getIsLogin() {
        return isLogin;
    }

    /**
     * @param isLogin
     */
    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

    /**
     * @return LOGIN_DATA
     */
    public Date getLoginData() {
        return loginData;
    }

    /**
     * @param loginData
     */
    public void setLoginData(Date loginData) {
        this.loginData = loginData;
    }
}