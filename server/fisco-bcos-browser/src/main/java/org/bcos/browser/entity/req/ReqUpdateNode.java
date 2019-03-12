package org.bcos.browser.entity.req;

import javax.validation.constraints.NotNull;
import lombok.Data;
import org.bcos.browser.base.ConstantCode;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class ReqUpdateNode {
    @NotNull(message = ConstantCode.GROUP_ID_IS_EMPTY)
    private int groupId;
    @NotNull(message = ConstantCode.NODE_ID_IS_EMPTY)
    private String nodeId;
    @NotBlank(message = ConstantCode.NODE_IP_IS_EMPTY)
    private String ip;
    @NotBlank(message = ConstantCode.NODE_RPCPORT_IS_EMPTY)
    private String rpcPort;
    @NotBlank(message = ConstantCode.NODE_P2PPORT_IS_EMPTY)
    private String p2pPort;
}
