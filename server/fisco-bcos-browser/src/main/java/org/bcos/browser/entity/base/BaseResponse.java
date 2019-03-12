package org.bcos.browser.entity.base;

import lombok.Data;
import org.bcos.browser.base.RetCode;

@Data
public class BaseResponse {
    private int code;
    private String message;
    private Object data;

    public BaseResponse() {}

    public BaseResponse(int code) {
        this.code = code;
    }

    public BaseResponse(RetCode rsc) {
        this.code = rsc.getCode();
        this.message = rsc.getMsg();
    }

    /**
     * constructor.
     * 
     * @param rsc RetCode
     * @param obj data
     */
    public BaseResponse(RetCode rsc, Object obj) {
        this.code = rsc.getCode();
        this.message = rsc.getMsg();
        this.data = obj;
    }

    /**
     * constructor.
     * 
     * @param code code
     * @param message message
     * @param obj data
     */
    public BaseResponse(int code, String message, Object obj) {
        this.code = code;
        this.message = message;
        this.data = obj;
    }
}
