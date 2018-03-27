package cn.webank.bcos.browser.service;

import cn.webank.bcos.browser.dto.TbStatDto;
import cn.webank.bcos.browser.entity.req.ReqListTbStatByObjectAttrVO;

import java.util.List;

/**
 * @Description:Single point statistics table, service
 * @Author: v_wbsqwu
 * @Date: 2017/11/22 16:47
 */
public interface SingleStatisticsService {
    /*Query Object list*/
    List<String> listObject(String dateStr);
    /*Query property list*/
    List<TbStatDto> listAttr(String dateStr);
    /*Query a single point statistics list*/
    List<List<TbStatDto>> ListTbStat(ReqListTbStatByObjectAttrVO reqVo);
}
