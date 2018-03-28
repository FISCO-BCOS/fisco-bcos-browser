package cn.webank.bcos.browser.base;

/**
 * Error class
 */
public class BcosBrowserException extends RuntimeException{
    private RetCode rsc;

    public BcosBrowserException(RetCode rsc){
        super(rsc.getMsg());
        this.rsc = rsc;
    }

    public RetCode getRsc() {
        return rsc;
    }
}
