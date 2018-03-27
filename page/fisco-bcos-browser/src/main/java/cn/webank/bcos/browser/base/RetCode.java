package cn.webank.bcos.browser.base;

/**
 * REST server error class
 */
public class RetCode {
    private int code = 0;
    private String msg;//Error message

    public RetCode(){}

    public RetCode(int code, String msg){
        this.code=code;
        this.msg = msg;
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"code\":")
                .append(code);
        sb.append(",\"msg\":\"")
                .append(msg).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
