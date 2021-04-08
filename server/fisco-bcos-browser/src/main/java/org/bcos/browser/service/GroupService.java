package org.bcos.browser.service;

import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.enums.NodeSyncType;
import org.bcos.browser.base.exception.BaseException;
import org.bcos.browser.entity.base.BaseResponse;
import org.bcos.browser.entity.dto.Group;
import org.bcos.browser.entity.dto.Node;
import org.bcos.browser.entity.dto.Peer;
import org.bcos.browser.entity.dto.SyncInfoFromChain;
import org.bcos.browser.mapper.BlockChainInfoMapper;
import org.bcos.browser.mapper.GroupMapper;
import org.bcos.browser.mapper.NodeMapper;
import org.bcos.browser.mapper.UserMapper;
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
    UserMapper userMapper;
    @Autowired
    TableService tableService;
    @Autowired
    NodeService nodeService;

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
        tableService.newTableByGroupId(groupId);

        label: for (Group loop : list) {
            List<Node> nodes = nodeMapper.getAllNode(loop.getGroupId());
            for (Node node : nodes) {
                if (node.getType() == NodeSyncType.MANUAL.getValue()) {
                    SyncInfoFromChain syncInfo = web3jRpc.getSyncInfo(groupId, node);
                    if (syncInfo != null) {
                        node.setGroupId(groupId);
                        node.setNodeId(syncInfo.getNodeId());
                        node.setType(NodeSyncType.MANUAL.getValue());
                        nodeMapper.add(node);
                        // sync node info
                        for (Peer peer : syncInfo.getPeers()) {
                            Node syncNode = new Node();
                            syncNode.setNodeId(peer.getNodeId());
                            syncNode.setGroupId(groupId);
                            syncNode.setType(NodeSyncType.AUTO.getValue());
                            nodeMapper.sync(syncNode);
                        }
                        // add data export
                        nodeService.dataExportStart(node);
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
    public List<Group> getGroupList() {
        List<Group> list = groupMapper.getGroupList();
        return list;
    }

    /**
     * deleteGroup.
     * 
     * @param groupId groupId
     * @return
     */
    @Transactional
    public BaseResponse deleteGroup(int groupId) throws BaseException {
        // check group id
        checkGroupId(groupId);
        // stop data export
        nodeService.dataExportStopByGroupId(groupId);
        // delete node
        nodeMapper.deleteNodeByGroupId(groupId);
        // drop table
        tableService.dropTableByGroupId(groupId);
        // delete group info
        groupMapper.deleteGroup(groupId);
        // delete blockChainInfo
        blockChainInfoMapper.deleteByGroupId(groupId);
        // delete txn
        blockChainInfoMapper.deleteTxnByGroupId(groupId);
        // delete user info
        userMapper.deleteByGroupId(groupId);
        return new BaseResponse(ConstantCode.SUCCESS);
    }

    public void checkGroupId(int groupId) throws BaseException {
        Group group = groupMapper.getGroupById(groupId);
        if (Objects.isNull(group)) {
            throw new BaseException(ConstantCode.GROUP_ID_NOT_EXISTS);
        }
    }

    public Group getGroupById(int groupId) {
        Group group = groupMapper.getGroupById(groupId);
        return group;
    }
}
