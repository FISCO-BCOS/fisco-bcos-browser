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
    NODE("tb_node_"), TXN("tb_txnByDay_"), BLOCK("tb_block_"), TRANS("tb_transaction_");
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
