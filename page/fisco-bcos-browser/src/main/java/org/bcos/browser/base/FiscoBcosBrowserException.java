package org.bcos.browser.base;

/**
 * 错误类
 */
public class FiscoBcosBrowserException extends RuntimeException{
    private RetCode rsc;//错误码类

    public FiscoBcosBrowserException(RetCode rsc){
        super(rsc.getMsg());
        this.rsc = rsc;
    }

    public RetCode getRsc() {
        return rsc;
    }
}
