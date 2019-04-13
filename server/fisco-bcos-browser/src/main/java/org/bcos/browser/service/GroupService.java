package org.bcos.browser.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.Constants;
import org.bcos.browser.base.exception.BaseException;
import org.bcos.browser.entity.base.BaseResponse;
import org.bcos.browser.entity.dto.*;
import org.bcos.browser.mapper.BlockChainInfoMapper;
import org.bcos.browser.mapper.BlockMapper;
import org.bcos.browser.mapper.ContractMapper;
import org.bcos.browser.mapper.GroupMapper;
import org.bcos.browser.mapper.NodeMapper;
import org.bcos.browser.mapper.TransactionMapper;
import org.bcos.browser.util.Web3jRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class GroupService {
    @Autowired
    GroupMapper groupMapper;
    @Autowired
    BlockChainInfoMapper blockChainInfoMapper;
    @Autowired
    NodeMapper nodeMapper;
    @Autowired
    BlockMapper blockMapper;
    @Autowired
    TransactionMapper transactionMapper;
    @Autowired
    ContractMapper contractMapper;

    /**
     * addGroup.
     * 
     * @param group info
     * @return
     */
    @Transactional
    public BaseResponse addGroup(Group group) throws BaseException {
        log.info("addGroup group:{}", group);
        int groupId = group.getGroupId();
        Web3jRpc web3jRpc = new Web3jRpc();
        // check if existed
        List<Group> list = groupMapper.getGroupList();
        for (Group loop : list) {
            if (groupId == loop.getGroupId()) {
                throw new BaseException(ConstantCode.GROUP_ID_IS_EXISTED);
            }
            if (group.getGroupName().equals(loop.getGroupName())) {
                throw new BaseException(ConstantCode.GROUP_NAME_IS_EXISTED);
            }
        }
        // create table by group
        String tableName = Constants.PREFIX_TB_NODE + groupId;
        groupMapper.createTbNode(tableName);
        tableName = Constants.PREFIX_TB_BLOCKCHAININFO + groupId;
        groupMapper.createTbBlockChainInfo(tableName);
        tableName = Constants.PREFIX_TB_TXNBYDAY + groupId;
        groupMapper.createTbTxnByDay(tableName);
        tableName = Constants.PREFIX_TB_BLOCK + groupId;
        groupMapper.createTbBlock(tableName);
        tableName = Constants.PREFIX_TB_TRANSACTION + groupId;
        groupMapper.createTbTransaction(tableName);

        label:
        for(Group loop : list){
            List<Node> nodes = nodeMapper.getAllNode(loop.getGroupId());
            for (Node node : nodes){
                if (node.getType() == 0) {
                    SyncInfoFromChain syncInfo = web3jRpc.getSyncInfo(groupId, node);
                    if (syncInfo != null) {
                        node.setGroupId(groupId);
                        node.setNodeId(syncInfo.getNodeId());
                        node.setType(0);
                        nodeMapper.add(node);
                        // sync node info
                        for (Peer peer : syncInfo.getPeers()) {
                            Node syncNode = new Node();
                            syncNode.setNodeId(peer.getNodeId());
                            syncNode.setGroupId(groupId);
                            syncNode.setType(1);
                            nodeMapper.sync(syncNode);
                        }
                        break label;
                    }
                }
            }
        }
        // add group info
        groupMapper.addGroup(group);
        return new BaseResponse(ConstantCode.SUCCESS);
    }

    /**
     * getGroupList.
     * 
     * @return
     */
    public BaseResponse getGroupList() {
        BaseResponse response = new BaseResponse(ConstantCode.SUCCESS);
        List<Group> list = groupMapper.getGroupList();
        response.setData(list);
        return response;
    }

    /**
     * deleteGroup.
     * 
     * @param groupId groupId
     * @return
     */
    @Transactional
    public BaseResponse deleteGroup(int groupId) {
        // drop table
        String tableName = Constants.PREFIX_TB_NODE + groupId;
        groupMapper.dropTableByName(tableName);
        tableName = Constants.PREFIX_TB_BLOCKCHAININFO + groupId;
        groupMapper.dropTableByName(tableName);
        tableName = Constants.PREFIX_TB_TXNBYDAY + groupId;
        groupMapper.dropTableByName(tableName);
        tableName = Constants.PREFIX_TB_BLOCK + groupId;
        groupMapper.dropTableByName(tableName);
        tableName = Constants.PREFIX_TB_TRANSACTION + groupId;
        groupMapper.dropTableByName(tableName);

        // delete group info
        groupMapper.deleteGroup(groupId);
        
        return new BaseResponse(ConstantCode.SUCCESS);
    }
}
