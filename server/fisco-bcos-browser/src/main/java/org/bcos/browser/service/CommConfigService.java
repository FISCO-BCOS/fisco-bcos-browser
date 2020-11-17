package org.bcos.browser.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.Constants;
import org.bcos.browser.base.exception.BaseException;
import org.bcos.browser.entity.base.BasePageResponse;
import org.bcos.browser.entity.base.BaseResponse;
import org.bcos.browser.entity.dto.*;
import org.bcos.browser.entity.req.ReqAddNode;
import org.bcos.browser.entity.req.ReqAddNodeInfo;
import org.bcos.browser.mapper.GroupMapper;
import org.bcos.browser.mapper.NodeMapper;
import org.bcos.browser.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class CommConfigService {
    @Autowired
    GroupService groupService;
    @Autowired
    NodeService nodeService;
    @Autowired
    GroupMapper groupMapper;
    @Autowired
    NodeMapper nodeMapper;
    @Autowired
    FileUtils fileUtils;

    /**
     * getWebConfig
     */
    public BaseResponse getWebConfig(String rootPath) throws BaseException {
        File webConfigFile = fileUtils.createFile(rootPath, "config.json", Constants.WEB_CONFIG_EXAM);
        if (webConfigFile == null) {
            throw new BaseException(ConstantCode.SYSTEM_ERROR);
        }
        WebConfig webConfig = readWebConfig(webConfigFile);
        return new BaseResponse(ConstantCode.SUCCESS, webConfig);
    }

    /**
     * updateGroupAndNode
     */
    @Transactional(rollbackFor = BaseException.class)
    public BaseResponse updateGroupAndNode(String rootPath) throws BaseException {
        File groupFile = fileUtils.createFile(rootPath, "group.json", Constants.GROUP_CONFIG_EXAM);
        File nodeFile = fileUtils.createFile(rootPath, "node.json", Constants.NODE_CONFIG_EXAM);
        if (groupFile == null || nodeFile == null) {
            throw new BaseException(ConstantCode.SYSTEM_ERROR);
        }
        List<Group> groupList = readGroupConfig(groupFile);
        //第一次部署或无群组 就不用更新群组、节点
        if (!groupList.isEmpty()) {
            updateGroup(groupList);
            updateNode(readNodeConfig(nodeFile));
        }
        return new BaseResponse(ConstantCode.SUCCESS);
    }

    /**
     * readWebConfig
     */
    private WebConfig readWebConfig(File file) {
        String webName = "";
        try {
            ObjectMapper objMap = new ObjectMapper();
            JsonNode root = objMap.readTree(file);
            webName = root.path("webName").asText();
        } catch (IOException e) {
            log.warn("readWebConfig", e);
        }
        return new WebConfig(webName);
    }

    /**
     * readGroupConfig
     */
    private List<Group> readGroupConfig(File file) {
        ObjectMapper objMap = new ObjectMapper();
        List<Group> groupList = new ArrayList<>();
        try {
            JsonNode root = objMap.readTree(file);
            for (int i = 0; i < root.size(); i++) {
                JsonNode node = root.path(i);
                int groupId = node.path("groupId").asInt();
                String groupName = node.path("groupName").asText();
                String groupDesc = node.path("groupDesc").asText();
                if (groupId != 0 && !groupName.isEmpty()) {
                    groupList.add(new Group(groupId, groupName, groupDesc));
                }
            }
        } catch (IOException e) {
            log.warn("readGroupConfig", e);
        }
        return groupList;
    }

    /**
     * updateGroup
     */
    private void updateGroup(List<Group> groupList) throws BaseException {
        List<Group> dbGroupList = groupMapper.getGroupList();
        if (dbGroupList != null) {
            for (Group group : dbGroupList) {
                boolean isExists = false;
                for (Group group1 : groupList) {
                    if (group.getGroupId() == group1.getGroupId()) {
                        isExists = true;
                        break;
                    }
                }
                if (!isExists) {
                    groupService.deleteGroup(group.getGroupId());
                }
            }
        }
        //
        for (Group group : groupList) {
            Group groupById = groupMapper.getGroupById(group.getGroupId());
            if (groupById == null) {
                //添加群组
                groupService.addGroup(group);
            } else {
                if (!groupById.getGroupName().equals(group.getGroupName())
                        || !groupById.getGroupDesc().equals(group.getGroupDesc())) {
                    groupService.deleteGroup(groupById.getGroupId());
                    groupService.addGroup(group);
                }
            }
        }
    }

    /**
     * readNodeConfig
     */
    private List<ReqAddNode> readNodeConfig(File file) {
        ObjectMapper objMap = new ObjectMapper();
        List<ReqAddNode> nodeList = new ArrayList<>();
        try {
            JsonNode root = objMap.readTree(file);
            for (int i = 0; i < root.size(); i++) {
                ReqAddNode reqAddNode = new ReqAddNode();
                JsonNode node = root.path(i);
                int groupId = node.path("groupId").asInt();
                reqAddNode.setGroupId(groupId);
                JsonNode data = node.path("data");
                List<ReqAddNodeInfo> dataList = new ArrayList<>();
                for (int j = 0; j < data.size(); j++) {
                    JsonNode node1 = data.path(j);
                    String ip = node1.path("ip").asText();
                    String rpcPort = node1.path("rpcPort").asText();
                    String p2pPort = node1.path("p2pPort").asText();
                    dataList.add(new ReqAddNodeInfo(ip, rpcPort, p2pPort));
                }
                reqAddNode.setData(dataList);
                nodeList.add(reqAddNode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nodeList;
    }

    /**
     * updateNode
     */
    private void updateNode(List<ReqAddNode> nodeList) throws BaseException {
        for (ReqAddNode reqAddNode : nodeList) {
            List<Node> nodes = nodeMapper.getManualNode(reqAddNode.getGroupId());
            for (Node node : nodes) {
                boolean isExists = false;
                for (ReqAddNodeInfo nodeInfo : reqAddNode.getData()) {
                    if (node.getIp() != null
                            && node.getRpcPort() != null
                            && node.getP2pPort() != null
                            && node.getIp().equals(nodeInfo.getIp())
                            && node.getRpcPort().equals(nodeInfo.getRpcPort())
                            && node.getP2pPort().equals(nodeInfo.getP2pPort())) {
                        isExists = true;
                        break;
                    }
                }
                if (!isExists) {
                    nodeService.deleteNodeById(node.getGroupId(), node.getNodeId());
                }
            }
            for (ReqAddNodeInfo nodeInfo : reqAddNode.getData()) {
                BasePageResponse basePageResponse1 = nodeService.getNodeList(reqAddNode.getGroupId(), 1, 1, 0, nodeInfo.getIp(), nodeInfo.getRpcPort(), nodeInfo.getP2pPort());
                if (basePageResponse1.getTotalCount() <= 0) {
                    List<ReqAddNodeInfo> nodeInfos = new ArrayList<>();
                    nodeInfos.add(nodeInfo);
                    ReqAddNode dbAddNode = new ReqAddNode(reqAddNode.getGroupId(), nodeInfos);
                    nodeService.addNode(dbAddNode);
                }
            }
        }
    }
}
