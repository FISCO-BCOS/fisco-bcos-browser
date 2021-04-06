# 接口说明

# 1、群组接口

## 1.1 新增群组

新增群组，群组id要和链上一致

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：`/fisco-bcos-browser/group/add`

c)、请求方式：POST

d)、返回格式：json

### 参数信息详情

| 序号     | 输入参数     | 类型     | 可为空     | 备注                  |
| -------- | ------------ | -------- | ---------- | --------------------- |
| 1        | groupId      | int      | 否         | 群组编号              |
| 2        | groupName    | String   | 否         | 群组名称              |
| 3        | groupDesc    | String   | 是         | 群组描述              |
| **序号** | **输出参数** | **类型** | **可为空** | **备注**              |
| 1        | code         | int      | 否         | 返回码信息请参看附录1 |
| 2        | message      | String   | 否         | 描述                  |
| 3        | data         | object   | 是         | 返回信息实体          |

### 入参事例

http://localhost:5101/fisco-bcos-browser/group/add

```
{
  "groupId": 1,
  "groupName": "test",
  "groupDesc": "测试"
}
```

### 出参示例

**a)、成功：**

```
{
  "code": 0,
  "message": "success",
  "data": null
}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

## 1.2 群组列表

获取群组信息列表

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：`/fisco-bcos-browser/group/groupList`

c)、请求方式：GET

d)、返回格式：json

### 参数信息详情

| 序号     | 输入参数     | 类型     | 可为空     | 备注                  |
| -------- | ------------ | -------- | ---------- | --------------------- |
|          |              |          |            |                       |
| **序号** | **输出参数** | **类型** | **可为空** | **备注**              |
| 1        | code         | int      | 否         | 返回码信息请参看附录1 |
| 2        | message      | String   | 否         | 描述                  |
| 3        | data         | object   | 是         | 返回信息实体          |
| 3.1      |              | List     |            | 群组列表              |
| 3.1.1    | groupId      | int      | 否         | 群组编号              |
| 3.1.2    | groupName    | String   | 否         | 群组名称              |
| 3.1.3    | groupDesc    | String   | 否         | 群组描述              |

### 入参事例

 http://localhost:5101/fisco-bcos-browser/group/groupList

### 出参示例

**a)、成功：**

```
{
  "code": 0,
  "message": "success",
  "data": [
    {
      "groupId": 1,
      "groupName": "test",
      "groupDesc": "测试"
    },
    {
      "groupId": 2,
      "groupName": "test2",
      "groupDesc": "测试2"
    }
  ]
}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

## 1.3 删除群组

根据群组id删除该群组及该群组下的节点、区块等相关信息

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：`/fisco-bcos-browser/group/deleteById/{groupId}`

c)、请求方式：DELETE

d)、返回格式：json

### 参数信息详情

| 序号     | 输入参数     | 类型     | 可为空     | 备注                  |
| -------- | ------------ | -------- | ---------- | --------------------- |
| 1        | groupId      | int      | 否         | 群组编号              |
| **序号** | **输出参数** | **类型** | **可为空** | **备注**              |
| 1        | code         | int      | 否         | 返回码信息请参看附录1 |
| 2        | message      | String   | 否         | 描述                  |
| 3        | data         | object   | 是         | 返回信息实体          |

### 入参事例

http://localhost:5101/fisco-bcos-browser/group/deleteById/3

### 出参示例

**a)、成功：**

```
{
  "code": 0,
  "message": "success",
  "data": null
}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

# 2、概览接口

## 2.1 区块链概览

获取当前群组的区块链概览信息

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：`/fisco-bcos-browser/home/blockChainInfo/{groupId}`

c)、请求方式：GET

d)、返回格式：json

### 参数信息详情

| 序号     | 输入参数     | 类型     | 可为空     | 备注                  |
| -------- | ------------ | -------- | ---------- | --------------------- |
| 1        | groupId      | int      | 否         | 群组编号              |
| **序号** | **输出参数** | **类型** | **可为空** | **备注**              |
| 1        | code         | int      | 否         | 返回码信息请参看附录1 |
| 2        | message      | String   | 否         | 描述                  |
| 3        | data         | object   | 否         | 返回信息实体          |
| 3.1      | groupId      | int      | 否         | 群组编号              |
| 3.2      | latestNumber | int      | 否         | 最新块高              |
| 3.3      | txn          | int      | 否         | 总交易量              |
| 3.4      | pendingTxn   | int      | 否         | 待处理交易数          |
| 3.5      | pbftView     | int      | 否         | 视图                  |
| 3.6      | nodeCount    | int      | 否         | 节点个数              |

### 入参事例

http://localhost:5101/fisco-bcos-browser/home/blockChainInfo/1

### 出参示例

**a)、成功：**

```
{
  "code": 0,
  "message": "success",
  "data": {
    "groupId": 1,
    "latestNumber": 2,
    "txn": 2,
    "pendingTxn": 0,
    "pbftView": 73807,
    "nodeCount": 2
  }
}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

## 2.2 交易量信息

获取当前群组某段时间的交易量信息

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：`/fisco-bcos-browser/home/txnLately/{groupId}/{dateTimeBegin}/{dateTimeEnd}`

c)、请求方式：GET

d)、返回格式：json

### 参数信息详情

| 序号     | 输入参数      | 类型     | 可为空     | 备注                         |
| -------- | ------------- | -------- | ---------- | ---------------------------- |
| 1        | groupId       | int      | 否         | 群组编号                     |
| 2        | dateTimeBegin | String   | 否         | 开始时间（格式：2019-02-10） |
| 3        | dateTimeEnd   | String   | 否         | 结束时间（格式：2019-02-10） |
| **序号** | **输出参数**  | **类型** | **可为空** | **备注**                     |
| 1        | code          | int      | 否         | 返回码信息请参看附录1        |
| 2        | message       | String   | 否         | 描述                         |
| 3        | data          | object   | 是         | 返回信息实体                 |
| 3.1      |               | List     |            | 信息列表                     |
| 3.1.1    | dateStr       | String   | 否         | 时间                         |
| 3.1.2    | txn           | int      | 否         | 交易量                       |

