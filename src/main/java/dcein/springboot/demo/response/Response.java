package dcein.springboot.demo.response;

public class Response {

    private String responseCode = "000000";

    private String responseMsg = "成功";

    public Response() {
    }

    public Response(ExceptionMsg msg) {
        this.responseCode = msg.getCode();
        this.responseMsg = msg.getMsg();
    }

    public Response(String responseCode) {
        this.responseCode = responseCode;
        this.responseMsg = "";
    }

    public Response(String responseCode, String responseMsg) {
        this.responseCode = responseCode;
        this.responseMsg = responseMsg;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    @Override
    public String toString() {
        return "Response{" +
                "responseCode='" + responseCode + '\'' +
                ", responseMsg='" + responseMsg + '\'' +
                '}';
    }
}
