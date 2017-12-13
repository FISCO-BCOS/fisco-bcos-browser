package org.bcos.browser.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 每个交易的表,实体类
 * Created by wicker on 2017/10/12.
 */
public class TbTransactionDto implements Serializable {
    private static final long serialVersionUID = -5208902808917066427L;
    private String pkHash;//	TxHash
    private String blockHash;//		VARCHAR	32
    private Integer blockNumber;//块高
    private Timestamp blockTimestamp;//时间戳
    private BigInteger blockGasLimit;//Gas Limit
    private BigInteger transactionIndex;
    private String transactionFrom;
    private String transactionTo;
    private BigInteger gas;//Used By Txn
    private BigDecimal gasPrice;
    private BigInteger cumulativeGas;
    private String randomId;
    private String inputText;//Input Data


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

    public Timestamp getBlockTimestamp() {
        return blockTimestamp;
    }

    public void setBlockTimestamp(Timestamp blockTimestamp) {
        this.blockTimestamp = blockTimestamp;
    }

    public BigInteger getBlockGasLimit() {
        return blockGasLimit;
    }

    public void setBlockGasLimit(BigInteger blockGasLimit) {
        this.blockGasLimit = blockGasLimit;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"pkHash\":\"")
                .append(pkHash).append('\"');
        sb.append(",\"blockHash\":\"")
                .append(blockHash).append('\"');
        sb.append(",\"blockNumber\":")
                .append(blockNumber);
        sb.append(",\"blockTimestamp\":\"")
                .append(blockTimestamp).append('\"');
        sb.append(",\"blockGasLimit\":")
                .append(blockGasLimit);
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
        sb.append('}');
        return sb.toString();
    }
}
