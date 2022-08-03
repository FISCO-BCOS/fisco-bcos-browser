/**
 * Copyright 2014-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.bcos.browser.service;

import java.time.Instant;
import java.util.List;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.enums.TableName;
import org.bcos.browser.base.exception.BaseException;
import org.bcos.browser.mapper.TableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * service of table
 */
@Data
@Log4j2
@Service
public class TableService {

    @Autowired
    private TableMapper tableMapper;
    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String dbUser;
    @Value("${spring.datasource.password}")
    private String dbPwd;

    /**
     * create common table.
     */
    public void newCommonTable() {
        tableMapper.createTbGroup();
        tableMapper.createTbBlockChainInfo();
        tableMapper.createTbContract();
        tableMapper.createTbFunction();
        tableMapper.createTbUser();
        tableMapper.createTbNode();
        tableMapper.createTbTxnDaily();
        tableMapper.createTbChainUser();
        tableMapper.createTbChainContract();
    }

    /**
     * create table by groupId
     */
    public void newTableByGroupId(int groupId) {
        if (groupId == 0) {
            return;
        }
        tableMapper.createTbBlock(TableName.BLOCK_DATA.getTableName(groupId));
        tableMapper.createTbTransaction(TableName.TX_DATA.getTableName(groupId));
        tableMapper.createTbReceipt(TableName.RECEIPT_DATA.getTableName(groupId));
    }
    
    /**
     * deop table.
     */
    public void dropTableByGroupId(int groupId) throws BaseException {
        Instant startTime = Instant.now();
        log.info("start dropTableByGroupId. startTime:{}", startTime.toEpochMilli());
        if (groupId == 0) {
            return;
        }
        for (TableName enumName : TableName.values()) {
            dropTableByName(enumName.getTableName(groupId));
        }
    }

    /**
     * check tableName.
     */
    public boolean checkTableExist(String tableName) throws BaseException {
        if (StringUtils.isBlank(tableName)) {
            return false;
        }
        List<String> tableNameList = tableMapper.queryTables(getDbName(), tableName);
        if (tableNameList == null || tableNameList.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * drop table by tableName.
     */
    private void dropTableByName(String tableName) throws BaseException {
        log.info("start drop table. name:{}", tableName);
        if (StringUtils.isBlank(tableName)) {
            return;
        }
        if (!checkTableExist(tableName)) {
            log.warn("fail dropTableByName. not fount this table, tableName:{}", tableName);
            return;
        }
        int affectedRow = 1;
        while (affectedRow > 0) {
            affectedRow = tableMapper.deleteByTableName(tableName);
            log.debug("delete table:{} affectedRow:{}", tableName, affectedRow);
        }

        // drop table
        tableMapper.dropTable(getDbName(), tableName);
        log.info("end dropTableByName. name:{}", tableName);
    }

    /**
     * get db name.
     */
    private String getDbName() throws BaseException {
        if (StringUtils.isBlank(dbUrl)) {
            log.error("fail getDbName. dbUrl is null");
            throw new BaseException(ConstantCode.SYSTEM_ERROR);
        }
        String subUrl = dbUrl.substring(0, dbUrl.indexOf("?"));
        String dbName = subUrl.substring(subUrl.lastIndexOf("/") + 1);
        return dbName;
    }
}
