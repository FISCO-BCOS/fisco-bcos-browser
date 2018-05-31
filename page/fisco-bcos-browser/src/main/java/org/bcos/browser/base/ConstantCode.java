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
 * @file: ConstantCode.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.base;


public class ConstantCode {

        /*Return success*/
    public static final RetCode SUCCESS = new RetCode(0,"success");


    /**
     * 3000~3999 General user exception
     */
    public static final String LOGIN_FAIL_USERNAME_EMPTY  = "{\"code\":3000,\"msg\":\"Login failed, user name cannot be empty\"}";
    public static final String LOGIN_FAIL_PASSWORD_EMPTY = "{\"code\":3001,\"msg\":\"Login failed, password cannot be empty\"}";
    public static final RetCode LOGIN_FAIL_INPUT_INFO_IS_NULL = new RetCode(3002,"Login failed, please enter user login information");
    public static final RetCode LOGIN_FAIL_USER_NOT_FOUND = new RetCode(3003,"Login failed, user information does not exist");
    public static final RetCode LOGIN_FAIL_PASSWORD_IS_INCORRECT = new RetCode(3004,"Login failed password");
    public static final RetCode LOGIN_FAIL_USER_TATUS_IS_LOCK = new RetCode(3005,"Login failed, account locked");
    public static final String QUERY_FAIL_PAGE_SIZE_EMPTY  = "{\"code\":3006,\"msg\":\"Page records cannot be empty\"}";
    public static final String QUERY_FAIL_PAGE_SIZE_MIN_5  = "{\"code\":3007,\"msg\":\"The number of records per page must be greater than or equal to 5\"}";
    public static final String QUERY_FAIL_PAGE_NUMBER_EMPTY  = "{\"code\":3008,\"msg\":\"Current page number cannot be blank\"}";
    public static final String QUERY_FAIL_PAGE_NUMBER_MIN_1  = "{\"code\":3009,\"msg\":\"The current page number must be greater than or equal to 1\"}";
    public static final RetCode QUERY_FAIL_BLOCK_HASH_EMPTY  = new RetCode(3010,"Block Hash cannot be empty");
    public static final RetCode QUERY_FAIL_PK_HASH_EMPTY  = new RetCode(3011,"Hash value cannot be empty");
    public static final String QUERY_FAIL_PK_HASH_PARAM_EMPTY  = "{\"code\":3012,\"msg\":\"Hash value cannot be empty\"}";
    public static final String QUERY_FAIL_OBJECT_ARRAY_PARAM_EMPTY  = "{\"code\":3013,\"msg\":\"Object cannot be empty\"}";
    public static final String QUERY_FAIL_ATTR_ARRAY_PARAM_EMPTY  = "{\"code\":3014,\"msg\":\"ATTR cannot be empty\"}";
    public static final String QUERY_FAIL_PK_ID_PARAM_EMPTY  = "{\"code\":3015,\"msg\":\"pkId cannot be empty\"}";
    public static final String SAVE_FAIL_RPCPORT_PARAM_EMPTY  = "{\"code\":3016,\"msg\":\"rpcPort cannot be empty\"}";
    public static final String SAVE_FAIL_IP_PARAM_EMPTY  = "{\"code\":3017,\"msg\":\"Ip cannot be empty\"}";
    public static final RetCode SAVE_FAIL  =  new RetCode(3018,"Failed to save");
    public static final String SAVE_FAIL_PK_ID_PARAM_EMPTY  = "{\"code\":3019,\"msg\":\"Pkid cannot be empty\"}";
    public static final String DELETE_FAIL_PK_ID_PARAM_EMPTY  = "{\"code\":3020,\"msg\":\"Pkid cannot be empty\"}";
    public static final RetCode DELETE_FAIL  =  new RetCode(3021,"failed to delete");
    public static final RetCode SAVE_FAIL_IP_PARAM_FORMAT_ERROR  =  new RetCode(3022,"Please enter the correct ip address");
    public static final String SAVE_FAIL_RPCPORT_PARAM_FORMAT_ERROR  = "{\"code\":3023,\"msg\":\"Please enter the correct RPC port\"}";
    public static final String QUERY_FAIL_DATESTR_PARAM_EMPTY  = "{\"code\":3024,\"msg\":\"Date cannot be blank\"}";


    /**
     * 4000~4999 system exception
     */
    public static final RetCode GET_PARAM_VAILD_RETCODE_FAIL = new RetCode(4000,"Failed to enter the checksum");
    public static final RetCode SYSTEM_EXCEPTION = new RetCode(4001,"System abnormality");
    public static final RetCode USER_STATUS_IS_NOT_FOUND = new RetCode(4002,"Unknown user status");
    public static final RetCode DB_HANDLER_EXCEPTION = new RetCode(4003,"Database exception");
}

