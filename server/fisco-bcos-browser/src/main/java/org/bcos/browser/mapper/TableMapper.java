/**
 * Copyright 2014-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bcos.browser.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TableMapper {

    // common table
    void createTbGroup();
    
    void createTbBlockChainInfo();
    
    void createTbContract();
    
    void createTbFunction();
    
    void createTbUser();
    
    void createTbNode();
    
    void createTbTxnDaily();

    void createTbChainUser();

    void createTbChainContract();

    // sub table
    void createTbBlock(@Param(value = "tableName")String tableName);
    
    void createTbTransaction(@Param(value = "tableName")String tableName);
    
    void createTbReceipt(@Param(value = "tableName")String tableName);
    
    // query
    List<String> queryTables(@Param("dbName") String dbName, @Param("tableName") String tableName);
    
    int deleteByTableName(@Param("tableName") String tableName);
    
    int dropTable(@Param("dbName") String dbName, @Param("tableName") String tableName);
}