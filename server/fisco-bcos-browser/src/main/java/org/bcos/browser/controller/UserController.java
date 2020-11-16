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
package org.bcos.browser.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.bcos.browser.auth.ConfigAuth;
import org.bcos.browser.base.BaseController;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.exception.BaseException;
import org.bcos.browser.entity.base.BasePageResponse;
import org.bcos.browser.entity.base.BaseResponse;
import org.bcos.browser.entity.dto.User;
import org.bcos.browser.entity.req.ReqAddUser;
import org.bcos.browser.entity.req.ReqUpdateUser;
import org.bcos.browser.entity.dto.UserQueryParam;
import org.bcos.browser.service.UserService;
import org.bcos.browser.util.JsonTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController.
 */
@Log4j2
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * add user info.
     */
    @PostMapping(value = "/add")
    public BaseResponse addUserInfo(@RequestBody @Valid ReqAddUser user, BindingResult result)
            throws BaseException {
        checkParamResult(result);
        BaseResponse baseResponse = new BaseResponse(ConstantCode.SUCCESS);
        Instant startTime = Instant.now();
        // add user row
        Integer userId = userService.addUserInfo(user);
        // query user row
        User userRow = userService.queryByUserId(userId);
        baseResponse.setData(userRow);
        log.info("end addUserInfo useTime:{} result:{}",
                Duration.between(startTime, Instant.now()).toMillis(),
                JsonTools.toJSONString(baseResponse));
        return baseResponse;
    }

    /**
     * qurey user info list.
     */
    @GetMapping(value = "/userList/{groupId}/{pageNumber}/{pageSize}")
    public BasePageResponse userList(@PathVariable("groupId") Integer groupId,
            @PathVariable("pageNumber") Integer pageNumber,
            @PathVariable("pageSize") Integer pageSize,
            @RequestParam(value = "userParam", required = false) String userParam)
            throws BaseException {
        BasePageResponse pagesponse = new BasePageResponse(ConstantCode.SUCCESS);
        Instant startTime = Instant.now();
        log.info("start userList startTime:{} groupId:{} pageNumber:{} pageSize:{} userParam:{}",
                startTime.toEpochMilli(), groupId, pageNumber, pageSize, userParam);

        UserQueryParam param = new UserQueryParam();
        param.setGroupId(groupId);
        param.setUserParam(userParam);

        Integer count = userService.countOfUser(param);
        if (count != null && count > 0) {
            Integer start =
                    Optional.ofNullable(pageNumber).map(page -> (page - 1) * pageSize).orElse(null);
            param.setStart(start);
            param.setPageSize(pageSize);
            List<User> listOfUser = userService.qureyUserList(param);
            pagesponse.setData(listOfUser);
            pagesponse.setTotalCount(count);
        }

        log.info("end userList useTime:{} result:{}",
                Duration.between(startTime, Instant.now()).toMillis(),
                JsonTools.toJSONString(pagesponse));
        return pagesponse;
    }

    /**
     * update user info.
     */
    @PutMapping(value = "/update")
    public BaseResponse updateUserInfo(@RequestBody @Valid ReqUpdateUser reqUpdateUser,
            BindingResult result) throws BaseException {
        checkParamResult(result);
        BaseResponse baseResponse = new BaseResponse(ConstantCode.SUCCESS);
        Instant startTime = Instant.now();
        log.info("start updateUserInfo startTime:{} userUpdateParam:{}", startTime.toEpochMilli(),
                JsonTools.toJSONString(reqUpdateUser));
        // update user row
        userService.updateUser(reqUpdateUser);
        // query user row
        User userRow = userService.queryByUserId(reqUpdateUser.getUserId());
        baseResponse.setData(userRow);
        log.info("end updateUserInfo useTime:{} result:{}",
                Duration.between(startTime, Instant.now()).toMillis(),
                JsonTools.toJSONString(baseResponse));
        return baseResponse;
    }

    /**
     * deleteById.
     * 
     * @param nodeId nodeId
     * @return
     */
    @ConfigAuth
    @DeleteMapping("/deleteById/{userId}")
    public BaseResponse deleteUserById(@PathVariable("userId") int userId) throws BaseException {
        log.info("deleteUserById. userId:{}", userId);
        BaseResponse response = new BaseResponse(ConstantCode.SUCCESS);
        userService.deleteById(userId);
        return response;
    }
}
