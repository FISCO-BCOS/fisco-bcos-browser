package org.bcos.browser.mapper;

import java.util.List;
import java.util.Map;
import org.bcos.browser.entity.dto.Block;

public interface BlockMapper {

    void add(Block block);

    int getAllBlockCount(Map<String, Object> map);

    List<Block> getBlockInfoByBage(Map<String, Object> map);

    int getMaxBlockNumber(int groupId);
}
