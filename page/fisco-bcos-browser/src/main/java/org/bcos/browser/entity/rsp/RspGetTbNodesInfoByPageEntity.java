package org.bcos.browser.entity.rsp;

import java.io.Serializable;

/**
 * @Description:分页查询节点的信息表的数据,返回参
 * @Author: v_wbsqwu
 * @Date: 2017/11/2 15:37
 */
public class RspGetTbNodesInfoByPageEntity implements Serializable {
    private static final long serialVersionUID = 4569906256526130891L;
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
