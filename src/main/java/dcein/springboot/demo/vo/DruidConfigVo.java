package dcein.springboot.demo.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: DingCong
 * @Description: 德鲁伊配置vo
 * @@Date: Created in 19:05 2018/8/8
 */
@Getter@Setter
public class DruidConfigVo {

    private String type;
    private String url;
    private String driverClassName;
    private String username;
    private String password;
    private Integer initialSize;
    private Integer minIdle;
    private Integer maxActive;
    private Integer maxWait;
    private Integer timeBetweenEvictionRunsMillis;
    private Integer minEvictableIdleTimeMillis;
    private String validationQuery;
    private Boolean testWhileIdle;
    private Boolean testOnBorrow;
    private Boolean testOnReturn;
    private Boolean poolPreparedStatements;
    private Integer maxPoolPreparedStatementPerConnectionSize;
    private String filters;
    private String connectionProperties;
    private String publicKey;


}
