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
package org.bcos.browser.entity.dto;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * save user's address, userName, groupId etc.
 */
@Data
@NoArgsConstructor
public class User {

    private Integer userId;
    private String userName;
    private Integer groupId;
    private String address;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;

    /**
     * init TbUser.
     */
    public User(String userName, Integer groupId, String address, String description) {
        super();
        this.userName = userName;
        this.groupId = groupId;
        this.description = description;
        this.address = address;
    }
}
