package org.bcos.browser.dto;

import java.io.Serializable;

/**
 * @Description:节点的信息表,实体类
 * @Author: v_wbsqwu
 * @Date: 2017/11/2 14:43
 */
public class TbNodesInfoDto implements Serializable{
    private static final long serialVersionUID = -7992773023563564787L;
    private String pkId;//借点id
    private String addr;//节点ip和p2p_port
    private Integer blockNumber;//节点快高

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"pkId\":\"")
                .append(pkId).append('\"');
        sb.append(",\"addr\":\"")
                .append(addr).append('\"');
        sb.append(",\"blockNumber\":")
                .append(blockNumber);
        sb.append('}');
        return sb.toString();
    }
}
