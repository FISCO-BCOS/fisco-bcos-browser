package cn.webank.bcos.browser.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * Blockchain global information table, entity class
 * Created by wicker on 2017/10/12.
 */
public class TbBlockChainInfoDto implements Serializable {
    private static final long serialVersionUID = 2146862709881742381L;
    private Integer pkId;//Only specify a primary key, no practical significance
    private Integer lastBlock;//The latest block of this query is high
    private BigInteger transactionNumber;//Number of transactions
    private BigInteger pendingTxn;//Number of transactions currently being processed but not yet chained
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
