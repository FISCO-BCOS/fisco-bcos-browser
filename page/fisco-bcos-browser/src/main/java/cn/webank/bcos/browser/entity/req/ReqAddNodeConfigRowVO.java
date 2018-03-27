package cn.webank.bcos.browser.entity.req;

import cn.webank.bcos.browser.base.ConstantCode;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public class ReqAddNodeConfigRowVO {
    @NotBlank(message = ConstantCode.SAVE_FAIL_IP_PARAM_EMPTY)
    private String ip;
    @NotNull(message = ConstantCode.SAVE_FAIL_RPCPORT_PARAM_EMPTY)
    @Min(value=1,message = ConstantCode.SAVE_FAIL_RPCPORT_PARAM_FORMAT_ERROR)
    private Integer rpcPort;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getRpcPort() {
        return rpcPort;
    }

    public void setRpcPort(Integer rpcPort) {
        this.rpcPort = rpcPort;
    }
}
