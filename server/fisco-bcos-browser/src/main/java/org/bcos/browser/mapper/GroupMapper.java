package org.bcos.browser.mapper;

import java.util.List;
import org.bcos.browser.entity.dto.Group;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMapper {
    
    void addGroup(Group group);
    
    List<Group> getGroupList();

    Group getGroupById(int groupId);

    void deleteGroup(int groupId);

}
