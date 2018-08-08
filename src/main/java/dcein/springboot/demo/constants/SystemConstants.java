package dcein.springboot.demo.constants;

/**
 * @Auther: DingCong
 * @Description: 系统级别常量类
 * @@Date: Created in 13:25 2018/5/16
 */
public class SystemConstants {

    /**
     * 登陆用户名
     */
    public static final String USERNAME = "root";

    /**
     * 登陆密码
     */
    public static final String PASSWORD = "root";

    /**
     * 默认错误跳转视图
     */
    public static final String DEFAULT_ERROR_VIEW = "error";

    /**
     * 日期类型
     */
    public static final String DATE_PATTERN_5 = "yyyyMMddHHmmssSSS";

    /**
     * 德鲁伊公钥
     */
    public static final String DRUID_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKYx7h17iuSy0Po0OglVBM86N2Kp/TXYb3PfGkYKv5LUFSQuH2n0ClcsOkOGf+B73KE4rrg2nKOGsj4Y5vgDS0UCAwEAAQ==";

    /**
     * 德鲁伊URL映射
     */
    public static final String DRUID_URL_MAPPING = "/druid/*";

    /**
     * 德鲁伊登陆名
     */
    public static final String DRUID_LOGIN_USERNAME = "loginUsername";

    /**
     * 德鲁伊登陆密码
     */
    public static final String DRUID_LOGIN_PASSWORD = "loginPassword";

    /**
     * 德鲁伊排出匹配类型
     */
    public static final String DRUID_EXCLUSION_PATTERN = "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*";

}
