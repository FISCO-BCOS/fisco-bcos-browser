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
package org.bcos.browser.service;

import java.util.List;
import java.util.Objects;
import lombok.extern.log4j.Log4j2;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.exception.BaseException;
import org.bcos.browser.entity.dto.User;
import org.bcos.browser.entity.dto.UserQueryParam;
import org.bcos.browser.entity.req.ReqAddUser;
import org.bcos.browser.entity.req.ReqUpdateUser;
import org.bcos.browser.mapper.UserMapper;
import org.bcos.browser.util.AddressUtils;
import org.bcos.browser.util.JsonTools;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * services for user data.
 */
@Log4j2
@Service
public class UserService {

    @Autowired
    private GroupService groupService;
    @Autowired
    private UserMapper userMapper;

    /**
     * add user info.
     */
    @Transactional
    public Integer addUserInfo(ReqAddUser user) throws BaseException {
        log.debug("start addUserInfo User:{}", JsonTools.toJSONString(user));

        // check group id
        int groupId = user.getGroupId();
        groupService.checkGroupId(groupId);

        // check userName
        User userRow = queryByName(groupId, user.getUserName());
        if (Objects.nonNull(userRow)) {
            log.warn("fail addUserInfo. userName is already exists");
            throw new BaseException(ConstantCode.USER_EXISTS);
        }

        // check address
        String address = user.getAddress();
        AddressUtils.checkAndGetAddress(address);
        User addressRow = queryUser(null, groupId, null, address);
        if (Objects.nonNull(addressRow)) {
            log.warn("fail addUserInfo. address is already exists");
            throw new BaseException(ConstantCode.USER_EXISTS);
        }

        // add row
        User newUserRow = new User(user.getUserName(), groupId, address, user.getDescription());
        Integer affectRow = userMapper.addUserRow(newUserRow);
        if (affectRow == 0) {
            log.warn("addUserInfo affect 0 rows of tb_user");
            throw new BaseException(ConstantCode.DB_EXCEPTION);
        }
        Integer userId = newUserRow.getUserId();
        log.debug("end addUserInfo userId:{}", userId);
        return userId;
    }

    /**
     * query count of user.
     */
    public Integer countOfUser(UserQueryParam userQueryParam) throws BaseException {
        try {
            Integer count = userMapper.countOfUser(userQueryParam);
            return count;
        } catch (RuntimeException ex) {
            log.error("fail countOfUser userParam:{}", JsonTools.toJSONString(userQueryParam), ex);
            throw new BaseException(ConstantCode.DB_EXCEPTION);
        }
    }

    /**
     * query user list by page.
     */
    public List<User> qureyUserList(UserQueryParam userQueryParam) throws BaseException {
        log.debug("start qureyUserList userParam:{}", JsonTools.toJSONString(userQueryParam));
        // query user list
        List<User> listOfUser = userMapper.listOfUser(userQueryParam);
        log.debug("end qureyUserList listOfUser:{}", JsonTools.toJSONString(listOfUser));
        return listOfUser;
    }

    /**
     * update user info.
     */
    public void updateUser(ReqUpdateUser reqUpdateUser) throws BaseException {
        // check userId
        Integer userId = reqUpdateUser.getUserId();
        User userRow = queryByUserId(userId);
        if (userRow == null) {
            log.warn("fail updateUser. user id not exists");
            throw new BaseException(ConstantCode.USER_NOT_EXISTS);
        }
        // check address
        AddressUtils.checkAndGetAddress(reqUpdateUser.getAddress());
        // update
        try {
            User user = new User();
            BeanUtils.copyProperties(reqUpdateUser, user);
            Integer affectRow = userMapper.updateUser(user);
            if (affectRow == 0) {
                log.warn("affect 0 rows of tb_user");
                throw new BaseException(ConstantCode.DB_EXCEPTION);
            }
        } catch (RuntimeException ex) {
            log.error("fail updateUser userId:{}", userId, ex);
            throw new BaseException(ConstantCode.DB_EXCEPTION);
        }
    }

    public void deleteById(Integer userId) throws BaseException {
        try {
            userMapper.deleteById(userId);
        } catch (RuntimeException ex) {
            log.error("fail deleteById userId:{}", userId, ex);
            throw new BaseException(ConstantCode.DB_EXCEPTION);
        }
    }

    /**
     * query by userId.
     */
    public User queryByUserId(Integer userId) throws BaseException {
        return queryUser(userId, null, null, null);
    }

    /**
     * query name by address.
     */
    public String queryNameByAddress(Integer groupId, String address) throws BaseException {
        User user = queryUser(null, groupId, null, address);
        if (user != null) {
            return user.getUserName();
        }
        return address;
    }

    /**
     * query by userName.
     */
    private User queryByName(Integer groupId, String userName) throws BaseException {
        return queryUser(null, groupId, userName, null);
    }

    /**
     * query user row.
     */
    private User queryUser(Integer userId, Integer groupId, String userName, String address)
            throws BaseException {
        try {
            User userRow = userMapper.queryUser(userId, groupId, userName, address);
            return userRow;
        } catch (RuntimeException ex) {
            log.error("fail queryUser userId:{} groupId:{} userName:{}  address:{}", userId,
                    groupId, userName, address, ex);
            throw new BaseException(ConstantCode.DB_EXCEPTION);
        }
    }
}
