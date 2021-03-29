package org.bcos.browser.controller;

import java.util.List;
import javax.validation.Valid;
import org.bcos.browser.auth.ConfigAuth;
import org.bcos.browser.base.BaseController;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.exception.BaseException;
import org.bcos.browser.entity.base.BaseResponse;
import org.bcos.browser.entity.dto.Group;
import org.bcos.browser.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "group")
public class GroupController extends BaseController {
    @Autowired
    GroupService groupService;

    /**
     * addGroup.
     * 
     * @param group info
     * @param result checkResult
     * @return
     */
    @ConfigAuth
    @PostMapping("/add")
    public BaseResponse addGroup(@Valid @RequestBody Group group, BindingResult result)
            throws BaseException {
        checkParamResult(result);
        BaseResponse response = groupService.addGroup(group);
        return response;
    }

    /**
     * getGroupList.
     * 
     * @return
     */
    @GetMapping("/groupList")
    public BaseResponse getGroupList() {
        BaseResponse response = new BaseResponse(ConstantCode.SUCCESS);
        List<Group> list = groupService.getGroupList();
        response.setData(list);
        return response;
    }

    /**
     * deleteGroup.
     * 
     * @param groupId groupId
     * @return
     */
    @ConfigAuth
    @DeleteMapping("/deleteById/{groupId}")
    public BaseResponse deleteGroup(@PathVariable("groupId") int groupId) throws BaseException {
        BaseResponse response = groupService.deleteGroup(groupId);
        return response;
    }
}