### 入参事例

 http://localhost:5101/fisco-bcos-browser/home/txnLately/1/2019-02-10/2019-02-22

### 出参示例

**a)、成功：**

```
{
  "code": 0,
  "message": "success",
  "data": [
    {
      "dateStr": "2/22",
      "txn": 5
    },
    {
      "dateStr": "2/21",
      "txn": 3
    }
  ]
}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

## 2.3 配置开关

查询是否开启配置权限

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：`/fisco-bcos-browser/home/isConfigAuth`

c)、请求方式：GET

d)、返回格式：json

### 参数信息详情

| 序号     | 输入参数     | 类型     | 可为空     | 备注                  |
| -------- | ------------ | -------- | ---------- | --------------------- |
|          |              |          |            |                       |
| **序号** | **输出参数** | **类型** | **可为空** | **备注**              |
| 1        | code         | int      | 否         | 返回码信息请参看附录1 |
| 2        | message      | String   | 否         | 描述                  |
| 3        | data         | object   | 否         | 返回信息实体          |

### 入参事例

http://localhost:5101/fisco-bcos-browser/home/isConfigAuth

### 出参示例

**a)、成功：**

```
{
  "code": 0,
  "message": "success",
  "data": true
}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

# 3、节点接口

## 3.1 新增节点

新增当前群组的节点信息，支持批量新增

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：`/fisco-bcos-browser/node/add`

c)、请求方式：POST

d)、返回格式：json

### 参数信息详情

| 序号     | 输入参数     | 类型     | 可为空     | 备注                  |
| -------- | ------------ | -------- | ---------- | --------------------- |
| 1        | groupId      | int      | 否         | 群组编号              |
| 2        | data         | List     | 否         | 节点信息列表          |
| 2.1      | ip           | String   | 否         | 节点ip                |
| 2.2      | rpcPort      | String   | 否         | 节点rpcPort           |
| 2.3      | p2pPort      | String   | 否         | 节点p2pPort           |
| **序号** | **输出参数** | **类型** | **可为空** | **备注**              |
| 1        | code         | int      | 否         | 返回码信息请参看附录1 |
| 2        | message      | String   | 否         | 描述                  |
| 3        | data         | object   | 是         | 返回信息实体          |

### 入参事例

http://localhost:5101/fisco-bcos-browser/node/add

```
{
  "groupId": 1,
  "data": [
    {
      "ip": "127.0.0.1",
      "rpcPort": "8545",
      "p2pPort": "30300"
    }
  ]
}
```

### 出参示例

**a)、成功：**

```
{
  "code": 0,
  "message": "success",
  "data": null
}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

## 3.2 节点列表

获取当前群组下节点信息列表

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：

`/fisco-bcos-browser/node/nodeList/{groupId}/{pageNumber}/{pageSize}?type={type}&ip={ip}&rpcPort={rpcPort}&p2pPort={p2pPort}`

c)、请求方式：GET

d)、返回格式：json

### 参数信息详情

| 序号     | 输入参数     | 类型     | 可为空     | 备注                                  |
| -------- | ------------ | -------- | ---------- | ------------------------------------- |
| 1        | groupId      | int      | 否         | 群组编号                              |
| 2        | pageNumber   | int      | 否         | 当前页码                              |
| 3        | pageSize     | int      | 否         | 每页记录数                            |
| 4        | type         | int      | 是         | 节点类型（0-手动添加）                |
| 5        | ip           | String   | 是         | 节点ip                                |
| 6        | rpcPort      | String   | 是         | 节点rpcPort                           |
| 7        | p2pPort      | String   | 是         | 节点p2pPort                           |
| **序号** | **输出参数** | **类型** | **可为空** | **备注**                              |
| 1        | code         | int      | 否         | 返回码信息请参看附录1                 |
| 2        | message      | String   | 否         | 描述                                  |
| 3        | data         | object   | 是         | 返回信息实体                          |
| 3.1      |              | List     |            | 信息列表                              |
| 3.1.1    | groupId      | int      | 否         | 群组编号                              |
| 3.1.2    | nodeId       | int      | 否         | 节点id                                |
| 3.1.3    | groupDesc    | String   | 否         | 群组描述                              |
| 3.1.4    | ip           | String   | 否         | 节点ip                                |
| 3.1.5    | rpcPort      | String   | 否         | 节点rpcPort                           |
| 3.1.6    | p2pPort      | String   | 否         | 节点p2pPort                           |
| 3.1.7    | blockNumber  | int      | 否         | 节点块高                              |
| 3.1.8    | pbftView     | int      | 否         | 节点视图                              |
| 3.1.9    | status       | int      | 否         | 节点状态（0：正常，1：异常，2：废弃） |
| 3.2.0    | type         | int      | 否         | 节点类型                              |
| 4        | totalCount   | int      | 否         | 总条数                                |

### 入参事例

 http://localhost:5101/fisco-bcos-browser/node/nodeList/1/1/5?ip=127.0.0.1

### 出参示例

**a)、成功：**

```
{
  "code": 0,
  "message": "success",
  "data": [
    {
      "nodeId": 1,
      "groupId": 1,
      "ip": "127.0.0.1",
      "rpcPort": "8545",
      "p2pPort": "30300",
      "blockNumber": 2,
      "pbftView": 1002,
      "status": 0,
      "type": 0
    }
  ],
  "totalCount": 1
}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

## 3.3 删除节点

根据节点id删除节点

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：`/fisco-bcos-browser/node/deleteById/{groupId}/{nodeId}`

c)、请求方式：DELETE

d)、返回格式：json

### 参数信息详情

