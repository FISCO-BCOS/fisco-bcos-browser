package org.bcos.browser.controller;

import org.bcos.browser.base.BaseController;
import org.bcos.browser.base.exception.BaseException;
import org.bcos.browser.entity.base.BasePageResponse;
import org.bcos.browser.entity.base.BaseResponse;
import org.bcos.browser.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "block")
public class BlockController extends BaseController {
    @Autowired
    BlockService blockService;

    /**
     * getBlockInfoByPage.
     * 
     * @param groupId groupId
     * @param pageNumber pageNumber
     * @param pageSize pageSize
     * @param blockHash blockHash
     * @param blockNumber blockNumber
     * @return
     */
    @GetMapping("/blockList/{groupId}/{pageNumber}/{pageSize}")
    public BasePageResponse getBlockInfoByPage(@PathVariable("groupId") int groupId,
            @PathVariable("pageNumber") int pageNumber, @PathVariable("pageSize") int pageSize,
            @RequestParam(value = "blockHash", required = false) String blockHash,
            @RequestParam(value = "blockNumber", required = false) String blockNumber)
            throws BaseException {
        BasePageResponse response = blockService.getBlockInfoByPage(groupId, pageNumber, pageSize,
                blockHash, blockNumber);
        return response;
    }

    /**
     * getBlockInfoByHash.
     * 
     * @param groupId groupId
     * @param blockHash blockHash
     * @return
     * @throws BaseException
     */
    @GetMapping("/blockByHash/{groupId}/{blockHash}")
    public BaseResponse getBlockInfoByHash(@PathVariable("groupId") int groupId,
            @PathVariable("blockHash") String blockHash) throws BaseException {
        BaseResponse response = blockService.getBlockInfoByHash(groupId, blockHash);
        return response;
    }
}
