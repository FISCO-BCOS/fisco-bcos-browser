package cn.webank.bcos.browser.dto;

/**
 * Node rpc link table, entity class
 * Created by wicker on 2017/10/12.
 */
public class TbNodeConnectionDto{
    private Integer pkId;
    private String ip;
    private Integer rpcPort;

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

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
