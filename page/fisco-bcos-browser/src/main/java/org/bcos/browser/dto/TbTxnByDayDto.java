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
 * @file: TbTxnByDayDto.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.dto;

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