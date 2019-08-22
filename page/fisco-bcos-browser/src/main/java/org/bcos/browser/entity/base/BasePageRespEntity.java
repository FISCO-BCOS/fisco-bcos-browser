package org.bcos.browser.entity.base;

import org.bcos.browser.base.Constants;
import org.bcos.browser.base.RetCode;

import java.util.List;

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
 * @file: BasePageRespEntity.java
 * @author: v_wbsqwu
 * @date: 2018
 */
public class BasePageRespEntity<T>{
    private int status;
    private String msg;
    private Integer total;
    private Integer pageTotal;
    private Integer pageSize;
    private Integer pageNumber;
    private Integer start;
    private List<T> list;

    public Integer getTotal() {
        return total;
    }

    public Integer getPageTotal() {
        if (total > 0 && total <= pageSize) {
            pageTotal = 1;
        } else {
            pageTotal = total / pageSize;
            if((total % pageSize)>0){
                pageTotal +=1;
            }
        }

        return pageTotal;
    }

    public Integer getPageSize() {
        return (pageSize==null || pageSize==0) ? Constants.DB_QUERY_DEFAULT_PAGE_SIZE : pageSize;
    }

    public Integer getPageNumber() {
        return (pageNumber==null || pageNumber==0) ? Constants.DB_QUERY_DEFAULT_PAGE_NO : pageNumber;
    }

    public Integer getStart() {
        return (getPageNumber() - 1) * getPageSize();
    }

    public List<T> getList() {
        return list;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }



    //设置返回码
    public void setRetCode(RetCode retCode){
        this.status = retCode.getCode();
        this.msg = retCode.getMsg();
    }
}