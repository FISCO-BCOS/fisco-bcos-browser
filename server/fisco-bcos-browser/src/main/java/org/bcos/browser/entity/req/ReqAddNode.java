package org.bcos.browser.entity.req;

import java.util.List;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bcos.browser.base.ConstantCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqAddNode {
    @NotNull(message = ConstantCode.GROUP_ID_IS_EMPTY)
    private int groupId;
    private List<ReqAddNodeInfo> data;
}
