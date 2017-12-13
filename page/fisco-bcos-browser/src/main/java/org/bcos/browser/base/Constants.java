package org.bcos.browser.base;

/**
 * 常量
 */
public class Constants {
    /*用户状态，无效*/
    public static final String USER_STATUS_INVALID = "0";
    /*用户状态，正常*/
    public static final String USER_STATUS_NORMAL = "1";
    /*用户状态，锁定*/
    public static final String USER_STATUS_LOCK = "2";

    /*用户密码最大错误次数*/
    public static final int USER_PASSWORD_FAIL_MAX_TIMES = 5;

    /*数据库分页查询默认起始位置*/
    public static final int DB_QUERY_DEFAULT_OFFSET = 0;
    /*数据库分页查询每页最大记录数*/
    public static final int DB_QUERY_MAX_PAGE_SIZE = 500;
    /*数据库分页查询默认当前页*/
    public static final int DB_QUERY_DEFAULT_PAGE_NO = 1;
    /*数据库分页查询默认每页记录数*/
    public static final int DB_QUERY_DEFAULT_PAGE_SIZE = 20;
    /*每日交易量记录表，默认查询记录数：14条*/
    public static final int TBTXNBYDAY_DEFAULT_SIZE = 14;

    /*默认的日期时间格式*/
    public static final String DEFAULT_DATA_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";
    /*默认的日期格式*/
    public static final String DEFAULT_DATA_FORMAT = "yyyy-MM-dd";

}