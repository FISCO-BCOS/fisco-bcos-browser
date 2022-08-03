package org.bcos.browser.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.Constants;
import org.bcos.browser.base.enums.NodeStatus;
import org.bcos.browser.base.enums.NodeSyncType;
import org.bcos.browser.base.exception.BaseException;
import org.bcos.browser.entity.base.BasePageResponse;
import org.bcos.browser.entity.base.BaseResponse;
import org.bcos.browser.entity.dto.Group;
import org.bcos.browser.entity.dto.Node;
import org.bcos.browser.entity.dto.Peer;
import org.bcos.browser.entity.dto.SyncInfoFromChain;
import org.bcos.browser.entity.req.ReqAddNode;
import org.bcos.browser.entity.req.ReqAddNodeInfo;
import org.bcos.browser.mapper.NodeMapper;
import org.bcos.browser.schedule.SchedulerService;
import org.bcos.browser.util.CommonUtils;
import org.bcos.browser.util.JsonTools;
import org.bcos.browser.util.Web3jRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.JsonNode;
import com.webank.blockchain.data.export.ExportDataSDK;
import com.webank.blockchain.data.export.common.entity.ChainInfo;
import com.webank.blockchain.data.export.common.entity.ExportConfig;
import com.webank.blockchain.data.export.common.entity.ExportDataSource;
import com.webank.blockchain.data.export.common.entity.MysqlDataSource;
import com.webank.blockchain.data.export.common.enums.IgnoreBasicDataParam;
import com.webank.blockchain.data.export.task.DataExportExecutor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NodeService {
    @Autowired
    Web3jRpc web3jRpc;
    @Autowired
    NodeMapper nodeMapper;
    @Autowired
    SchedulerService schedulerService;
    @Autowired
    GroupService groupService;
    @Autowired
    private TableService tableService;

    public static Map<String, DataExportExecutor> DATA_EXPORT_MAP = new ConcurrentHashMap<>();

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

        // check group id
        groupService.checkGroupId(groupId);

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

            List<Integer> groupIds = web3jRpc.getGroupList(node);
            if (groupIds == null || groupIds.size() == 0) {
                throw new BaseException(ConstantCode.NODE_ERROR_OR_NOT_ACTIVE);
            }
            if (!groupIds.contains(reqAddNode.getGroupId())) {
                throw new BaseException(ConstantCode.NODE_NO_NOT_BELONG);
            }
            for (int k = 0; k < groupIds.size(); k++) {
                Group group = groupService.getGroupById(groupIds.get(k));
                if (group != null) {
                    SyncInfoFromChain syncInfo = web3jRpc.getSyncInfo(groupIds.get(k), node);
                    node.setNodeId(syncInfo.getNodeId());
                    node.setGroupId(groupIds.get(k));
                    node.setType(NodeSyncType.MANUAL.getValue());
                    nodeMapper.add(node);
                    // sync node info
                    for (Peer peer : syncInfo.getPeers()) {
                        Node syncNode = new Node();
                        syncNode.setNodeId(peer.getNodeId());
                        syncNode.setGroupId(groupIds.get(k));
                        syncNode.setType(NodeSyncType.AUTO.getValue());
                        nodeMapper.sync(syncNode);
                    }
                    // add data export
                    dataExportStart(node);
                }
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
    public BasePageResponse getNodeList(int groupId, int pageNumber, int pageSize, int type,
            String ip, String rpcPort, String p2pPort) {
        int start = Optional.ofNullable(pageNumber).map(page -> (page - 1) * pageSize).orElse(null);

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
        // stop data export
        dataExportStop(groupId, nodeId);
        nodeMapper.updateToSync(groupId, nodeId);
        return response;
    }

    /**
     * getEncryptType.
     *
     * @param groupId groupId
     * @return
     */
    public BaseResponse getEncryptType(int groupId) {
        BaseResponse response = new BaseResponse(ConstantCode.SUCCESS);
        Object object = web3jRpc.getClientVersion(groupId);
        int encryptType = 0;
        if (object != null) {
            JsonNode jsonNode = JsonTools.stringToJsonNode(JsonTools.toJSONString(object));
            String version = jsonNode.get("FISCO-BCOS Version").asText();
            if (version.contains("gm")) {
                encryptType = 1;
            }
        }
        response.setData(encryptType);
        return response;
    }

    /**
     * dataExportInit.
     */
    public void dataExportInit() {
        List<Group> list = groupService.getGroupList();
        for (Group group : list) {
            int groupId = group.getGroupId();
            List<Node> nodeList = nodeMapper.getManualNode(groupId);
            for (Node node : nodeList) {
                if (node.getStatus() == NodeStatus.NORMAL.getValue()) {
                    dataExportStart(node);
                }
            }
        }
    }

    /**
     * dataExportStart.
     */
    public void dataExportStart(Node node) {
        int groupId = node.getGroupId();
        String key = groupId + "_" + node.getNodeId();
        DataExportExecutor exportExecutor = DATA_EXPORT_MAP.get(key);
        if (exportExecutor != null) {
            return;
        }
        MysqlDataSource mysqlDataSourc = MysqlDataSource.builder().jdbcUrl(tableService.getDbUrl())
                .user(tableService.getDbUser()).pass(tableService.getDbPwd()).build();
        List<MysqlDataSource> mysqlDataSourceList = new ArrayList<>();
        mysqlDataSourceList.add(mysqlDataSourc);
        ExportDataSource dataSource = ExportDataSource.builder()
                .mysqlDataSources(mysqlDataSourceList).autoCreateTable(true).build();
        ExportConfig exportConfig = new ExportConfig();
        exportConfig.setTablePostfix("_" + groupId);
        Map<String, List<String>> ignoreBasicDataTableParam = new HashMap<>();
        List<String> ignoreBlockRawData = new ArrayList<>();

        ignoreBlockRawData.add(IgnoreBasicDataParam.BlockRawDataParams.DB_HASH.name());
        ignoreBlockRawData.add(IgnoreBasicDataParam.BlockRawDataParams.EXTRA_DATA.name());
        ignoreBlockRawData.add(IgnoreBasicDataParam.BlockRawDataParams.GAS_LIMIT.name());
        ignoreBlockRawData.add(IgnoreBasicDataParam.BlockRawDataParams.GAS_USED.name());
        ignoreBlockRawData.add(IgnoreBasicDataParam.BlockRawDataParams.LOGS_BLOOM.name());
        ignoreBlockRawData.add(IgnoreBasicDataParam.BlockRawDataParams.PARENT_HASH.name());
        ignoreBlockRawData.add(IgnoreBasicDataParam.BlockRawDataParams.RECEIPTS_ROOT.name());
        ignoreBlockRawData.add(IgnoreBasicDataParam.BlockRawDataParams.SEALER.name());
        ignoreBlockRawData.add(IgnoreBasicDataParam.BlockRawDataParams.STATE_ROOT.name());
        ignoreBlockRawData.add(IgnoreBasicDataParam.BlockRawDataParams.SIGNATURE_LIST.name());
        ignoreBlockRawData.add(IgnoreBasicDataParam.BlockRawDataParams.TRANSACTION_LIST.name());
        ignoreBlockRawData.add(IgnoreBasicDataParam.BlockRawDataParams.TRANSACTIONS_ROOT.name());

        List<String> ignoreTxReceiptRawData = new ArrayList<>();
        ignoreTxReceiptRawData.add(IgnoreBasicDataParam.TxReceiptRawDataParams.GAS_USED.name());
        ignoreTxReceiptRawData.add(IgnoreBasicDataParam.TxReceiptRawDataParams.LOGS.name());
        ignoreTxReceiptRawData.add(IgnoreBasicDataParam.TxReceiptRawDataParams.INPUT.name());
        ignoreTxReceiptRawData.add(IgnoreBasicDataParam.TxReceiptRawDataParams.MESSAGE.name());
        ignoreTxReceiptRawData.add(IgnoreBasicDataParam.TxReceiptRawDataParams.OUTPUT.name());
        ignoreTxReceiptRawData.add(IgnoreBasicDataParam.TxReceiptRawDataParams.LOGS_BLOOM.name());
        ignoreTxReceiptRawData.add(IgnoreBasicDataParam.TxReceiptRawDataParams.ROOT.name());
        ignoreTxReceiptRawData.add(IgnoreBasicDataParam.TxReceiptRawDataParams.TX_INDEX.name());
        ignoreTxReceiptRawData.add(IgnoreBasicDataParam.TxReceiptRawDataParams.TX_PROOF.name());
        ignoreTxReceiptRawData.add(IgnoreBasicDataParam.TxReceiptRawDataParams.RECEIPT_PROOF.name());

        List<String> ignoreTxRawData = new ArrayList<>();
        ignoreTxRawData.add(IgnoreBasicDataParam.TxRawDataParams.GAS.name());
        ignoreTxRawData.add(IgnoreBasicDataParam.TxRawDataParams.GAS_PRICE.name());
        ignoreTxRawData.add(IgnoreBasicDataParam.TxRawDataParams.INPUT.name());
        ignoreTxRawData.add(IgnoreBasicDataParam.TxRawDataParams.NONCE.name());
        ignoreTxRawData.add(IgnoreBasicDataParam.TxRawDataParams.VALUE.name());

        ignoreBasicDataTableParam.put(IgnoreBasicDataParam.IgnoreBasicDataTable.BLOCK_RAW_DATA_TABLE.name(),ignoreBlockRawData);
        ignoreBasicDataTableParam.put(IgnoreBasicDataParam.IgnoreBasicDataTable.TX_RAW_DATA_TABLE.name(),ignoreTxRawData);
        ignoreBasicDataTableParam.put(IgnoreBasicDataParam.IgnoreBasicDataTable.TX_RECEIPT_RAW_DATA_TABLE.name(),ignoreTxReceiptRawData);
        exportConfig.setIgnoreBasicDataTableParam(ignoreBasicDataTableParam);
        try {
            exportExecutor =
                    ExportDataSDK.create(dataSource,
                            ChainInfo.builder()
                                    .rpcUrl(String.format(Constants.RPC_BASE_URI, node.getIp(),
                                            node.getRpcPort()))
                                    .groupId(groupId).build(),
                            exportConfig);
            ExportDataSDK.start(exportExecutor);
            DATA_EXPORT_MAP.put(key, exportExecutor);
        } catch (Exception e) {
            log.error("dataExportStart key:{} error:{}", key, e);
        }
    }

    /**
     * dataExportStopByGroupId.
     * 
     * @param groupId
     */
    public void dataExportStopByGroupId(int groupId) {
        List<Node> nodeList = nodeMapper.getManualNode(groupId);
        for (Node node : nodeList) {
            dataExportStop(groupId, node.getNodeId());
        }
    }

    /**
     * dataExportStop.
     * 
     * @param groupId
     * @param nodeId
     */
    public void dataExportStop(int groupId, String nodeId) {
        String key = groupId + "_" + nodeId;
        DataExportExecutor exportExecutor = DATA_EXPORT_MAP.get(key);
        if (exportExecutor == null) {
            return;
        }
        ExportDataSDK.stop(exportExecutor);
        DATA_EXPORT_MAP.remove(key);
    }
}
