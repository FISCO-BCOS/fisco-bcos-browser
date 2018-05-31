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
 * @file: TbTransactionDto.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

public class TbTransactionDto implements Serializable {
    private static final long serialVersionUID = -5208902808917066427L;
    private String pkHash;//	TxHash
    private String blockHash;//		VARCHAR	32
    private Integer blockNumber;
    private Timestamp blockTimestamp;
    private BigInteger blockGasLimit;//Gas Limit
    private BigInteger transactionIndex;
    private String transactionFrom;
    private String transactionTo;
    private BigInteger gas;//Used By Txn
    private BigDecimal gasPrice;
    private BigInteger cumulativeGas;
    private String randomId;
    private String inputText;//Input Data
    private String contractName;
    private String version;
    private String method;
    private String params;

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

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}