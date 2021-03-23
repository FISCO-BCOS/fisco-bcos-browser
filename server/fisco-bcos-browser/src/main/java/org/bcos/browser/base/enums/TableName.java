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
package org.bcos.browser.base.enums;

/**
 * table name.
 */
public enum TableName {
    TASK_POOL("block_task_pool_"), 
    BLOCK_DATA("block_raw_data_"), 
    BLOCK_DETAIL("block_detail_info_"), 
    TX_DATA("tx_raw_data_"), 
    RECEIPT_DATA("tx_receipt_raw_data_"),
    TX_DETAIL("block_tx_detail_info_"), 
    CONTRACT_INFO("contract_info_"), 
    ACCOUNT_INFO("deployed_account_info_");
    
    String value;

    TableName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getTableName(int groupId) {
        return value + groupId;
    };
}
