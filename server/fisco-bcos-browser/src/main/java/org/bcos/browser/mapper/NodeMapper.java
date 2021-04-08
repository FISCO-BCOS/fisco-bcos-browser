package org.bcos.browser.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.bcos.browser.entity.dto.Node;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeMapper {

    void add(Node node);
    
    void sync(Node node);

    int getNodeCnts(Map<String, Object> map);

    List<Node> getNodeListByPage(Map<String, Object> map);

    List<Node> getManualNode(@Param(value = "groupId") int groupId);

    List<Node> getAllNode(@Param(value = "groupId") int groupId);

    void updateStatus(Map<String, Object> map);
    
    void updateToSync(@Param(value = "groupId") int groupId,
            @Param(value = "nodeId") String nodeId);

    void deleteNodeById(@Param(value = "groupId") int groupId,
            @Param(value = "nodeId") String nodeId);
    
    void deleteNodeByGroupId(@Param(value = "groupId") int groupId);
}
