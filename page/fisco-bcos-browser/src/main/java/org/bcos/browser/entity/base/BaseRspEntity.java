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
 * @file: BaseRspEntity.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.entity.base;


import org.bcos.browser.base.RetCode;

public class BaseRspEntity {
    private int status;
    private String msg;
    private Object data;

    public BaseRspEntity(){}
    public BaseRspEntity(int status){
        this.status = status;
    }
    public BaseRspEntity(RetCode rsc){
        this.status = rsc.getCode();
        this.msg = rsc.getMsg();
    }
    public BaseRspEntity(RetCode rsc, Object obj){
        this.status = rsc.getCode();
        this.msg = rsc.getMsg();
        this.data = obj;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}