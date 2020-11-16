package org.bcos.browser.controller;

import javax.validation.Valid;
import org.bcos.browser.auth.ConfigAuth;
import org.bcos.browser.base.BaseController;
import org.bcos.browser.base.exception.BaseException;
import org.bcos.browser.entity.base.BasePageResponse;
import org.bcos.browser.entity.base.BaseResponse;
import org.bcos.browser.entity.req.ReqAddNode;
import org.bcos.browser.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "node")
public class NodeController extends BaseController {
    @Autowired
    NodeService nodeService;

    /**
     * addNode.
     * 
     * @param reqAddNode node info
     * @param result checkResult
     * @return
     */
    @ConfigAuth
    @PostMapping("/add")
    public BaseResponse addNode(@Valid @RequestBody ReqAddNode reqAddNode, BindingResult result)
            throws BaseException {
        checkParamResult(result);
        BaseResponse response = nodeService.addNode(reqAddNode);
        return response;
    }

    /**
     * getNodeList.
     * 
     * @param groupId groupId
     * @param pageNumber pageNumber
     * @param pageSize pageSize
     * @param ip node ip
     * @param rpcPort node rpcPort
     * @param p2pPort node p2pPort
     * @return
     */
    @GetMapping("/nodeList/{groupId}/{pageNumber}/{pageSize}")
    public BasePageResponse getNodeList(@PathVariable("groupId") int groupId,
            @PathVariable("pageNumber") int pageNumber,
            @PathVariable("pageSize") int pageSize,
            @RequestParam(value = "type", required = false) int type,
            @RequestParam(value = "ip", required = false) String ip,
            @RequestParam(value = "rpcPort", required = false) String rpcPort,
            @RequestParam(value = "p2pPort", required = false) String p2pPort) {
        BasePageResponse response =
                nodeService.getNodeList(groupId, pageNumber, pageSize, type, ip, rpcPort, p2pPort);
        return response;
    }

    /**
     * deleteNodeById.
     * 
     * @param nodeId nodeId
     * @return
     */
    @ConfigAuth
    @DeleteMapping("/deleteById/{groupId}/{nodeId}")
    public BaseResponse deleteNodeById(@PathVariable("groupId") int groupId,
            @PathVariable("nodeId") String nodeId) {
        BaseResponse response = nodeService.deleteNodeById(groupId, nodeId);
        return response;
    }
    
    /**
     * getEncryptType.
     * 
     * @param groupId groupId
     * @return
     */
    @GetMapping("/getEncryptType/{groupId}")
    public BaseResponse getEncryptType(@PathVariable("groupId") int groupId) {
        BaseResponse response = nodeService.getEncryptType(groupId);
        return response;
    }
}