| 序号     | 输入参数     | 类型     | 可为空     | 备注                  |
| -------- | ------------ | -------- | ---------- | --------------------- |
| 1        | groupId      | int      | 否         | 群组编号              |
| 2        | nodeId       | String   | 否         | 节点Id                |
| **序号** | **输出参数** | **类型** | **可为空** | **备注**              |
| 1        | code         | int      | 否         | 返回码信息请参看附录1 |
| 2        | message      | String   | 否         | 描述                  |
| 3        | data         | object   | 是         | 返回信息实体          |

### 入参事例

http://localhost:5101/fisco-bcos-browser/node/deleteById/1/xxxxxxx

### 出参示例

**a)、成功：**

```
{
  "code": 0,
  "message": "success",
  "data": null
}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

# 4、区块接口

## 4.1 区块信息列表

获取当前群组下的区块信息列表

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：`/fisco-bcos-browser/block/blockList/{groupId}/{pageNumber}/{pageSize}?blockHash={blockHash}&blockNumber={blockNumber}}`

c)、请求方式：GET

### 参数信息详情

| 序号     | 输入参数     | 类型     | 可为空     | 备注                  |
| -------- | ------------ | -------- | ---------- | --------------------- |
| 1        | groupId      | int      | 否         | 群组编号              |
| 2        | pageNumber   | int      | 否         | 当前页码              |
| 3        | pageSize     | int      | 否         | 每页记录数            |
| 4        | blockHash    | String   | 是         | 块hash                |
| 5        | blockNumber  | String   | 是         | 块高                  |
| **序号** | **输出参数** | **类型** | **可为空** | **备注**              |
| 1        | code         | int      | 否         | 返回码信息请参看附录1 |
| 2        | message      | String   | 否         | 描述                  |
| 3        | data         | object   | 是         | 返回信息实体          |
| 3.1      |              | List     |            | 信息列表              |
| 3.1.1    | blockHash    | String   | 否         | 区块hash              |
| 3.1.2    | number       | int      | 否         | 块高                  |
| 3.1.3    | sealer       | String   | 否         | 出块者                |
| 3.1.4    | dateTimeStr  | String   | 否         | 出块时间              |
| 3.1.5    | txn          | int      | 否         | 块中交易量            |
| 4        | totalCount   | int      | 否         | 总条数                |

### 入参事例

 http://localhost:5101/fisco-bcos-browser/block/blockList/1/1/5

### 出参示例

**a)、成功：**

```
{
  "code": 0,
  "message": "success",
  "data": [
    {
      "blockHash": "0x973cbc3ff1611599523edcdb1b2fa869a3941f4ada798dc45a0225b5de0a401b",
      "number": 2,
      "sealer": "0x1",
      "dateTimeStr": "2019-02-21 17:55:00:777",
      "txn": 1
    }
  ],
  "totalCount": 1
}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

## 4.2 区块信息

根据区块hash获取区块信息

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：`/fisco-bcos-browser/block/blockByHash/{groupId}/{blockHash}`

c)、请求方式：GET

d)、返回格式：json

### 参数信息详情

| 序号     | 输入参数     | 类型     | 可为空     | 备注                  |
| -------- | ------------ | -------- | ---------- | --------------------- |
| 1        | groupId      | int      | 否         | 群组编号              |
| 2        | blockHash    | String   | 否         | 区块hash              |
| **序号** | **输出参数** | **类型** | **可为空** | **备注**              |
| 1        | code         | int      | 否         | 返回码信息请参看附录1 |
| 2        | message      | String   | 否         | 描述                  |
| 3        | data         | object   | 否         | 返回信息实体          |

### 入参事例

 http://localhost:5101/fisco-bcos-browser/block/blockByHash/1/0x6060dffc89a336cefa58589c6d1f7180704b4f31e367f43c7722bbfc994180a7

### 出参示例

**a)、成功：**

