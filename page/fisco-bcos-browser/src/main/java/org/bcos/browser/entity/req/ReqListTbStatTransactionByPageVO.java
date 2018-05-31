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
 * @file: ReqListTbStatTransactionByPageVO.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.entity.req;

import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.entity.base.BasePageReqEntity;
import org.hibernate.validator.constraints.NotBlank;

public class ReqListTbStatTransactionByPageVO extends BasePageReqEntity {
    private String hash;//hash
    @NotBlank(message = ConstantCode.QUERY_FAIL_DATESTR_PARAM_EMPTY)
    private String dateStr;
    private String timeStartStr;
    private String timeEndStr;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getTimeStartStr() {
        return timeStartStr;
    }

    public void setTimeStartStr(String timeStartStr) {
        this.timeStartStr = timeStartStr;
    }

    public String getTimeEndStr() {
        return timeEndStr;
    }

    public void setTimeEndStr(String timeEndStr) {
        this.timeEndStr = timeEndStr;
    }
}
