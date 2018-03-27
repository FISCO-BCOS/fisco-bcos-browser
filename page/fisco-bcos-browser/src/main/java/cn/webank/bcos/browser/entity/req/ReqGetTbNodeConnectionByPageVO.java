package cn.webank.bcos.browser.entity.req;

import cn.webank.bcos.browser.entity.base.BasePageReqEntity;


public class ReqGetTbNodeConnectionByPageVO extends BasePageReqEntity {
    private String ipVal;//ip
    private String rpcPortVal;

    public String getIpVal() {
        return ipVal;
    }

    public void setIpVal(String ipVal) {
        this.ipVal = ipVal;
    }

    public String getRpcPortVal() {
        return rpcPortVal;
    }

    public void setRpcPortVal(String rpcPortVal) {
        this.rpcPortVal = rpcPortVal;
    }
}
