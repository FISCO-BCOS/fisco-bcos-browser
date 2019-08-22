package org.bcos.browser.base;

import lombok.Data;

@Data
public class RetCode {
    private Integer code;
    private String msg;

    public RetCode() {}

    public RetCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static RetCode mark(int code, String msg) {
        return new RetCode(code, msg);
    }

    public static RetCode mark(Integer code) {
        return new RetCode(code, null);
    }

    @Override
    public String toString() {
        return "RetCode [code=" + code + ", msg=" + msg + "]";
    }
}