```
{
  "code": 0,
  "message": "success",
  "data": {
    "sealer": "0x0",
    "gasLimit": "0x0",
    "logsBloom": "0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
    "number": "0x1",
    "gasUsed": "0x0",
    "extraData": [],
    "transactionsRoot": "0xaf0b34f0d1dd6d148cad0cb5d92f8228bc63d7963622e8e1351b0d7272e5b3d7",
    "stateRoot": "0xaae025c9a89626a9465d0c3d81d73cdefc79ad646989c3b37ed422985ecfb3bf",
    "parentHash": "0xcb35ab7696d19f9376aee7f01c8cebbef073e5e4ffeebaea87f5bfbd19a1471d",
    "transactions": [
      {
        "blockHash": "0x6060dffc89a336cefa58589c6d1f7180704b4f31e367f43c7722bbfc994180a7",
        "input": "0x48f85bce000000000000000000000000000000000000000000000000000000000000001bf5bd8a9e7ba8b936ea704292ff4aaa5797bf671fdc8526dcd159f23c1f5a05f44e9fa862834dc7cb4541558f2b4961dc39eaaf0af7f7395028658d0e01b86a37",
        "blockNumber": "0x1",
        "gas": "0x9184e729fff",
        "from": "0x0efaa59ae8a68c8833dcfa59e0eca7a95dc9b4ba",
        "transactionIndex": "0x0",
        "to": "0xd6f1a71052366dbae2f7ab2d5d5845e77965cf0d",
        "nonce": "0x2ade583745343a8f9a70b40db996fbe69c635318328581550547418000017516",
        "value": "0x0",
        "hash": "0x01185aad6d8b2f430df19514ec20a8deea14ca2b861773b9583f0c42babdf710",
        "gasPrice": "0x174876e7ff"​
      }
    ],
    "hash": "0x6060dffc89a336cefa58589c6d1f7180704b4f31e367f43c7722bbfc994180a7",
    "timestamp": "0x16903d3fec8"​
  }
}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

# 5、交易接口

## 5.1 交易信息列表

获取当前群组下的交易信息列表

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：`/fisco-bcos-browser/transaction/transactionList/{groupId}/{pageNumber}/{pageSize}?transHash={transHash}&blockNumber={blockNumber}}`

c)、请求方式：GET

d)、返回格式：json

### 参数信息详情

| 序号     | 输入参数      | 类型     | 可为空     | 备注                  |
| -------- | ------------- | -------- | ---------- | --------------------- |
| 1        | groupId       | int      | 否         | 群组编号              |
| 2        | pageNumber    | int      | 否         | 当前页码              |
| 3        | pageSize      | int      | 否         | 每页记录数            |
| 4        | transHash     | String   | 是         | 交易hash              |
| 5        | blockNumber   | String   | 是         | 块高                  |
| **序号** | **输出参数**  | **类型** | **可为空** | **备注**              |
| 1        | code          | int      | 否         | 返回码信息请参看附录1 |
| 2        | message       | String   | 否         | 描述                  |
| 3        | data          | object   | 是         | 返回信息实体          |
| 3.1      |               | List     |            | 信息列表              |
| 3.1.1    | transHash     | String   | 否         | 交易hash              |
| 3.1.2    | blockHash     | String   | 否         | 区块hash              |
| 3.1.3    | blockNumber   | int      | 否         | 块高                  |
| 3.1.4    | blockTimesStr | String   | 否         | 出块时间              |
| 3.1.5    | from          | String   | 否         | 发送者                |
| 3.1.6    | to            | String   | 否         | 接收者                |
| 3.1.7    | transIndex    | int      | 否         | 交易索引              |
| 3.1.8    | method        | String   | 否         | 调用的方法            |
| 4        | totalCount    | int      | 否         | 总条数                |

### 入参事例

 http://localhost:5101/fisco-bcos-browser/transaction/transactionList/1/1/5

### 出参示例

**a)、成功：**

```
{
  "code": 0,
  "message": "success",
  "data": [
    {
      "transHash": "0x8f6d3d24bd9c5676ebb7b655a05ad9da1e254d8049daba1c13b2941a0eb4b990",
      "blockHash": "0x973cbc3ff1611599523edcdb1b2fa869a3941f4ada798dc45a0225b5de0a401b",
      "blockNumber": 2,
      "blockTimesStr": "2019-02-21 17:55:00:777",
      "from": "0x494c9042dd1801e8f21cfd2be2eca9aa0550b71a",
      "to": "0xd6f1a71052366dbae2f7ab2d5d5845e77965cf0d",
      "transIndex": 0,
      "method": "set"
    }
  ],
  “totalCount”: 1
}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

## 5.2 交易信息

根据交易hash获取交易信息

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：`/fisco-bcos-browser/transaction/transactionByHash/{groupId}/{transHash}`

c)、请求方式：GET

d)、返回格式：json

### 参数信息详情

| 序号     | 输入参数     | 类型     | 可为空     | 备注                  |
| -------- | ------------ | -------- | ---------- | --------------------- |
| 1        | groupId      | int      | 否         | 群组编号              |
| 2        | transHash    | String   | 否         | 交易hash              |
| **序号** | **输出参数** | **类型** | **可为空** | **备注**              |
| 1        | code         | int      | 否         | 返回码信息请参看附录1 |
| 2        | message      | String   | 否         | 描述                  |
| 3        | data         | object   | 否         | 返回信息实体          |

### 入参事例

http://localhost:5101/fisco-bcos-browser/transaction/transactionByHash/1/0x01185aad6d8b2f430df19514ec20a8deea14ca2b861773b9583f0c42babdf710

### 出参示例

**a)、成功：**

```
{
  “code”: 0,
  “message”: “success”,
  “data”: {
    “blockHash”: “0x6060dffc89a336cefa58589c6d1f7180704b4f31e367f43c7722bbfc994180a7”,
    “input”: “0x48f85bce000000000000000000000000000000000000000000000000000000000000001bf5bd8a9e7ba8b936ea704292ff4aaa5797bf671fdc8526dcd159f23c1f5a05f44e9fa862834dc7cb4541558f2b4961dc39eaaf0af7f7395028658d0e01b86a37”,
    “blockNumber”: “0x1”,
    “gas”: “0x9184e729fff”,
    “from”: “0x0efaa59ae8a68c8833dcfa59e0eca7a95dc9b4ba”,
    “transactionIndex”: “0x0”,
    “to”: “0xd6f1a71052366dbae2f7ab2d5d5845e77965cf0d”,
    “nonce”: “0x2ade583745343a8f9a70b40db996fbe69c635318328581550547418000017516”,
    “value”: “0x0”,
    “hash”: “0x01185aad6d8b2f430df19514ec20a8deea14ca2b861773b9583f0c42babdf710”,
    “gasPrice”: “0x174876e7ff”
  }
}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

## 5.3 交易回执信息

根据交易hash获取交易回执信息

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：`/fisco-bcos-browser/transaction/receiptByHash/{groupId}/{transHash}`

c)、请求方式：GET

d)、返回格式：json

### 参数信息详情

| 序号     | 输入参数     | 类型     | 可为空     | 备注                  |
| -------- | ------------ | -------- | ---------- | --------------------- |
| 1        | groupId      | int      | 否         | 群组编号              |
| 2        | transHash    | String   | 否         | 交易hash              |
| **序号** | **输出参数** | **类型** | **可为空** | **备注**              |
| 1        | code         | int      | 否         | 返回码信息请参看附录1 |
| 2        | message      | String   | 否         | 描述                  |
| 3        | data         | object   | 否         | 返回信息实体          |

### 入参事例

http://localhost:5101/fisco-bcos-browser/transaction/receiptByHash/1/0x01185aad6d8b2f430df19514ec20a8deea14ca2b861773b9583f0c42babdf710

### 出参示例

**a)、成功：**

```
{
  “code”: 0,
  “message”: “success”,
  “data”: {
    “output”: “0x”,
    “blockHash”: “0x6060dffc89a336cefa58589c6d1f7180704b4f31e367f43c7722bbfc994180a7”,
    “logsBloom”: “0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000”,
    “gasUsed”: “0x64d8”,
    “blockNumber”: “0x1”,
    “contractAddress”: “0x0000000000000000000000000000000000000000”,
    “from”: “0x0efaa59ae8a68c8833dcfa59e0eca7a95dc9b4ba”,
    “transactionIndex”: “0x0”,
    “to”: “0xd6f1a71052366dbae2f7ab2d5d5845e77965cf0d”,
    “logs”: [],
    “transactionHash”: “0x01185aad6d8b2f430df19514ec20a8deea14ca2b861773b9583f0c42babdf710”,
    “status”: “0x0”
  }
}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

