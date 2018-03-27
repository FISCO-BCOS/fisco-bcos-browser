package cn.webank.bcos.browser.mapper;

import cn.webank.bcos.browser.dto.TbStatTransactionDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description:Transaction flow statistics table,mapper
 * @Author: v_wbsqwu
 * @Date: 2017/11/22 11:23
 */
public interface TbStatTransactionMapper {
   //Query transaction process statistics list total records
   int countTbStatTransaction(Map<String,Object> map);
   //Paging query transaction process statistics list
    List<TbStatTransactionDto> listTbStatTransaction(Map<String,Object> map);
}
