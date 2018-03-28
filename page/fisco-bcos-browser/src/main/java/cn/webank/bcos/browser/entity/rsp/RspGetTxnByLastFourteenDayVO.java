package cn.webank.bcos.browser.entity.rsp;

import java.io.Serializable;
import java.math.BigInteger;


public class RspGetTxnByLastFourteenDayVO implements Serializable{
    private static final long serialVersionUID = 163898116794072699L;
    private String pkDateStr;
    private BigInteger transactionNumber;

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
