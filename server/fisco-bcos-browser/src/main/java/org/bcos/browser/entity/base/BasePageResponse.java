package org.bcos.browser.entity.base;

import java.util.Collections;
import lombok.Data;
import org.bcos.browser.base.RetCode;

@Data
public class BasePageResponse {
    private int code;
    private String message;
    private Object data = Collections.emptyList();
    private int totalCount;

    public BasePageResponse() {}

    public BasePageResponse(RetCode retcode) {
        this.code = retcode.getCode();
        this.message = retcode.getMsg();
    }
}
