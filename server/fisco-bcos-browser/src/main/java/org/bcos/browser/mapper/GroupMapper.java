package org.bcos.browser.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.bcos.browser.entity.dto.Group;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMapper {
    
    void createTbGroup(@Param(value = "tableName")String tableName);
    
    void createTbNode(@Param(value = "tableName")String tableName);
    
    void createTbBlockChainInfo(@Param(value = "tableName")String tableName);
    
    void createTbTxnByDay(@Param(value = "tableName")String tableName);
    
    void createTbBlock(@Param(value = "tableName")String tableName);
    
    void createTbTransaction(@Param(value = "tableName")String tableName);
    
    void createTbContract(@Param(value = "tableName")String tableName);
    
    void dropTableByName(@Param(value = "tableName")String tableName);
    
    void addGroup(Group group);

    List<Group> getGroupList();

    void deleteGroup(int groupId);

}