## 5.4 合约二进制代码

根据合约地址获取合约二进制代码

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：`/fisco-bcos-browser/transaction/code`

c)、请求方式：POST

d)、返回格式：json

### 参数信息详情

| 序号     | 输入参数     | 类型          | 可为空     | 备注                  |
| -------- | ------------ | ------------- | ---------- | --------------------- |
| 1        | groupId      | int           | 否         | 群组编号              |
| 2        | data         | List<address> | 否         | 合约地址列表          |
| **序号** | **输出参数** | **类型**      | **可为空** | **备注**              |
| 1        | code         | int           | 否         | 返回码信息请参看附录1 |
| 2        | message      | String        | 否         | 描述                  |
| 3        | data         | object        | 否         | 返回信息实体          |

### 入参事例

http://localhost:5101/fisco-bcos-browser/transaction/code

```
{
  "groupId": 1,
  "data": [
    "0x0000000000000000000000000000000000001004"
  ]
}
```

### 出参示例

**a)、成功：**

```
{
  "code": 0,
  "message": "success",
  "data": [
    "0x"
  ]
}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

## 5.5 交易解析数据列表

根据交易hash调用获取交易解析所需数据

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：`/fisco-bcos-browser/transaction/analyzeData`

c)、请求方式：POST

d)、返回格式：json

### 参数信息详情

| 序号     | 输入参数             | 类型     | 可为空     | 备注                  |
| -------- | -------------------- | -------- | ---------- | --------------------- |
| 1        | groupId              | int      | 否         | 群组编号              |
| 2        | data                 | List     | 否         | 列表                  |
| 2.1      | transHash            | String   | 是         | 交易hash              |
| **序号** | **输出参数**         | **类型** | **可为空** | **备注**              |
| 1        | code                 | int      | 否         | 返回码信息请参看附录1 |
| 2        | message              | String   | 否         | 描述                  |
| 3        | data                 | object   | 是         | 返回信息实体          |
| 3.1      |                      | List     |            | 信息列表              |
| 3.1.1    | transactionFromChain | object   | 否         | 交易信息              |
| 3.1.2    | receiptFromChain     | object   | 否         | 交易回执信息          |

### 入参事例

http://localhost:5101/fisco-bcos-browser/transaction/analyzeData

```
{
  "groupId": 1,
  "data": [
    {
      "transHash": "XXXX"
    },
    {
      "transHash": "XXXX"
    }
  ]
}
```

### 出参示例

**a)、成功：**

```
{
  "code": 0,
  "message": "success",
  "data": [
    {
      "transactionFromChain": {
        "blockHash": "0x582c13681b26ea942fa46f13146e0c44311de89cbd5305e48ad5925ff8ab3215",
        "input": "0xa216464b000000000000000000000000000000000000000000000000000000000000008000000000000000000000000000000000000000000000000000000000000000c000000000000000000000000000000000000000000000000000000000000001000000000000000000000000000000000000000000000000000000000000000160000000000000000000000000000000000000000000000000000000000000000a48656c6c6f576f726c64000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003312e310000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000002a307832393963306136666463373137373038666663396635323866376637613037346338643431613866000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
        "blockNumber": "0x15",
        "gas": "0x419ce0",
        "from": "0x7234c32327795e4e612164e3442cfae0d445b9ad",
        "transactionIndex": "0x0",
        "to": "0x0000000000000000000000000000000000001004",
        "nonce": "0x2d4ba6ebfbb055933dedbabb46c72c5d2980f7a1fa94b0bf83bf6c58ae4b831",
        "value": "0x0",
        "hash": "0xb8e078e26222a247c97b822bfd071c579474e6b8e8780720931542846ceb7689",
        "gasPrice": "0x51f4d5c00"​
      },
      "receiptFromChain": {
        "output": "0x0000000000000000000000000000000000000000000000000000000000000001",
        "blockHash": "0x582c13681b26ea942fa46f13146e0c44311de89cbd5305e48ad5925ff8ab3215",
        "logsBloom": "0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
        "gasUsed": "0x68d8",
        "blockNumber": "0x15",
        "contractAddress": "0x0000000000000000000000000000000000000000",
        "from": "0x7234c32327795e4e612164e3442cfae0d445b9ad",
        "transactionIndex": "0x0",
        "to": "0x0000000000000000000000000000000000001004",
        "logs": [
          
        ],
        "transactionHash": "0xb8e078e26222a247c97b822bfd071c579474e6b8e8780720931542846ceb7689",
        "status": "0x0"
      }
    }
  ]
}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

# 6、合约接口

## 6.1 合约保存

将合约信息保存到DB

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：`/fisco-bcos-browser/contract/add`

c)、请求方式：POST

d)、返回格式：json

### 参数信息详情

