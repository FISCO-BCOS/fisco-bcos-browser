package org.bcos.browser.entity.rsp;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @Description:获取最近14天的交易数据，返回参实体类
 * @Author: v_wbsqwu
 * @Date: 2017/10/15 16:29
 */
public class RspGetTxnByLastFourteenDayEntity implements Serializable{
    private static final long serialVersionUID = 163898116794072699L;
    private String pkDateStr;//日期
    private BigInteger transactionNumber;//交易数

    public String getPkDateStr() {
        return pkDateStr;
    }

    public void setPkDateStr(String pkDateStr) {
        this.pkDateStr = pkDateStr;
    }

    public BigInteger getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(BigInteger transactionNumber) {
        this.transactionNumber = transactionNumber;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"pkDateStr\":\"")
                .append(pkDateStr).append('\"');
        sb.append(",\"transactionNumber\":")
                .append(transactionNumber);
        sb.append('}');
        return sb.toString();
    }
}
