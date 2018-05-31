/*
 * This file is part of FISCO BCOS Browser.
 *
 * FISCO BCOS Browser is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FISCO BCOS Browser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FISCO BCOS Browser.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * @file: RspTbTransactionInfoVO.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.entity.rsp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class RspTbTransactionInfoVO implements Serializable{
    private static final long serialVersionUID = 5738497779153145147L;
    private String pkHash;//	TxHash
    private String blockHash;//		VARCHAR	32
    private Integer blockNumber;
    private String blockTimesStr;
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

    public String getBlockTimesStr() {
        return blockTimesStr;
    }

    public void setBlockTimesStr(String blockTimesStr) {
        this.blockTimesStr = blockTimesStr;
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
        sb.append(",\"blockTimesStr\":\"")
                .append(blockTimesStr).append('\"');
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