| 序号     | 输入参数       | 类型     | 可为空     | 备注                  |
| -------- | -------------- | -------- | ---------- | --------------------- |
| 1        | data           | List     | 否         | 合约列表              |
| 1.1      | contractName   | String   | 否         | 合约名                |
| 1.2      | contractDesc   | String   | 否         | 合约描述              |
| 1.3      | contractSource | String   | 否         | 合约源码              |
| 1.3      | contractPath   | String   | 否         | 合约路径              |
| **序号** | **输出参数**   | **类型** | **可为空** | **备注**              |
| 1        | code           | int      | 否         | 返回码信息请参看附录1 |
| 2        | message        | String   | 否         | 描述                  |
| 3        | data           | object   | 是         | 返回信息实体          |

### 入参事例

http://localhost:5101/fisco-bcos-browser/contract/add

```
{
  “data”: [
    {
      “contractName”: ”Helloworld”,
      “contractDesc”: ””,
      “contractSource”: ””,
      “contractPath”: ”/”
    }
  ]
}
```

### 出参示例

**a)、成功：**

```
{
  "code": 0,
  "message": "success",
  "data": null
}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

## 6.2 合约信息列表

返回合约信息到前台展示

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：`/fisco-bcos-browser/contract/contractList/{pageNumber}/{pageSize}`

c)、请求方式：GET

d)、返回格式：json

### 参数信息详情

| 序号     | 输入参数       | 类型     | 可为空     | 备注                  |
| -------- | -------------- | -------- | ---------- | --------------------- |
| 1        | pageNumber     | int      | 否         | 当前页码              |
| 2        | pageSize       | int      | 否         | 每页记录数            |
| **序号** | **输出参数**   | **类型** | **可为空** | **备注**              |
| 1        | code           | int      | 否         | 返回码信息请参看附录1 |
| 2        | message        | String   | 否         | 描述                  |
| 3        | data           | object   | 是         | 返回信息实体          |
| 3.1      |                | List     |            | 信息列表              |
| 3.1.1    | contractId     | int      | 否         | 合约编号              |
| 3.1.3    | contractName   | String   | 否         | 合约名                |
| 3.1.4    | contractDesc   | String   | 是         | 合约描述              |
| 3.1.5    | contractBin    | String   | 是         | 合约bin               |
| 3.1.6    | contractAbi    | String   | 是         | 合约abi               |
| 3.1.7    | contractSource | String   | 否         | 合约源码              |
| 3.1.8    | contractStatus | int      | 否         | 合约状态              |
| 3.1.9    | errorInfo      | String   | 是         | 编译错误信息          |
| 4        | totalCount     | int      | 否         | 总条数                |

### 入参事例

http://localhost:5101/fisco-bcos-browser/contract/contractList/1/2

### 出参示例

**a)、成功：**

```
{
  “code”: 0,
  “message”: “success”,
  “data”: [
    {
      “contractId”: 300001,
      “contractName”: “OK”,
      “contractDesc”: “XXXX”,
      “contractBin”: “XXXX”,
      “contractAbi”: “XXXX”,
      “contractSource”: “XXXX”,
      “contractStatus”: 1,
      “errorInfo”: “XXXX”
    },
    {
      “contractId”: 300001,
      “contractName”: “OK”,
      “contractDesc”: “XXXX”,
      “contractBin”: “XXXX”,
      “contractAbi”: “XXXX”,
      “contractSource”: “XXXX”,
      “contractStatus”: 1,
      “errorInfo”: “XXXX”
    }
  ],
  "totalCount":13}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

## 6.3 合约更新

将合约编译后的信息存入到DB

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：`/fisco-bcos-browser/contract/update`

c)、请求方式：PUT

d)、返回格式：json

### 参数信息详情

| 序号     | 输入参数        | 类型     | 可为空     | 备注                  |
| -------- | --------------- | -------- | ---------- | --------------------- |
| 1        | data            | List     | 否         | 合约信息              |
| 1.1      | contractId      | int      | 是         | 合约编号              |
| 1.2      | contractBin     | String   | 是         | 合约bin               |
| 1.3      | contractAbi     | String   | 是         | 合约abi               |
| 1.4      | contractAddress | String   | 是         | 合约地址              |
| 1.5      | errorInfo       | String   | 是         | 编译错误信息          |
| **序号** | **输出参数**    | **类型** | **可为空** | **备注**              |
| 1        | code            | int      | 否         | 返回码信息请参看附录1 |
| 2        | message         | String   | 否         | 描述                  |
| 3        | data            | object   | 是         | 返回信息实体          |

### 入参事例

http://localhost:5101/fisco-bcos-browser/contract/update

```
{
  "data": [
    {
      "contractId": 1,
      "contractBin": "XXXX",
      "contractAbi": "XXXX",
      "contractAddress": "XXXX",
      "errorInfo": "XXXX"
    },
    {
      "contractId": 1,
      "contractBin": "XXXX",
      "contractAbi": "XXXX",
      "contractAddress": "XXXX",
      "errorInfo": "XXXX"
    }
  ]
}
```

### 出参示例

**a)、成功：**

```
{
  "code": 0,
  "message": "success",
  "data": null
}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

## 6.4 合约删除

根据合约id删除合约

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：`/fisco-bcos-browser/contract/deleteById?contractId={contractId}`

c)、请求方式：DELETE

d)、返回格式：json

### 参数信息详情

| 序号     | 输入参数     | 类型     | 可为空     | 备注                   |
| -------- | ------------ | -------- | ---------- | ---------------------- |
| 1        | contractId   | String   | 否         | 合约编号,多个以“,”分隔 |
| **序号** | **输出参数** | **类型** | **可为空** | **备注**               |
| 1        | code         | int      | 否         | 返回码信息请参看附录1  |
| 2        | message      | String   | 否         | 描述                   |
| 3        | data         | object   | 是         | 返回信息实体           |

### 入参事例

http://localhost:5101/fisco-bcos-browser/contract/deleteById?contractId=‘1,2’

### 出参示例

**a)、成功：**

```
{
  "code": 0,
  "message": "success",
  "data": null
}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

## 6.5 合约方法相关信息保存

保存合约方法相关信息

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：`/fisco-bcos-browser/contract/addFunction`

c)、请求方式：PUT

