package org.bcos.browser.mapper;

import java.util.List;
import java.util.Map;
import org.bcos.browser.entity.dto.Block;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockMapper {

    int getAllBlockCount(Map<String, Object> map);

    List<Block> getBlockInfoByBage(Map<String, Object> map);

    int getMaxBlockNumber(int groupId);
}
