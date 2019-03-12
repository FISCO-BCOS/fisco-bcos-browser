var Mock = require('mockjs');

Mock.mock('/fisco-cc-browser-page/home/getTbBlockChainInfo.json',function (req,res) {
    return {
        "status":0,
        "msg":"success",
        "data":{"pkId":1,
            "lastBlock":17,
            "transactionNumber":17,
            "pendingTxn":0,
            "pbftView":9768,
            "avgTime":100.00
        }
    }
});

Mock.mock('/fisco-cc-browser-page/home/getTbNodesInfoByPage.json',function (req,res) {
    return {
        "status":0,
        "msg":"success",
        "total":2,
        "pageTotal":1,
        "pageSize":10,
        "pageNumber":1,
        "start":0,
        "list":[
            {
                "pkId":"1111111111111111111111111111111111111111111111111111111111",
                "addr":"0.0.0.0:0",
                "blockNumber":17,
                "active":"true"
            },
            {
                "pkId":"1111111111111111111111111111111111111111111111111111111111",
                "addr":"0.0.0.0:0",
                "blockNumber":17,
                "active":"true"
            }
            ]
    }
});

Mock.mock('/fisco-cc-browser-page/home/getTxnByLastFourteenDay.json',function (req,res) {
    return {
        "status":0,
        "msg":"success",
        "data": [
            {
                "pkDateStr":"5/12",
                "transactionNumber":3
            },
            {
                "pkDateStr":"5/14",
                "transactionNumber":6
            },
            {
                "pkDateStr":"5/15",
                "transactionNumber":3
            },
            {
                "pkDateStr":"5/16",
                "transactionNumber":4
            },
            {
                "pkDateStr":"5/21",
                "transactionNumber":1
            }
            ]
    }
});




