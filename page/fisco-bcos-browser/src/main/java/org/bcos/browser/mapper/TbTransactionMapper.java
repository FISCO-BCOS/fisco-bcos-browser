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
 * @file: TbTransactionMapper.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.mapper;

import org.bcos.browser.dto.TbTransactionDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TbTransactionMapper {
    /*Get the total number of records in the transaction information table*/
    int getAllTransactionCount(Map<String,Object> map);
    /*Paging query the latest record*/
    List<TbTransactionDto> getTbTransactionByOffset(Map<String,Object> map);
    /*According to pkHash query transaction table records*/
    TbTransactionDto getTbTransactionByPkHash(@Param(value = "pkHash") String pkHash);
}