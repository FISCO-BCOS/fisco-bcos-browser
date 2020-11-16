package org.bcos.browser.entity.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bcos.browser.base.ConstantCode;
import org.hibernate.validator.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqAddNodeInfo {
    @NotBlank(message = ConstantCode.NODE_IP_IS_EMPTY)
    private String ip;
    @NotBlank(message = ConstantCode.NODE_RPCPORT_IS_EMPTY)
    private String rpcPort;
    @NotBlank(message = ConstantCode.NODE_P2PPORT_IS_EMPTY)
    private String p2pPort;

}
