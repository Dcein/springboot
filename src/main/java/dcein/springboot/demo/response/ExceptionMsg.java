
package dcein.springboot.demo.response;

public enum ExceptionMsg {

    /**
     * 000000 - 成功
     */
    SUCCESS("000000", "成功"),

    /**
     * 200001 - 用户名或密码错误
     */
    USERNAME_OR_PASSWORD_ERROR("200001", "用户名或密码错误"),

    /**
     * 200002 - 用户名或密码为空
     */
    USER_NOT_PERMIT_NULL("200002", "用户名或密码为空");

    ExceptionMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}

