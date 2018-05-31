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
 * @file: RspGetTbNodesInfoByPageVO.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.entity.rsp;

import java.io.Serializable;

public class RspGetTbNodesInfoByPageVO implements Serializable {
    private static final long serialVersionUID = 4569906256526130891L;
    private String pkId;
    private String addr;
    private Integer blockNumber;
    private String active;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Integer getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Integer blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"pkId\":\"")
                .append(pkId).append('\"');
        sb.append(",\"addr\":\"")
                .append(addr).append('\"');
        sb.append(",\"blockNumber\":")
                .append(blockNumber);
        sb.append(",\"active\":\"")
                .append(active).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
