package cn.webank.bcos.browser.dto;

import java.io.Serializable;

/**
 * @Description:Node information table, entity class
 * @Author: v_wbsqwu
 * @Date: 2017/11/2 14:43
 */
public class TbNodesInfoDto implements Serializable{
    private static final long serialVersionUID = -7992773023563564787L;
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
