package org.bcos.browser.entity.req;

import org.bcos.browser.base.ConstantCode;

import javax.validation.constraints.NotNull;

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
 * @file: ReqDeleteNodeConfigRowVO.java
 * @author: v_wbsqwu
 * @date: 2018
 */
public class ReqDeleteNodeConfigRowVO {
    @NotNull(message = ConstantCode.DELETE_FAIL_PK_ID_PARAM_EMPTY)
    private Integer pkId;

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }
}
