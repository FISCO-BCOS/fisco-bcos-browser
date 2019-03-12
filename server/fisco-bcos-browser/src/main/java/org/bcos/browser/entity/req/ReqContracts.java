package org.bcos.browser.entity.req;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.entity.dto.Contract;

@Data
public class ReqContracts {
    @NotNull(message = ConstantCode.GROUP_ID_IS_EMPTY)
    private int groupId;
    private List<Contract> data;
}
