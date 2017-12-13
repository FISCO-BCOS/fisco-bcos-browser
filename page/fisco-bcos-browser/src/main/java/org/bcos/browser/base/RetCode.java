package org.bcos.browser.base;

/**
 * REST服务端的错误类
 */
public class RetCode {
    private int code = 0;//0成功   1000~1999系统错误  2000~2999用户错误   3000~3999一般异常
    private String msg;//错误信息

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
