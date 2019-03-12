package org.bcos.browser.entity.req;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.bcos.browser.base.ConstantCode;

@Data
public class ReqAddNode {
    @NotNull(message = ConstantCode.GROUP_ID_IS_EMPTY)
    private int groupId;
    private List<ReqAddNodeInfo> data;
}
