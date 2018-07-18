package dcein.springboot.demo.response;
/***
  * @Description: 统一响应结果封装
  * @Author:      DingCong
  * @CreateDate:  2018/6/14 13:46
  */
public class ResponseData extends Response{

    /**
     * 响应对象
     */
    private Object data;

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public ResponseData(Object data) {
        this.data = data;
    }

    /**
     * 响应异常信息
     * @param msg
     */
    public ResponseData(ExceptionMsg msg) {
    	  super(msg);
    }

    /**
     * 响应结果信息和信息码
     * @param responseCode
     * @param responseMsg
     */
    public ResponseData(String responseCode, String responseMsg) {
        super(responseCode, responseMsg);
    }

    /**
     * 响应数据，结果码和结果信息
     * @param responseCode
     * @param responseMsg
     * @param data
     */
    public ResponseData(String responseCode, String responseMsg, Object data) {
        super(responseCode, responseMsg);
        this.data = data;
    }

    /**
     * 响应异常信息和数据
     * @param msg
     * @param data
     */
    public ResponseData(ExceptionMsg msg, Object data) {
        super(msg);
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "data=" + data +
                "} " + super.toString();
    }
}
