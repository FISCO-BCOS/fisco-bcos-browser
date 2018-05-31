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
 * @file: ReqAddNodeConfigRowVO.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.entity.req;

import org.bcos.browser.base.ConstantCode;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ReqAddNodeConfigRowVO {
    @NotBlank(message = ConstantCode.SAVE_FAIL_IP_PARAM_EMPTY)
    private String ip;
    @NotNull(message = ConstantCode.SAVE_FAIL_RPCPORT_PARAM_EMPTY)
    @Min(value=1,message = ConstantCode.SAVE_FAIL_RPCPORT_PARAM_FORMAT_ERROR)
    private Integer rpcPort;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getRpcPort() {
        return rpcPort;
    }

    public void setRpcPort(Integer rpcPort) {
        this.rpcPort = rpcPort;
    }
}
