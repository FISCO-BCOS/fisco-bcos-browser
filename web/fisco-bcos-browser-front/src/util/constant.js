export default {
    //首页统计块列表常量
    TOTAL_STATISTICS_LIST: [
        {
            label: '当前块高',
            route: 'block',
            class: 'bg-8693f3 margin-right-15 margin-bottom-15',
            value: 0
        },
        {
            label: '交易总量',
            route: 'transaction',
            class: 'bg-bc8dee margin-left-15 margin-bottom-15',
            value: 0
        },
        {
            label: '正在处理的交易数',
            route: '',
            class: 'bg-ffa897 margin-top-15 margin-right-15',
            value: 0
        },
        {
            label: 'PBFT 当前视图',
            route: '',
            class: 'bg-89c3f8 margin-top-15  margin-left-15',
            value: 0
        }
    ],
    MENU_LIST: [
        {
            title: '配置',
            subMenu: [
                {
                    title: '群组配置',
                    name: 'groupConfig',
                },
                {
                    title: '节点配置',
                    name: 'nodeConfig',
                },
                {
                    title: '合约配置',
                    name: 'contractConfig',
                },
                
            ]
        },
        {
            title: '区块链信息',
            subMenu: [
                {
                    title: '概览',
                    name: 'home',
                },
                {
                    title: '查看区块',
                    name: 'block',
                },
                {
                    title: '查看交易',
                    name: 'transaction',
                },
                // {
                //     title: '正在处理的交易',
                //     name: 'pendingTransaction',
                // },
                // {
                //     title: '统计指标',
                //     name: 'singleStatistics',
                // },
                // {
                //     title: '交易流',
                //     name: 'transactionStatistics'
                // },
                // {
                //     title: '共识流',
                //     name: 'blockStatistics',
                // }
            ]
        }
    ],
//    error info
    ERROR: '服务器内部错误',

    DELETE_NODE_SUCCESS: '删除节点成功',

    ADD_NODE_SUCCESS: '新增节点成功',

    EDIT_NODE_SUCCESS: '修改节点成功',

    DELETE_CHAIN_SUCCESS: '删除区块链成功',

    DELETE_PROJECT_SUCCESS: '删除项目成功',

    ADD_GROUP_SUCCESS: '新增群组成功',

    INTERVALTIME: 20000,

    CHAIN_OPTION: [
        {
            label: '联盟委员链',
            value: "00"
        },
        {
            label: '业务存证链',
            value: "01"
        },
    ],
    LIVE: {
        "0": "否",
        "1": "是",
    },
    TRANSACTION_ITEM_DATA:  [
        {
            label: "blockHash",
            value: null
        },{
            label:"blockNumber",
            value: null
        },{
            label:"gas",
            value: null
        },{
            label:"from",
            value: null
        },{
            label:"transactionIndex",
            value: null
        },{
            label:"to",
            value: null
        },{
            label:"nonce",
            value: null
        },{
            label:"value",
            value: null
        },{
            label:"hash",
            value: null
        },{
            label:"gasPrice",
            value: null
        }
    ],
    RECEIPT_ITEM_DATA: [
        {
            label: "output",
            value: null
        },{
            label:"blockHash",
            value: null
        },{
            label:"gasUsed",
            value: null
        },{
            label:"blockNumber",
            value: null
        },{
            label:"contractAddress",
            value: null
        },{
            label:"from",
            value: null
        },{
            label:"transactionIndex",
            value: null
        },{
            label:"to",
            value: null
        },{
            label:"logsBloom",
            value: null
        },{
            label:"transactionHash",
            value: null
        },{
            label:"status",
            value: null
        }
    ],
    SYSTEM_CONTRACT_ADDRESS: [
        {
            contractName: 'Authority',
            contractAddress: '0x0000000000000000000000000000000000001005'
        },
        {
            contractName: 'CNS',
            contractAddress: '0x0000000000000000000000000000000000001004'
        },
        {
            contractName: 'SystemConfig',
            contractAddress: '0x0000000000000000000000000000000000001000'
        },
        {
            contractName: 'Consensus',
            contractAddress: '0x0000000000000000000000000000000000001003'
        }
    ]
}


