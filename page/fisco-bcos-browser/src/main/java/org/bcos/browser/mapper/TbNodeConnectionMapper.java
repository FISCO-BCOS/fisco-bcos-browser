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
 * @file: TbNodeConnectionMapper.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.mapper;


import org.bcos.browser.dto.TbNodeConnectionDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TbNodeConnectionMapper {
    /*Get the total number of records in the node rpc link table*/
    int getAllNodeConnectionCount(Map<String,Object> map);
    /*Paging query the latest record*/
    List<TbNodeConnectionDto> getTbNodeConnectionByOffset(Map<String,Object> map);
    /*Get node configuration information based on pkid*/
    TbNodeConnectionDto getTbNodeConnectionByPkId(@Param(value = "pkId") Integer pkId);
    /*Delete node configuration information*/
    Integer deleteTbNodeConnection(@Param(value = "pkId") Integer pkId);
    /*Modify node configuration information*/
    Integer updateTbNodeConnection(Map<String,Object> map);
    /*Add record*/
    Long addRow(TbNodeConnectionDto tbNodeConnectionDto);
}
