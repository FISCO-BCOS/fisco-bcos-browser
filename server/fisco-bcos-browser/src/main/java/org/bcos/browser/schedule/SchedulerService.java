package org.bcos.browser.schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.bcos.browser.base.Constants;
import org.bcos.browser.base.enums.NodeStatus;
import org.bcos.browser.base.enums.NodeSyncType;
import org.bcos.browser.base.enums.NodeType;
import org.bcos.browser.entity.dto.*;
import org.bcos.browser.mapper.*;
import org.bcos.browser.service.GroupService;
import org.bcos.browser.util.CommonUtils;
import org.bcos.browser.util.Web3jRpc;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Slf4j
@Component
public class SchedulerService {
    @Autowired
    Web3jRpc web3jRpc;
    @Autowired
    SchedulerService schedulerService;
    @Autowired
    GroupService groupService;
    @Autowired
    BlockChainInfoMapper blockChainInfoMapper;
    @Autowired
    BlockMapper blockMapper;
    @Autowired
    TransactionMapper transactionMapper;
    @Autowired
    NodeMapper nodeMapper;
    @Autowired
    private Constants constants;
    @Autowired
    ChainUserMapper chainUserMapper;
    @Autowired
    ChainContractMapper chainContractMapper;

    /**
     * handleBlockChainInfo.
     */
    public void handleBlockChainInfo() {
        List<Group> list = groupService.getGroupList();
        for (Group loop : list) {
            Object[] params = new Object[] {loop.getGroupId()};
            // get block number and txn
            BlockNumberAndTxn blockNumberAndTxn = web3jRpc.getTxn(loop.getGroupId());
            // get txn
            int pendingTxn = CommonUtils.parseHexStr2Int((String) web3jRpc
                    .rpcRequest(loop.getGroupId(), Constants.GET_PENDING_TX_SIZE, params));
            // get pbftView
            int pbftView = CommonUtils.parseHexStr2Int((String) web3jRpc
                    .rpcRequest(loop.getGroupId(), Constants.GET_PBFT_VIEW, params));

            BlockChainInfo blockChainInfo = new BlockChainInfo();
            blockChainInfo.setGroupId(loop.getGroupId());
            blockChainInfo.setLatestNumber(blockNumberAndTxn.getBlockNumber());
            blockChainInfo.setPbftView(pbftView);
            blockChainInfo.setTxn(blockNumberAndTxn.getTxn());
            blockChainInfo.setPendingTxn(pendingTxn);
            blockChainInfoMapper.add(blockChainInfo);
        }
    }

    /**
     * handleTxnByDay.
     */
    public void handleTxnByDay() {
        List<Group> list = groupService.getGroupList();
        for (Group loop : list) {
            statisticByGroupId(loop.getGroupId());
        }
    }

    /**
     * static transCount by groupId.
     */
    private void statisticByGroupId(Integer groupId) {
        // statistic latest transactionCount
        List<LatestTransCount> latestTransCountList =
                transactionMapper.queryLatestTransCount(groupId);
        if (CollectionUtils.isEmpty(latestTransCountList)) {
            log.debug("updateTransDailyData jump over, latestTransCountList is null,groupId:{} ",
                    groupId);
            return;
        }
        latestTransCountList.stream().forEach(ltc -> saveLatestTransCount(ltc, groupId));
    }

    /**
     * save latest transaction count.
     */
    private void saveLatestTransCount(LatestTransCount latestTransCount, Integer groupId) {
        TransactionByDay transactionByDay = new TransactionByDay();
        transactionByDay.setGroupId(groupId);
        BeanUtils.copyProperties(latestTransCount, transactionByDay);
        blockChainInfoMapper.addTxnByDay(transactionByDay);
    }

    /**
     * deleteTxnSchedule.
     */
    public void deleteTxnSchedule() {
        List<Group> list = groupService.getGroupList();
        for (Group loop : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("groupId", loop.getGroupId());
            int count = transactionMapper.getAllTransactionCount(map);
            if (count > constants.getKeepTxnCnt()) {
                int subTransNum = count - constants.getKeepTxnCnt();
                transactionMapper.deletePartTxn(loop.getGroupId(), subTransNum);
            }
        }
    }

    /**
     * syncNodeInfo.
     */
    public void syncNodeInfo() {
        List<Group> list = groupService.getGroupList();
        for (Group group : list) {
            int groupId = group.getGroupId();
            // sync node
            SyncInfoFromChain syncInfo = web3jRpc.getSyncInfo(groupId);
            if (syncInfo != null) {
                for (Peer peer : syncInfo.getPeers()) {
                    Node syncNode = new Node();
                    syncNode.setNodeId(peer.getNodeId());
                    syncNode.setGroupId(groupId);
                    syncNode.setType(NodeSyncType.AUTO.getValue());
                    nodeMapper.sync(syncNode);
                }
            }
            // check if node is abandoned
            List<Node> nodeList = nodeMapper.getAllNode(groupId);
            List<String> nodeIds = web3jRpc.getGroupPeers(groupId);
            for (Node loop : nodeList) {
                if (nodeIds.size() > 0 && !nodeIds.contains(loop.getNodeId())) {
                    nodeMapper.deleteNodeById(groupId, loop.getNodeId());
                }
            }
        }
    }

