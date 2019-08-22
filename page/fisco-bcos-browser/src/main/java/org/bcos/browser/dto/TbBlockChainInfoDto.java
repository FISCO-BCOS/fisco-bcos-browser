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
 * @file: TbBlockChainInfoDto.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

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