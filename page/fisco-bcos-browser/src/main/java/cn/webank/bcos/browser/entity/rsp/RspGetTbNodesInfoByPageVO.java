package cn.webank.bcos.browser.entity.rsp;

import java.io.Serializable;


public class RspGetTbNodesInfoByPageVO implements Serializable {
    private static final long serialVersionUID = 4569906256526130891L;
    private String pkId;
    private String addr;
    private Integer blockNumber;
    private String active;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Integer getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Integer blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"pkId\":\"")
                .append(pkId).append('\"');
        sb.append(",\"addr\":\"")
                .append(addr).append('\"');
        sb.append(",\"blockNumber\":")
                .append(blockNumber);
        sb.append(",\"active\":\"")
                .append(active).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