    /**
     * checkNodeActive.
     */
    public void checkNodeActive() {
        List<Group> list = groupService.getGroupList();
        for (Group group : list) {
            int groupId = group.getGroupId();
            List<Node> nodeList = nodeMapper.getAllNode(groupId);

            SyncInfoFromChain syncInfo = web3jRpc.getSyncInfo(groupId);
            log.debug("checkNodeActive syncInfo:{}", syncInfo);
            List<Peer> consensusInfo = web3jRpc.getConsensusInfo(groupId);
            log.debug("checkNodeActive consensusInfo:{}", consensusInfo);
            List<String> observerList = web3jRpc.getObserverList(groupId);

            for (Node loop : nodeList) {
                int blockNumber = 0;
                int pbftView = 0;
                String nodeId = loop.getNodeId();

                if (syncInfo != null) {
                    if (nodeId.equals(syncInfo.getNodeId())) {
                        blockNumber = syncInfo.getBlockNumber();
                    } else {
                        for (Peer peer : syncInfo.getPeers()) {
                            if (nodeId.equals(peer.getNodeId())) {
                                blockNumber = peer.getBlockNumber();
                                break;
                            }
                        }
                    }
                }
                for (Peer peer : consensusInfo) {
                    if (nodeId.equals(peer.getNodeId())) {
                        pbftView = peer.getView();
                        break;
                    }
                }

                int nodeType = NodeType.CONSENSUS.getValue(); // 0-consensus;1-observer
                if (observerList != null) {
                    nodeType = observerList.stream().filter(observer -> observer.equals(nodeId))
                            .map(c -> NodeType.OBSERVER.getValue()).findFirst().orElse(0);
                }

                // update node status
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("nodeId", nodeId);
                map.put("groupId", groupId);
                map.put("pbftView", pbftView);
                if (blockNumber == 0) {
                    blockNumber = loop.getBlockNumber();
                }
                map.put("blockNumber", blockNumber);
                if (nodeType == NodeType.CONSENSUS.getValue()) {
                    if (blockNumber == loop.getBlockNumber() && pbftView == loop.getPbftView()) {
                        map.put("status", NodeStatus.INVALID.getValue());
                    } else {
                        map.put("status", NodeStatus.NORMAL.getValue());
                    }
                } else {
                    if (blockNumber != web3jRpc.getBlockNumber(groupId)) {
                        map.put("status", NodeStatus.INVALID.getValue());
                    } else {
                        map.put("status", NodeStatus.NORMAL.getValue());
                    }
                }
                nodeMapper.updateStatus(map);
            }
        }
    }

    /**
     * syncChainUser
     */
    public void syncChainUser() {
        log.debug("==========syncChainUser begin=============");
        List<Group> list = groupService.getGroupList();
        for (Group group : list) {
            List<Transaction> transFrom = transactionMapper.getTransFormByGroup(group.getGroupId());
            log.info("syncChainUser groupId: {}, size: {}", group.getGroupId(), transFrom.size());
            transFrom.stream().forEach(ts -> saveChainUser(ts, group.getGroupId()));
        }
        log.debug("==========syncChainUser end=============");
    }

    /**
     * saveChainUser
     * 
     * @param transaction
     * @param groupId
     */
    public void saveChainUser(Transaction transaction, int groupId) {
        ChainUser chainUser = new ChainUser();
        chainUser.setAddress(transaction.getTransFrom());
        chainUser.setGroupId(groupId);
        chainUserMapper.addChainUser(chainUser);
    }

    /**
     * syncChainContract
     */
    public void syncChainContract() {
        log.debug("==========syncChainContract begin=============");
        List<Group> list = groupService.getGroupList();
        for (Group group : list) {
            List<Transaction> transactions =
                    transactionMapper.getTbTransactionByGroup(group.getGroupId());
            log.info("syncChainContract groupId: {}, size: {}", group.getGroupId(),
                    transactions.size());
            transactions.stream().forEach(ts -> saveChainContract(ts, group.getGroupId()));
        }
        log.debug("==========syncChainContract end=============");
    }

    /**
     * saveChainContract
     * 
     * @param ts
     * @param groupId
     */
    public void saveChainContract(Transaction ts, int groupId) {
        ChainContract chainContract = new ChainContract();
        chainContract.setGroupId(groupId);
        chainContract.setBlockHeight(ts.getBlockNumber());
        chainContract.setContractAddress(ts.getContractAddress());
        chainContract.setTransFrom(ts.getTransFrom());
        chainContract.setTransHash(ts.getTransHash());
        chainContract.setBlockTime(ts.getBlockTime());
        chainContractMapper.addChainContract(chainContract);
    }
}
