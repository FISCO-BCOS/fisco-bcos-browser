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
 * @file: ReqGetTbTransactionByPkHashVO.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.entity.req;

import org.bcos.browser.base.ConstantCode;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

public class ReqGetTbTransactionByPkHashVO implements Serializable{
    private static final long serialVersionUID = -1268724433070179725L;
    @NotEmpty(message = ConstantCode.QUERY_FAIL_PK_HASH_PARAM_EMPTY)
    private String PkHash;

    public String getPkHash() {
        return PkHash;
    }

    public void setPkHash(String pkHash) {
        PkHash = pkHash;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"PkHash\":\"")
                .append(PkHash).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
