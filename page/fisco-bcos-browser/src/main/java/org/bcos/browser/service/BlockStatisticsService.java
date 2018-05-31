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
 * @file: BlockStatisticsService.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.service;

import org.bcos.browser.dto.TbStatBlockDto;

import java.util.List;
import java.util.Map;

public interface BlockStatisticsService {
    //Query the total number of records in the block flow statistics list
    int countTbStatBlock(Map<String,Object> map);
    //Paging query block process statistics list
    List<TbStatBlockDto> listTbStatBlock(Map<String,Object> map);
}
