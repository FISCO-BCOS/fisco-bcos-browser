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
    String USER_ID_IS_EMPTY = "{\"code\":205007,\"msg\":\"user id cannot be empty\"}";
    String USERNAME_IS_EMPTY = "{\"code\":205008,\"msg\":\"user name cannot be empty\"}";
    String ADDRESSS_IS_EMPTY = "{\"code\":205009,\"msg\":\"address cannot be empty\"}";

    /* general error */
    RetCode GROUP_ID_IS_EXISTED = RetCode.mark(305001, "group id is existed");
    RetCode GROUP_NAME_IS_EXISTED = RetCode.mark(305002, "group name is existed");
    RetCode RPCPORT_P2PPORT_SAME = RetCode.mark(305003, "rpcPort and p2pPort cannot be same");
    RetCode RPCPORT_IS_EXISTED = new RetCode(305004, "ip and rpcPort is existed in this group");
    RetCode P2PPORT_IS_EXISTED = new RetCode(305005, "ip and p2pPort is existed in this group");
    RetCode IP_FORMAT_ERROR = new RetCode(305006, "ip format error");
    RetCode NODE_ERROR_OR_NOT_ACTIVE = new RetCode(305007, "node error or not alive");
    RetCode NODE_NO_NOT_BELONG = new RetCode(305008, "node do not belong to this group");
    RetCode EMPETY_FILE_ERROR = new RetCode(305009, "the file is empty!");
    RetCode NOT_A_ZIP_FILE = new RetCode(305010, "it is not a zip file");
    RetCode FILE_FORMAT_ERROR = new RetCode(305011, "file format error");
    RetCode DO_NOT_ALL_ZIP_FILE = new RetCode(305012, "zip file can't contain zipfile");
    RetCode FOLDERS_ARE_NOT_ALLOWED = new RetCode(305013, "folders are not allowed");
    RetCode CONTRACT_ALREADY_EXIST = new RetCode(305014, "folder is already exist");
    RetCode NODE_ABNORMAL = new RetCode(305015, "node may be abnormal, please confirm");
    RetCode NOT_HAVE_PERMISSION = new RetCode(305016, "do not have permission, please check configAuth");
    RetCode GROUP_ID_NOT_EXISTS = RetCode.mark(305017, "group id not exists");
    RetCode NUMBER_TALLER_THAN_LATEST = RetCode.mark(305018, "request block number is taller than the latest");
    
    /* user code */
    RetCode USER_EXISTS = new RetCode(305101, "user already exists");
    RetCode USER_NOT_EXISTS = new RetCode(305102, "user id not exists");
    RetCode ADDRESS_INVALID = new RetCode(305103, "publickey address is invalid");
    
    /* system error */
    RetCode SYSTEM_ERROR = RetCode.mark(105001, "system error");
    RetCode PARAM_VAILD_FAIL = RetCode.mark(105002, "param valid fail");
    RetCode DB_EXCEPTION = RetCode.mark(105003, "database exception");
}
