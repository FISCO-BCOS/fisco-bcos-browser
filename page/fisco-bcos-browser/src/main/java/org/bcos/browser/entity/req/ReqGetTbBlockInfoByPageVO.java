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
 * @file: ReqGetTbBlockInfoByPageVO.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.entity.req;

import org.bcos.browser.entity.base.BasePageReqEntity;

public class ReqGetTbBlockInfoByPageVO extends BasePageReqEntity {
    private String hash;//hash
    private String dateTime1;
    private String dateTime2;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getDateTime1() {
        return dateTime1;
    }

    public void setDateTime1(String dateTime1) {
        this.dateTime1 = dateTime1;
    }

    public String getDateTime2() {
        return dateTime2;
    }

    public void setDateTime2(String dateTime2) {
        this.dateTime2 = dateTime2;
    }
}
