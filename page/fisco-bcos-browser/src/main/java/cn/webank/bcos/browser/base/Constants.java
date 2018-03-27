package cn.webank.bcos.browser.base;

/**
 * 常量
 */
public class Constants {

    /*Database paging query default starting position*/
    public static final int DB_QUERY_DEFAULT_OFFSET = 0;
    /*Database paging query the maximum number of records per page*/
    public static final int DB_QUERY_MAX_PAGE_SIZE = 500;
    /*Database paging query default current page*/
    public static final int DB_QUERY_DEFAULT_PAGE_NO = 1;
    /*Database paging query default number of records per page*/
    public static final int DB_QUERY_DEFAULT_PAGE_SIZE = 20;
    /*Daily trading volume record table, the default number of query records: 14*/
    public static final int TBTXNBYDAY_DEFAULT_SIZE = 14;

    /*Date format:yyyy-MM-dd HH:mm:ss:SSS*/
    public static final String DEFAULT_DATA_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";
    //Date format：YYYYMMDD
    public static final String DATE_FORMAT_YYMMDD = "yyyyMMdd";

}