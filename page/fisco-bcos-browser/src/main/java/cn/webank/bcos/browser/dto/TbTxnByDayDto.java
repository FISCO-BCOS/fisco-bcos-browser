package cn.webank.bcos.browser.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class TbTxnByDayDto implements Serializable,Comparable<TbTxnByDayDto> {
    private static final long serialVersionUID = -6563545177083880543L;
    private Date pkDate;
    private BigInteger transactionNumber;


    public Date getPkDate() {
        return pkDate;
    }

    public void setPkDate(Date pkDate) {
        this.pkDate = pkDate;
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
        sb.append("\"pkDate\":\"")
                .append(pkDate).append('\"');
        sb.append(",\"transactionNumber\":")
                .append(transactionNumber);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(TbTxnByDayDto other) {
        if(pkDate.after(other.getPkDate())){
            return 1;
        }
        if(pkDate.before(other.getPkDate())){
            return -1;
        }
        return 0;
    }
}
