package org.bcos.browser.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.bcos.browser.base.Constants;
import org.bcos.browser.entity.dto.BlockFromChain;
import org.bcos.browser.entity.dto.BlockNumberAndTxn;
import org.bcos.browser.entity.dto.Node;
import org.bcos.browser.entity.dto.Peer;
import org.bcos.browser.entity.dto.ReceiptFromChain;
import org.bcos.browser.entity.dto.SyncInfoFromChain;
import org.bcos.browser.entity.dto.TransactionFromChain;
import org.bcos.browser.mapper.NodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Web3jRpc {

    @Autowired
    NodeMapper nodeMapper;

    /**
     * rpcRequest.
     * 
     * @param groupId groupId
     * @param methodName rpc method
     * @param params params
     * @return
     */
    public Object rpcRequest(int groupId, String methodName, Object[] params) {
        Object object = null;

        List<Node> nodeList = nodeMapper.getManualNode(groupId);
        if (nodeList == null || nodeList.size() == 0) {
            log.warn("there are not manually added nodes");
            return null;
        }

        int nodeSize = nodeList.size();
        Random random = new Random();
        List<Integer> indexList = new ArrayList<>(nodeSize);

        while (true) {
            if (indexList.size() == nodeSize) {
                return object;
            }

            int index = random.nextInt(nodeSize);
            if (indexList.contains(index)) {
                continue;
            }

            Node node = nodeList.get(index);
            indexList.add(index);

            object = requestNode(methodName, params, node);
            if (object == null && indexList.size() < nodeSize) {
                continue;
            }
            return object;
        }
    }

    /**
     * requestNode.
     * 
     * @param methodName rpc method
     * @param params params
     * @param node info
     * @return
     */
    public Object requestNode(String methodName, Object[] params, Node node) {
        Object object = null;
        JsonRpcHttpClient client = null;

        String url = String.format(Constants.RPC_BASE_URI, node.getIp(), node.getRpcPort());
        log.debug("request url: {}", url);
        try {
            client = new JsonRpcHttpClient(new URL(url));
            client.setConnectionTimeoutMillis(5000);
            client.setReadTimeoutMillis(5000);
            object = client.invoke(methodName, params, Object.class);
        } catch (Throwable ex) {
            log.warn("fail request methodName:{} ip:{} rpcPort:{}", methodName, node.getIp(),
                    node.getRpcPort(), ex);
        }
        return object;
    }
    
    /**
     * getBlockNumber.
     * 
     * @param groupId groupId
     * @return
     */
    public int getBlockNumber(int groupId) {
        Object[] params = new Object[] {groupId};
        Object object = rpcRequest(groupId, Constants.GET_BLOCK_NUMBER, params);
        if (object != null) {
            return CommonUtils.parseHexStr2Int(object.toString());
        }
        return 0;
    }

    /**
     * getTxn.
     * 
     * @param groupId groupId
     * @return
     */
    public BlockNumberAndTxn getTxn(int groupId) {
        BlockNumberAndTxn result = new BlockNumberAndTxn();
        Object[] params = new Object[] {groupId};
        Object object = rpcRequest(groupId, Constants.GET_TOTAL_TRANSACTION_COUNT, params);
        if (object != null) {
            Map<String, Object> restult = JsonTools.toMap(object);
            result.setBlockNumber(CommonUtils.parseHexStr2Int(restult.get("blockNumber").toString()));
            result.setTxn(CommonUtils.parseHexStr2Int(restult.get("txSum").toString()));
        }
        return result;
    }

    /**
     * getBlockByNumber.
     * 
     * @param groupId groupId
     * @param number block number
     * @return
     */
    public BlockFromChain getBlockByNumber(int groupId, int number) {
        BlockFromChain result = null;
        Object[] params = new Object[] {groupId, CommonUtils.parseInt2HexStr(number), true};
        Object object = rpcRequest(groupId, Constants.GET_BLOCK_BY_NUMBER, params);
        if (object != null) {
            result = CommonUtils.object2JavaBean(object, BlockFromChain.class);
        }
        return result;
    }

    /**
     * getTransByHash.
     * 
     * @param groupId groupId
     * @param transHash transHash
     * @return
     */
    public TransactionFromChain getTransByHash(int groupId, String transHash) {
        TransactionFromChain result = null;
        Object[] params = new Object[] {groupId, transHash};
        Object object = rpcRequest(groupId, Constants.GET_TRANSACTION_BY_HASH, params);
        if (object != null) {
            result = CommonUtils.object2JavaBean(object, TransactionFromChain.class);
        }
        return result;
    }

    /**
     * getReceiptByHash.
     * 
     * @param groupId groupId
     * @param transHash transHash
     * @return
     */
    public ReceiptFromChain getReceiptByHash(int groupId, String transHash) {
        ReceiptFromChain result = null;
        Object[] params = new Object[] {groupId, transHash};
        Object object = rpcRequest(groupId, Constants.GET_TRANSACTION_RECEIPT, params);
        if (object != null) {
            result = CommonUtils.object2JavaBean(object, ReceiptFromChain.class);
        }
        return result;
    }

    /**
     * getCode.
     * 
     * @param groupId groupId
     * @param address address
     * @return
     */
    public String getCode(int groupId, String address) {
        Object[] params = new Object[] {groupId, address};
        String result = (String) rpcRequest(groupId, Constants.GET_CODE, params);
        return result;
    }

    /**
     * getSyncInfo.
     * 
     * @param groupId groupId
     * @param node node
     * @return
     */
    public SyncInfoFromChain getSyncInfo(int groupId, Node node) {
        SyncInfoFromChain result = null;
        Object[] params = new Object[] {groupId};
        Object object = requestNode(Constants.GET_SYNC_STATUS, params, node);
        if (object != null) {
            result = CommonUtils.object2JavaBean(object, SyncInfoFromChain.class);
        }
        return result;
    }

    /**
     * getSyncInfo.
     * 
     * @param groupId groupId
     * @return
     */
    public SyncInfoFromChain getSyncInfo(int groupId) {
        SyncInfoFromChain result = null;
        Object[] params = new Object[] {groupId};
        Object object = rpcRequest(groupId, Constants.GET_SYNC_STATUS, params);
        if (object != null) {
            result = CommonUtils.object2JavaBean(object, SyncInfoFromChain.class);
        }
        return result;
    }

    /**
     * getConsensusInfo.
     * 
     * @param groupId groupId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Peer> getConsensusInfo(int groupId) {
        List<Peer> result = new ArrayList<>();
        Object[] params = new Object[] {groupId};
        Object object = rpcRequest(groupId, Constants.GET_CONSENSUS_STATUS, params);
        List<Object> list = null;
        if (object != null) {
            list = CommonUtils.object2JavaBean(object, List.class);
            result = JsonTools.stringToObj(JsonTools.toJSONString(list.get(1)),
                    new TypeReference<List<Peer>>() {});
        }
        return result;
    }

    /**
     * getGroupPeers.
     * 
     * @param groupId groupId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<String> getGroupPeers(int groupId) {
        List<String> result = new ArrayList<>();
        Object[] params = new Object[] {groupId};
        Object object = rpcRequest(groupId, Constants.GET_GROUP_PEERS, params);
        if (object != null) {
            result = CommonUtils.object2JavaBean(object, List.class);
        }
        return result;
    }
    
    /**
     * getObserverList.
     * 
     * @param groupId groupId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<String> getObserverList(int groupId) {
        List<String> result = new ArrayList<>();
        Object[] params = new Object[] {groupId};
        Object object = rpcRequest(groupId, Constants.GET_OBSERVER_LIST, params);
        if (object != null) {
            result = CommonUtils.object2JavaBean(object, List.class);
        }
        return result;
    }

    /**
     * getGroupList.
     *
     * @param node info
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Integer> getGroupList(Node node) {
        List<Integer> result = new ArrayList<>();
        Object object = requestNode(Constants.GET_GROUP_LIST, null, node);
        if (object != null) {
            result = CommonUtils.object2JavaBean(object, List.class);
        }
        return result;
    }

    /**
     * getGroupPeers.
     * 
     * @param groupId groupId
     * @return
     */
    public Object getClientVersion(int groupId) {
        Object[] params = new Object[] {};
        Object object = rpcRequest(groupId, Constants.GET_CLIENT_VERSION, params);
        return object;
    }
}
