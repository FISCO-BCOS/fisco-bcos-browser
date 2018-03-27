package cn.webank.bcos.browser.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Trading tables that are processing but not yet chained, entity classes
 * Created by wicker on 2017/10/12.
 */
public class TbPendingTransactionDto implements Serializable {
    private static final long serialVersionUID = -8639243687168912416L;
    private String pkHash;//	TxHash
    private String blockHash;//		VARCHAR	32
    private Integer blockNumber;
    private BigInteger transactionIndex;
    private String transactionFrom;
    private String transactionTo;
    private BigInteger gas;//Used By Txn
    private BigDecimal gasPrice;
    private BigInteger cumulativeGas;
    private String randomId;
    private String inputText;//Input Data
    private String queueType;

    public String getPkHash() {
        return pkHash;
    }

    public void setPkHash(String pkHash) {
        this.pkHash = pkHash;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public Integer getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Integer blockNumber) {
        this.blockNumber = blockNumber;
    }

    public BigInteger getTransactionIndex() {
        return transactionIndex;
    }

    public void setTransactionIndex(BigInteger transactionIndex) {
        this.transactionIndex = transactionIndex;
    }

    public String getTransactionFrom() {
        return transactionFrom;
    }

    public void setTransactionFrom(String transactionFrom) {
        this.transactionFrom = transactionFrom;
    }

    public String getTransactionTo() {
        return transactionTo;
    }

    public void setTransactionTo(String transactionTo) {
        this.transactionTo = transactionTo;
    }

    public BigInteger getGas() {
        return gas;
    }

    public void setGas(BigInteger gas) {
        this.gas = gas;
    }

    public BigDecimal getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(BigDecimal gasPrice) {
        this.gasPrice = gasPrice;
    }

    public BigInteger getCumulativeGas() {
        return cumulativeGas;
    }

    public void setCumulativeGas(BigInteger cumulativeGas) {
        this.cumulativeGas = cumulativeGas;
    }

    public String getRandomId() {
        return randomId;
    }

    public void setRandomId(String randomId) {
        this.randomId = randomId;
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"pkHash\":\"")
                .append(pkHash).append('\"');
        sb.append(",\"blockHash\":\"")
                .append(blockHash).append('\"');
        sb.append(",\"blockNumber\":")
                .append(blockNumber);
        sb.append(",\"transactionIndex\":")
                .append(transactionIndex);
        sb.append(",\"transactionFrom\":\"")
                .append(transactionFrom).append('\"');
        sb.append(",\"transactionTo\":\"")
                .append(transactionTo).append('\"');
        sb.append(",\"gas\":")
                .append(gas);
        sb.append(",\"gasPrice\":")
                .append(gasPrice);
        sb.append(",\"cumulativeGas\":")
                .append(cumulativeGas);
        sb.append(",\"randomId\":\"")
                .append(randomId).append('\"');
        sb.append(",\"inputText\":\"")
                .append(inputText).append('\"');
        sb.append(",\"queueType\":\"")
                .append(queueType).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
