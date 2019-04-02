package org.bcos.browser.base;

public interface ConstantCode {
    /* return success */
    RetCode SUCCESS = RetCode.mark(0, "success");

    /* parameter check */
    String GROUP_NAME_IS_EMPTY = "{\"code\":205001,\"msg\":\"group name cannot be empty\"}";
    String GROUP_ID_IS_EMPTY = "{\"code\":205002,\"msg\":\"group id cannot be empty\"}";
    String NODE_ID_IS_EMPTY = "{\"code\":205003,\"msg\":\"node id cannot be empty\"}";
    String NODE_IP_IS_EMPTY = "{\"code\":205004,\"msg\":\"node ip cannot be empty\"}";
    String NODE_RPCPORT_IS_EMPTY = "{\"code\":205005,\"msg\":\"node rpcPort cannot be empty\"}";
    String NODE_P2PPORT_IS_EMPTY = "{\"code\":205006,\"msg\":\"node p2pPort cannot be empty\"}";


    /* general error */
    RetCode GROUP_ID_IS_EXISTED = RetCode.mark(305001, "group id is existed");
    RetCode GROUP_NAME_IS_EXISTED = RetCode.mark(305002, "group name is existed");
    RetCode RPCPORT_P2PPORT_SAME = RetCode.mark(305003, "rpcPort and p2pPort cannot be same");
    RetCode RPCPORT_IS_EXISTED = new RetCode(305004, "ip and rpcPort is existed in this group");
    RetCode P2PPORT_IS_EXISTED = new RetCode(305005, "ip and p2pPort is existed in this group");
    RetCode IP_FORMAT_ERROR = new RetCode(305006, "ip format error");
    RetCode NODE_ERROR_OR_NOT_ACTIVE = new RetCode(305007, "node error or not alive");
    RetCode FILE_TYPE_ERROR = new RetCode(305008,"file type error");
    RetCode EMPETY_FILE_ERROR = new RetCode(305009,"The file is empety!");
    RetCode NODE_NO_NOT_BELONG = new RetCode(305008, "node do not belong to this group");
    RetCode NOT_A_ZIP_FILE = new RetCode(305010,"not a zip file");
    /* system error */
    RetCode SYSTEM_ERROR = RetCode.mark(105001, "system error");
    RetCode PARAM_VAILD_FAIL = RetCode.mark(105002, "param valid fail");
}
