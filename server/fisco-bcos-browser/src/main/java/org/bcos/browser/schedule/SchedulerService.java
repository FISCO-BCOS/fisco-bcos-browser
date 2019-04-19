package org.bcos.browser.schedule;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.bcos.browser.base.Constants;
import org.bcos.browser.entity.dto.Block;
import org.bcos.browser.entity.dto.BlockChainInfo;
import org.bcos.browser.entity.dto.BlockFromChain;
import org.bcos.browser.entity.dto.BlockNumberAndTxn;
import org.bcos.browser.entity.dto.Group;
import org.bcos.browser.entity.dto.Node;
import org.bcos.browser.entity.dto.Peer;
import org.bcos.browser.entity.dto.SyncInfoFromChain;
import org.bcos.browser.entity.dto.Transaction;
import org.bcos.browser.entity.dto.TransactionFromChain;
import org.bcos.browser.mapper.BlockChainInfoMapper;
import org.bcos.browser.mapper.BlockMapper;
import org.bcos.browser.mapper.GroupMapper;
import org.bcos.browser.mapper.NodeMapper;
import org.bcos.browser.mapper.TransactionMapper;
import org.bcos.browser.util.CommonUtils;
import org.bcos.browser.util.Web3jRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class SchedulerService {
    @Autowired
    Web3jRpc web3jRpc;
    @Autowired
    SchedulerService schedulerService;
    @Autowired
    GroupMapper groupMapper;
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

    /**
     * handleBlockChainInfo.
     */
    public void handleBlockChainInfo() {
        List<Group> list = groupMapper.getGroupList();
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
     * handleBlocks.
     */
    public void handleBlocks() {
        List<Group> list = groupMapper.getGroupList();
        for (Group loop : list) {
            int groupId = loop.getGroupId();
            int latestNumber = blockChainInfoMapper.getBlockNumber(groupId);
            int maxNumber = blockMapper.getMaxBlockNumber(groupId);
            // check if block number change
            if (latestNumber == 0 || latestNumber == maxNumber) {
                continue;
            }
            // per task handle blocks
            int diffNumber = latestNumber - maxNumber;
            if (diffNumber > constants.getHandleBlocks()) {
                latestNumber = maxNumber + constants.getHandleBlocks();
            }
            for (int i = maxNumber + 1; i <= latestNumber; i++) {
                if (i == 1) {
                    schedulerService.handleBlockInfo(groupId, 0);
                }
                schedulerService.handleBlockInfo(groupId, i);
            }
        }
    }


    /**
     * handleBlockInfo.
     * 
     * @param groupId groupId
     * @param number block number
     */
    @Transactional
    public void handleBlockInfo(int groupId, int number) {
        BlockFromChain blockInfo = web3jRpc.getBlockByNumber(groupId, number);
        if (blockInfo == null) {
            return;
        }

        Timestamp timestamp = null;
        String sealer = "0x0";
        // Compatibility with older version
        if (number == 0 && "0xffffffffffffffff".equals(blockInfo.getTimestamp())) {
            timestamp = Timestamp.valueOf("2000-01-01 08:00:01");
        } else {
            timestamp = new Timestamp(Long.parseLong(blockInfo.getTimestamp().substring(2), 16));
        }
        if (number != 0) {
            sealer = blockInfo.getSealerList().get(
                    CommonUtils.parseHexStr2Int(blockInfo.getSealer()));
        }
        // add transaction info
        List<TransactionFromChain> transList = blockInfo.getTransactions();
        for (TransactionFromChain loop : transList) {
            Transaction transaction = new Transaction();
            transaction.setTransHash(loop.getHash());
            transaction.setGroupId(groupId);
            transaction.setBlockHash(blockInfo.getHash());
            transaction.setBlockNumber(CommonUtils.parseHexStr2Int(blockInfo.getNumber()));
            transaction.setBlockTime(timestamp);
            transaction.setBlockDate(timestamp);
            transaction.setTransFrom(loop.getFrom());
            transaction.setTransTo(loop.getTo());
            transaction.setTransIndex(CommonUtils.parseHexStr2Int(loop.getTransactionIndex()));
            transactionMapper.add(transaction);
        }
        // add block info
        Block block = new Block();
        block.setBlockHash(blockInfo.getHash());
        block.setGroupId(groupId);
        block.setNumber(number);
        block.setSealer(sealer);
        block.setBlockTime(timestamp);
        block.setTxn(transList.size());
        blockMapper.add(block);
    }

    /**
     * handleTxnByDay.
     */
    public void handleTxnByDay() {
        List<Group> list = groupMapper.getGroupList();
        for (Group loop : list) {
            blockChainInfoMapper.addTxnByDay(loop.getGroupId());
        }
    }

    /**
     * deleteTxnSchedule.
     */
    public void deleteTxnSchedule() {
        List<Group> list = groupMapper.getGroupList();
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
        List<Group> list = groupMapper.getGroupList();
        for (Group group : list) {
            int groupId = group.getGroupId();
            // sync node
            SyncInfoFromChain syncInfo = web3jRpc.getSyncInfo(groupId);
            if (syncInfo != null) {
                for (Peer peer: syncInfo.getPeers()) {
                    Node syncNode = new Node();
                    syncNode.setNodeId(peer.getNodeId());
                    syncNode.setGroupId(groupId);
                    syncNode.setType(1);
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
        List<Group> list = groupMapper.getGroupList();
        for (Group group : list) {
            int groupId = group.getGroupId();
            List<Node> nodeList = nodeMapper.getAllNode(groupId);
            
            SyncInfoFromChain syncInfo = web3jRpc.getSyncInfo(groupId);
            log.debug("checkNodeActive syncInfo:{}", syncInfo);
            List<Peer> consensusInfo = web3jRpc.getConsensusInfo(groupId);
            log.debug("checkNodeActive consensusInfo:{}", consensusInfo);
            
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
                // update node status
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("nodeId", nodeId);
                map.put("groupId", groupId);
                map.put("pbftView", pbftView);
                if (blockNumber == 0) {
                    blockNumber = loop.getBlockNumber();
                }
                map.put("blockNumber", blockNumber);
                if (blockNumber == loop.getBlockNumber() && pbftView == loop.getPbftView()) {
                    map.put("status", 1);
                } else {
                    map.put("status", 0);
                }
                nodeMapper.updateStatus(map);
            }
        }
    }
}
