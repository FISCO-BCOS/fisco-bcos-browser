package org.bcos.browser.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.exception.BaseException;
import org.bcos.browser.entity.base.BasePageResponse;
import org.bcos.browser.entity.base.BaseResponse;
import org.bcos.browser.entity.dto.Node;
import org.bcos.browser.entity.dto.Peer;
import org.bcos.browser.entity.dto.SyncInfoFromChain;
import org.bcos.browser.entity.req.ReqAddNode;
import org.bcos.browser.entity.req.ReqAddNodeInfo;
import org.bcos.browser.mapper.NodeMapper;
import org.bcos.browser.schedule.SchedulerService;
import org.bcos.browser.util.CommonUtils;
import org.bcos.browser.util.Web3jRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class NodeService {
    @Autowired
    Web3jRpc web3jRpc;
    @Autowired
    NodeMapper nodeMapper;
    @Autowired
    SchedulerService schedulerService;

    /**
     * addNode.
     * 
     * @param reqAddNode node info
     * @return
     */
    @Transactional(rollbackFor = BaseException.class)
    public BaseResponse addNode(ReqAddNode reqAddNode) throws BaseException {
        log.info("addNode reqAddNode:{}", reqAddNode);
        BaseResponse response = new BaseResponse(ConstantCode.SUCCESS);
        List<ReqAddNodeInfo> data = reqAddNode.getData();
        int groupId = reqAddNode.getGroupId();

        for (int i = 0; i < data.size(); i++) {
            if (!CommonUtils.isIp(data.get(i).getIp())) {
                log.error("addNode fail. ipStr:{}", data.get(i).getIp());
                throw new BaseException(ConstantCode.IP_FORMAT_ERROR);
            }

            if (data.get(i).getRpcPort().equals(data.get(i).getP2pPort())) {
                throw new BaseException(ConstantCode.RPCPORT_P2PPORT_SAME);
            }

            List<Node> nodeList = nodeMapper.getAllNode(groupId);
            for (int j = 0; j < nodeList.size(); j++) {
                if (data.get(i).getIp().equals(nodeList.get(j).getIp())
                        && data.get(i).getP2pPort().equals(nodeList.get(j).getP2pPort())) {
                    throw new BaseException(ConstantCode.P2PPORT_IS_EXISTED);
                } else if (data.get(i).getIp().equals(nodeList.get(j).getIp())
                        && data.get(i).getRpcPort().equals(nodeList.get(j).getRpcPort())) {
                    throw new BaseException(ConstantCode.RPCPORT_IS_EXISTED);
                }
            }

            Node node = new Node();
            node.setGroupId(groupId);
            node.setIp(data.get(i).getIp());
            node.setRpcPort(data.get(i).getRpcPort());
            node.setP2pPort(data.get(i).getP2pPort());

            List<Integer> groups = web3jRpc.getGroupList(node);
            if (groups == null || groups.size() == 0) {
                throw new BaseException(ConstantCode.NODE_ERROR_OR_NOT_ACTIVE);
            }
            if (!groups.contains(reqAddNode.getGroupId())) {
                throw new BaseException(ConstantCode.NODE_NO_NOT_BELONG);
            }

            SyncInfoFromChain syncInfo = web3jRpc.getSyncInfo(groupId, node);
            node.setNodeId(syncInfo.getNodeId());
            node.setType(0);
            nodeMapper.add(node);
            
            // sync node info
            for (Peer peer: syncInfo.getPeers()) {
                Node syncNode = new Node();
                syncNode.setNodeId(peer.getNodeId());
                syncNode.setGroupId(groupId);
                syncNode.setType(1);
                nodeMapper.sync(syncNode);
            }
        }
        return response;
    }

    /**
     * getNodeList.
     * 
     * @param groupId groupId
     * @param pageNumber pageNumber
     * @param pageSize pageSize
     * @param type node type
     * @param ip node ip
     * @param rpcPort node rpcPort
     * @param p2pPort node p2pPort
     * @return
     */
    public BasePageResponse getNodeList(int groupId, int pageNumber, int pageSize, 
            int type, String ip, String rpcPort, String p2pPort) {
        int start =
                Optional.ofNullable(pageNumber).map(page -> (page - 1) * pageSize).orElse(null);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("groupId", groupId);
        map.put("type", type);
        map.put("ip", CommonUtils.trimSpaces(ip));
        map.put("rpcPort", CommonUtils.trimSpaces(rpcPort));
        map.put("p2pPort", CommonUtils.trimSpaces(p2pPort));
        map.put("start", start);
        map.put("pageSize", pageSize);

        int total = nodeMapper.getNodeCnts(map);
        List<Node> list = new ArrayList<>();
        if (total > 0) {
            list = nodeMapper.getNodeListByPage(map);
        }

        BasePageResponse response = new BasePageResponse(ConstantCode.SUCCESS);
        response.setTotalCount(total);
        response.setData(list);
        return response;
    }

    /**
     * deleteNodeById.
     * 
     * @param nodeId nodeId
     * @return
     */
    public BaseResponse deleteNodeById(int groupId, String nodeId) {
        BaseResponse response = new BaseResponse(ConstantCode.SUCCESS);
        nodeMapper.updateToSync(groupId, nodeId);
        return response;
    }
}
