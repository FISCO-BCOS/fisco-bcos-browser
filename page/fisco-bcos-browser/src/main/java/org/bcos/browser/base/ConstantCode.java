package org.bcos.browser.base;

/**
 * 错误类常量
 * 0成功  3000~3999一般用户异常  4000~4999系统异常
 */
public class ConstantCode {

    /*返回成功*/
    public static final RetCode SUCCESS = new RetCode(0,"成功");


    /**
     * 3000~3999
     */
    /*用户名为空(入参校验)*/
    public static final String LOGIN_FAIL_USERNAME_EMPTY  = "{\"code\":3000,\"msg\":\"登录失败，用户名不能为空！\"}";
    /*密码为空(入参校验)*/
    public static final String LOGIN_FAIL_PASSWORD_EMPTY = "{\"code\":3001,\"msg\":\"登录失败，密码不能为空！\"}";
    /*用户传入的登录信息为空*/
    public static final RetCode LOGIN_FAIL_INPUT_INFO_IS_NULL = new RetCode(3002,"登录失败，请输入用户登录信息！");
    /*用户信息不存在*/
    public static final RetCode LOGIN_FAIL_USER_NOT_FOUND = new RetCode(3003,"登录失败，用户信息不存在！");
    /*密码错误*/
    public static final RetCode LOGIN_FAIL_PASSWORD_IS_INCORRECT = new RetCode(3004,"登录失败，密码错误！");
    /*帐号已锁定*/
    public static final RetCode LOGIN_FAIL_USER_TATUS_IS_LOCK = new RetCode(3005,"登录失败，帐号已锁定！");
    /*查询失败，每页记录数不能为空*/
    public static final String QUERY_FAIL_PAGE_SIZE_EMPTY  = "{\"code\":3006,\"msg\":\"每页记录数不能为空！\"}";
    /*查询失败，每页记录数需大于5*/
    public static final String QUERY_FAIL_PAGE_SIZE_MIN_5  = "{\"code\":3007,\"msg\":\"每页记录数需大于等于5！\"}";
    /*查询失败，每页记录数需大于5*/
    public static final String QUERY_FAIL_PAGE_NUMBER_EMPTY  = "{\"code\":3008,\"msg\":\"当前页码不能为空！\"}";
    /*查询失败，当前页码需大于等于1*/
    public static final String QUERY_FAIL_PAGE_NUMBER_MIN_1  = "{\"code\":3009,\"msg\":\"当前页码需大于等于1\"}";
    /*查询失败，块Hash不能为空*/
    public static final RetCode QUERY_FAIL_BLOCK_HASH_EMPTY  = new RetCode(3010,"块Hash不能为空！");
    /*查询失败，hash值不能为空*/
    public static final RetCode QUERY_FAIL_PK_HASH_EMPTY  = new RetCode(3011,"hash值不能为空！");
    /*查询失败，hash值不能为空*/
    public static final String QUERY_FAIL_PK_HASH_PARAM_EMPTY  = "{\"code\":3012,\"msg\":\"hash值不能为空\"}";


    /**
     * 4000~4999
     */
    /*获取参数校验的返回码时出错*/
    public static final RetCode GET_PARAM_VAILD_RETCODE_FAIL = new RetCode(4000,"入参校验失败！");
    /*系统异常*/
    public static final RetCode SYSTEM_EXCEPTION = new RetCode(4001,"系统异常！");
    /*未知的用户状态*/
    public static final RetCode USER_STATUS_IS_NOT_FOUND = new RetCode(4002,"未知的用户状态！");
    /*数据库异常*/
    public static final RetCode DB_HANDLER_EXCEPTION = new RetCode(4003,"数据库异常！");
}


