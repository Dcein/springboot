package dcein.springboot.demo.constants;

/**
 * @Auther: DingCong
 * @Description: 常量
 * @@Date: Created in 13:30 2018/6/14
 */
public enum ModelConstants {

    /**
     * 000000 - 成功
     */
    SUCCESS("000000","成功"),

    /**
     * 200001 - 参数错误
     */
    PARAM_ERROR("200001","参数错误")


    ;

    private String code;

    private String msg;

    ModelConstants(String code,String msg){

        this.msg = msg;

        this.code = code;

    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
