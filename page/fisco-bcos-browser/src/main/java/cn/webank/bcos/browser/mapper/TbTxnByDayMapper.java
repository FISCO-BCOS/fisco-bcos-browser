package cn.webank.bcos.browser.mapper;

import cn.webank.bcos.browser.dto.TbTxnByDayDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *@Description: Daily trading volume record table,mapper
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 11:58
 */
public interface TbTxnByDayMapper {
   /*Paging query the latest record*/
    List<TbTxnByDayDto> getLastTbTxnByDayByOffset(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);
}
