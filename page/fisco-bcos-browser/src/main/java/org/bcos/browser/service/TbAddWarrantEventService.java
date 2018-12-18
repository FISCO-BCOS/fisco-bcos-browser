package org.bcos.browser.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.bcos.browser.dto.TbAddWarrantEventDto;

public interface TbAddWarrantEventService {
    public List<TbAddWarrantEventDto> getAllAddWarrantEvent();
    public TbAddWarrantEventDto getAddWarrantEventByID(@Param("warrantID")String warrantID);
}