d)、返回格式：json

### 参数信息详情

| 序号     | 输入参数     | 类型     | 可为空     | 备注                  |
| -------- | ------------ | -------- | ---------- | --------------------- |
| 1        | data         | List     | 否         | 合约方法信息          |
| 1.1      | methodId     | String   | 是         | 方法信息              |
| 1.2      | abiInfo      | String   | 是         | Abi信息               |
| 1.3      | type         | String   | 是         | 方法类型              |
| **序号** | **输出参数** | **类型** | **可为空** | **备注**              |
| 1        | code         | int      | 否         | 返回码信息请参看附录1 |
| 2        | message      | String   | 否         | 描述                  |
| 3        | data         | object   | 是         | 返回信息实体          |

### 入参事例

http://localhost:5101/fisco-bcos-browser/contract/addFunction

```
{
  "data": [
    {
      "methodId": "XXXX",
      "abiInfo": "XXXX",
      "type": "XXXX"
    },
    {
      "methodId": "XXXX",
      "abiInfo": "XXXX",
      "type": "XXXX"
    }
  ]
}
```

### 出参示例

**a)、成功：**

```
{
  "code": 0,
  "message": "success",
  "data": null
}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

## 6.6 合约方法信息查询

通过methodId查询合约方法相关信息

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：`/fisco-bcos-browser/contract/function/{methodId}`

c)、请求方式：GET

d)、返回格式：json

### 参数信息详情

| 序号     | 输入参数     | 类型     | 可为空     | 备注                  |
| -------- | ------------ | -------- | ---------- | --------------------- |
| 1        | methodId     | String   | 否         | 方法id                |
| **序号** | **输出参数** | **类型** | **可为空** | **备注**              |
| 1        | code         | int      | 否         | 返回码信息请参看附录1 |
| 2        | message      | String   | 否         | 描述                  |
| 3        | data         | object   | 否         | 返回信息实体          |

### 入参事例

http://localhost:5101/fisco-bcos-browser/contract/function/0x11111

### 出参示例

**a)、成功：**

```
{
  “code”: 0,
  “message”: “success”,
  “data”: {
    "methodId": "XXXX",
    "abiInfo": "XXXX",
    "type": "XXXX"
  }
}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

## 6.7 合约abi查询

通过input信息查询合约abi

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：`/fisco-bcos-browser/contract/abi/{input}`

c)、请求方式：GET

d)、返回格式：json

### 参数信息详情

| 序号     | 输入参数     | 类型     | 可为空     | 备注                  |
| -------- | ------------ | -------- | ---------- | --------------------- |
| 1        | input        | String   | 否         | 合约方法input信息     |
| **序号** | **输出参数** | **类型** | **可为空** | **备注**              |
| 1        | code         | int      | 否         | 返回码信息请参看附录1 |
| 2        | message      | String   | 否         | 描述                  |
| 3        | data         | object   | 否         | 返回信息实体          |

### 入参事例

http://localhost:5101/fisco-bcos-browser/contract/abi/xxxxx

### 出参示例

**a)、成功：**

```
{
  “code”: 0,
  “message”: “success”,
  “data”: {
    "XXXX"
  }
}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

# 7、用户接口

## 7.1 新增用户

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：`/fisco-bcos-browser/user/add`

c)、请求方式：POST

d)、返回格式：json

### 参数信息详情

| 序号     | 输入参数     | 类型     | 可为空     | 备注                  |
| -------- | ------------ | -------- | ---------- | --------------------- |
| 1        | groupId      | int      | 否         | 所属群组编号          |
| 2        | userName     | String   | 否         | 用户名                |
| 3        | address      | String   | 否         | 用户公钥地址          |
| 4        | description  | String   | 是         | 描述                  |
| **序号** | **输出参数** | **类型** | **可为空** | **备注**              |
| 1        | code         | int      | 否         | 返回码信息请参看附录1 |
| 2        | message      | String   | 否         | 描述                  |
| 3        | data         | object   | 是         | 返回信息实体          |

### 入参事例

http://localhost:5101/fisco-bcos-browser/user/add

```
{
  "address": "0xd93ca98d540b5ed09ef49e9e035ada3d5236ecb0",
  "description": "test",
  "groupId": 1,
  "userName": "alice"
}
```

### 出参示例

**a)、成功：**

```
{
  "code": 0,
  "message": "success",
  "data": {
    "userId": 700001,
    "userName": "alice",
    "groupId": 1,
    "address": "0xd93ca98d540b5ed09ef49e9e035ada3d5236ecb0",
    "description": "test",
    "createTime": "2020-11-13 11:38:19",
    "modifyTime": "2020-11-13 11:38:19"
  }
}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

## 7.2 用户列表

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：`/fisco-bcos-browser/user/userList/{groupId}/{pageNumber}/{pageSize}?userParam={userParam}`

c)、请求方式：GET

d)、返回格式：json

### 参数信息详情

| 序号     | 输入参数     | 类型     | 可为空     | 备注                  |
| -------- | ------------ | -------- | ---------- | --------------------- |
| 1        | groupId      | int      | 否         | 所属群组编号          |
| 2        | pageNumber   | int      | 否         | 当前页码              |
| 3        | pageSize     | int      | 否         | 每页记录数            |
| 4        | userParam    | String   | 是         | 用户名或用户公钥地址  |
| **序号** | **输出参数** | **类型** | **可为空** | **备注**              |
| 1        | code         | int      | 否         | 返回码信息请参看附录1 |
| 2        | message      | String   | 否         | 描述                  |
| 3        | data         | object   | 是         | 返回信息实体          |
| 3.1      |              | List     |            | 信息列表              |
| 3.1.1    | userId       | int      | 否         | 用户编号              |
| 3.1.2    | groupId      | int      | 否         | 所属群组编号          |
| 3.1.3    | userName     | String   | 否         | 用户名                |
| 3.1.4    | address      | String   | 否         | 用户公钥地址          |
| 3.1.5    | description  | String   | 是         | 描述                  |
| 4        | totalCount   | int      | 否         | 总条数                |

