/*
 * This file is part of FISCO BCOS Browser.
 *
 * FISCO BCOS Browser is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FISCO BCOS Browser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FISCO BCOS Browser.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * @file: Constants.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.base;


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