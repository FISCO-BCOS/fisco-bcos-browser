package org.bcos.browser.entity.req;

import javax.validation.constraints.NotNull;
import lombok.Data;
import org.bcos.browser.base.ConstantCode;

@Data
public class ReqGetTxnByDay {
    @NotNull(message = ConstantCode.GROUP_ID_IS_EMPTY)
    private int groupId;
    private String dateTimeBegin;
    private String dateTimeEnd;
}
