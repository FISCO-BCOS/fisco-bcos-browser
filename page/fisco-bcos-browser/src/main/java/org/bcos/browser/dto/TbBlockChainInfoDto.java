package org.bcos.browser.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * 区块链全局信息表，实体类
 * Created by wicker on 2017/10/12.
 */
public class TbBlockChainInfoDto implements Serializable {
    private static final long serialVersionUID = 2146862709881742381L;
    private Integer pkId;//仅仅指定一个主键，无实际意义
    private Integer lastBlock;//本次query的最新块高
    private BigInteger transactionNumber;//交易数
    private BigInteger pendingTxn;//当前正在处理但还未上链的交易的个数
    private Integer pbftView;//
    private BigDecimal avgTime;//

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public Integer getLastBlock() {
        return lastBlock;
    }

    public void setLastBlock(Integer lastBlock) {
        this.lastBlock = lastBlock;
    }

    public BigInteger getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(BigInteger transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public BigInteger getPendingTxn() {
        return pendingTxn;
    }

    public void setPendingTxn(BigInteger pendingTxn) {
        this.pendingTxn = pendingTxn;
    }

    public Integer getPbftView() {
        return pbftView;
    }

    public void setPbftView(Integer pbftView) {
        this.pbftView = pbftView;
    }

    public BigDecimal getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(BigDecimal avgTime) {
        this.avgTime = avgTime;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"pkId\":")
                .append(pkId);
        sb.append(",\"lastBlock\":")
                .append(lastBlock);
        sb.append(",\"transactionNumber\":")
                .append(transactionNumber);
        sb.append(",\"pendingTxn\":")
                .append(pendingTxn);
        sb.append(",\"pbftView\":")
                .append(pbftView);
        sb.append(",\"avgTime\":")
                .append(avgTime);
        sb.append('}');
        return sb.toString();
    }
}
