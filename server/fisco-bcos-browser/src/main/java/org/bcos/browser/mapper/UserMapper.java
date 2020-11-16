/**
 * Copyright 2014-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.bcos.browser.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.bcos.browser.entity.dto.User;
import org.bcos.browser.entity.dto.UserQueryParam;
import org.springframework.stereotype.Repository;

/**
 * user data interface.
 */
@Repository
public interface UserMapper {

    Integer addUserRow(User user);

    Integer countOfUser(UserQueryParam userQueryParam);

    List<User> listOfUser(UserQueryParam userQueryParam);

    User queryUser(@Param("userId") Integer userId, @Param("groupId") Integer groupId,
            @Param("userName") String userName, @Param("address") String address);
    
    Integer updateUser(User user);
    
    void deleteById(@Param("userId") Integer userId);

    void deleteByGroupId(@Param("groupId") Integer groupId);

}