### 入参事例

http://localhost:5101/fisco-bcos-browser/user/userList/1/1/1

### 出参示例

**a)、成功：**

```
{
  "code": 0,
  "message": "success",
  "data": [
    {
      "userId": 700001,
      "userName": "alice",
      "groupId": 1,
      "address": "0xd93ca98d540b5ed09ef49e9e035ada3d5236ecb0",
      "description": "test",
      "createTime": "2020-11-13 11:38:19",
      "modifyTime": "2020-11-13 11:38:19"
    }
  ],
  "totalCount": 1
}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

## 7.3 用户更新

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：`/fisco-bcos-browser/user/update`

c)、请求方式：PUT

d)、返回格式：json

### 参数信息详情

| 序号     | 输入参数     | 类型     | 可为空     | 备注                  |
| -------- | ------------ | -------- | ---------- | --------------------- |
| 1        | userId       | int      | 否         | 用户编号              |
| 2        | userName     | String   | 否         | 用户名                |
| 3        | address      | String   | 否         | 用户公钥地址          |
| 4        | description  | String   | 是         | 描述                  |
| **序号** | **输出参数** | **类型** | **可为空** | **备注**              |
| 1        | code         | int      | 否         | 返回码信息请参看附录1 |
| 2        | message      | String   | 否         | 描述                  |
| 3        | data         | object   | 是         | 返回信息实体          |

### 入参事例

http://localhost:5101/fisco-bcos-browser/user/update

```
{
  "address": "0xd93ca98d540b5ed09ef49e9e035ada3d5236ecb0",
  "description": "update",
  "userId": 700001,
  "userName": "alice"
}
```

### 出参示例

**a)、成功：**

```
{
  "code": 0,
  "message": "success",
  "data": {
    "userId": 700001,
    "userName": "alice",
    "groupId": 1,
    "address": "0xd93ca98d540b5ed09ef49e9e035ada3d5236ecb0",
    "description": "update",
    "createTime": "2020-11-13 11:38:19",
    "modifyTime": "2020-11-13 11:52:58"
  }
}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

## 7.4 用户删除

根据用户id删除

### 传输协议规范

a)、网络传输协议：使用HTTP协议

b)、请求地址：`/fisco-bcos-browser/user/deleteById/{userId}`

c)、请求方式：DELETE

d)、返回格式：json

### 参数信息详情

| 序号     | 输入参数     | 类型     | 可为空     | 备注                  |
| -------- | ------------ | -------- | ---------- | --------------------- |
| 1        | userId       | int      | 否         | 用户编号              |
| **序号** | **输出参数** | **类型** | **可为空** | **备注**              |
| 1        | code         | int      | 否         | 返回码信息请参看附录1 |
| 2        | message      | String   | 否         | 描述                  |
| 3        | data         | object   | 是         | 返回信息实体          |

### 入参事例

http://localhost:5101/fisco-bcos-browser/user/deleteById/700001

### 出参示例

**a)、成功：**

```
{
  "code": 0,
  "message": "success",
  "data": null
}
```

**b)、失败：**

```.
{
  "code": 105001,
  "message": "system error",
  "data": null
}
```

# 附录

## 1. 返回码信息列表

| code   | message                                         | 描述                            |
| ------ | ----------------------------------------------- | ------------------------------- |
| 0      | success                                         | 成功                            |
| 105001 | system error                                    | 系统异常                        |
| 105002 | param valid fail                                | 参数校验异常                    |
| 205001 | group name cannot be empty                      | 群组名称不能为空                |
| 205002 | group id cannot be empty                        | 群组编号不能为空                |
| 205003 | node id cannot be empty                         | 节点编号不能为空                |
| 205004 | node ip cannot be empty                         | 节点ip不能为空                  |
| 205005 | node rpcPort cannot be empty                    | 节点rpc端口不能为空             |
| 205006 | node p2pPort cannot be empty                    | 节点p2p端口不能为空             |
| 205007 | user id cannot be empty                         | 用户编号不能为空                |
| 205008 | user name cannot be empty                       | 用户名不能为空                  |
| 205009 | address cannot be empty                         | 用户公钥地址不能为空            |
| 305001 | group id is existed                             | 群组编号已经存在                |
| 305002 | group name is existed                           | 群组名称已经存在                |
| 305003 | rpcPort and p2pPort cannot be same              | 节点rpc端口和p2p端口不能相同    |
| 305004 | ip and rpcPort is existed in this group         | 当前群组已配置该节点ip和rpc端口 |
| 305005 | ip and p2pPort is existed in this group         | 当前群组已配置该节点ip和p2p端口 |
| 305006 | ip format error                                 | ip格式错误                      |
| 305007 | node error or not alive                         | 节点信息错误或未存活            |
| 305008 | node do not belong to this group                | 节点信息不属于当前群组          |
| 305009 | the file is empty!                              | 文件为空                        |
| 305010 | it is not a zip file                            | 非压缩文件                      |
| 305011 | file format error                               | 文件格式错误                    |
| 305012 | zip file can't contain zipfile                  | 压缩包不能包含压缩包            |
| 305013 | folders are not allowed                         | 文件夹错误                      |
| 305014 | folder is already exist                         | 文件夹已存在                    |
| 305015 | node may be abnormal, please confirm            | 节点异常，请确认                |
| 305016 | do not have permission, please check configAuth | 没权限，请检查配置              |
| 305017 | group id not exists                             | 群组编号不存在                  |
| 305018 | request block number is taller than the latest  | 请求块高大于最新的块高          |
| 305101 | user already exists                             | 用户已存在                      |
| 305102 | user id not exists                              | 用户不存在                      |
| 305103 | publickey address is invalid                    | 用户公钥地址错误                |

 