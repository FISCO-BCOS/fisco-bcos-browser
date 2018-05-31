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
 * @file: BasePageReqEntity.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.entity.base;



import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.Constants;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class BasePageReqEntity {
    @NotNull(message = ConstantCode.QUERY_FAIL_PAGE_SIZE_EMPTY)
    @Min(value = 5,message = ConstantCode.QUERY_FAIL_PAGE_SIZE_MIN_5)
    private Integer pageSize = Constants.DB_QUERY_DEFAULT_PAGE_SIZE;

    @NotNull(message = ConstantCode.QUERY_FAIL_PAGE_NUMBER_EMPTY)
    @Min(value = 1,message = ConstantCode.QUERY_FAIL_PAGE_NUMBER_MIN_1)
    private Integer pageNumber = Constants.DB_QUERY_DEFAULT_PAGE_NO;
    private Integer start = 0;

    public Integer getPageSize() {
        return (pageSize==null || pageSize==0 || pageSize>Constants.DB_QUERY_MAX_PAGE_SIZE) ? Constants.DB_QUERY_DEFAULT_PAGE_SIZE : pageSize;
    }

    public Integer getPageNumber() {
        return (pageNumber==null || pageNumber==0) ? Constants.DB_QUERY_DEFAULT_PAGE_NO : pageNumber;
    }

    public Integer getStart() {
        return (getPageNumber() - 1) * getPageSize();
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
}