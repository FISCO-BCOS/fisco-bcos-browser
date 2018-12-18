package org.bcos.browser.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.bcos.browser.dto.TbAddWarrantEventDto;

public interface TbAddWarrantEventMapper {
    public List<TbAddWarrantEventDto> getAllAddWarrantEvent();
    public TbAddWarrantEventDto getAddWarrantEventByID(@Param(value = "warrantID") String warrantID);
}
