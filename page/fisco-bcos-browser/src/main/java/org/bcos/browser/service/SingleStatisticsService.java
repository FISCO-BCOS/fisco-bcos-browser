package org.bcos.browser.service;

import org.bcos.browser.dto.TbStatDto;
import org.bcos.browser.entity.req.ReqListTbStatByObjectAttrVO;

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
 * @file: SingleStatisticsService.java
 * @author: v_wbsqwu
 * @date: 2018
 */
public interface SingleStatisticsService {
    /*Query Object list*/
    List<String> listObject(String dateStr);
    /*Query property list*/
    List<TbStatDto> listAttr(String dateStr);
    /*Query a single point statistics list*/
    List<List<TbStatDto>> ListTbStat(ReqListTbStatByObjectAttrVO reqVo);
}
