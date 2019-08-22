package org.bcos.browser.base.exception;

import org.bcos.browser.base.RetCode;

public class BaseException extends Exception {

    private static final long serialVersionUID = 1L;
    private RetCode retCode;

    public BaseException(RetCode retCode) {
        super(retCode.getMsg());
        this.retCode = retCode;
    }

    public BaseException(int code, String msg) {
        super(msg);
        this.retCode = new RetCode(code, msg);
    }

    public RetCode getRetCode() {
        return retCode;
    }
}
