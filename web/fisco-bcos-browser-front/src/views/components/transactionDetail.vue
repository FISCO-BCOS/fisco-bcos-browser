<template>
    <div>
        <div class="search-main padding-bottom-0">
            <div class="container padding-bottom-0">
                <v-nav :hrTitle="'交易'" :navContent="PkHash" :navSubtitle="'交易'" :hrcontent="'交易信息'" :route="'transaction'"></v-nav>
            </div>
        </div>
        <div class="hash-content-info-tran">
            <div class="container">
                <div class="content">
                    <el-tabs v-model="activeName" @tab-click="handleClick">
                        <el-tab-pane label="交易信息" name="first">
                            <div class="tranbox">
                                <div v-for="item in transactionData" class="hash-content-label" :key='item.label'>
                                    <span class="label-title">{{item.label}}:</span>
                                    <span>{{item.value}}</span>
                                </div>
                                <div class="hash-content-label">
                                    <span class="label-title">input:</span>
                                    <div class="label-content">
                                        <span v-if="!showDecode" class="input-data">{{transactionByPkHash.input}}</span>
                                        <div v-if="showDecode" class="input-data">
                                            <div class="input-label">
                                                <span class="label">function</span>
                                                <span>{{funcData + "(" + abiType + ")"}}</span>
                                            </div>
                                            <div class="input-label">
                                                <span class="label">methodId</span>
                                                <span>{{methodId}}</span>
                                            </div>
                                            <div class="input-label ">
                                                <span class="label">data</span>
                                                <el-table :data="inputData" v-if="inputData.length" border style="display:inline-block;width:400px">
                                                    <el-table-column prop="name" label="name" align="center" v-if="inputData[0].name"></el-table-column>
                                                    <el-table-column prop="type" label="type" align="center"></el-table-column>
                                                    <el-table-column prop="data" label="data" align="center" :show-overflow-tooltip="true"></el-table-column>
                                                </el-table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="hash-content-label">
                                    <span class="label-title"></span>
                                    <el-button @click="decodeChange" type="primary">{{buttonTitle}}</el-button>
                                </div>
                            </div>
                        </el-tab-pane>
                        <el-tab-pane label="交易回执信息" name="second">
                            <div class="tranbox">
                                <div v-for="item in receiptData" class="hash-content-label" :key='item.label'>
                                    <span class="label-title">{{item.label}}:</span>
                                    <span class="receit-content">{{item.value}}</span>
                                </div>
                                <div class="hash-content-label">
                                    <span class="label-title">logs:</span>
                                    <div class="label-content">
                                        <span class="input-data" v-if="!eventLog.length">{{transactionReceiptByPkHash.logs}}</span>
                                        <div class="input-data" v-for="item in eventLog" v-if="eventLog.length" :key='item.address'>
                                            <div class="item">
                                                <span class="label">Address :</span>
                                                <span>{{item.address}}</span>
                                            </div>
                                            <div class="item">
                                                <span class="label">Name :</span>
                                                <span>{{item.eventName}}</span>
                                            </div>
                                            <div class="item">
                                                <span class="label">Topics :</span>
                                                <div style="display: inline-block;width:800px;">
                                                    <div v-for="(val,index) in item.topics " :key='val'>[{{index}}] {{val}}</div>
                                                </div>
                                            </div>
                                            <div class="item">
                                                <span class="label">Data :</span>
                                                <div class="detail-input-content " v-if='eventContent'>
                                                    <span  v-if="!item.eventDataShow"  class="input-data" style="width:600px;">{{item.data}}</span>
                                                    <el-table class="input-data " border :data="item.eventLgData" v-if="item.eventDataShow" style="display:inline-block;width:400px">
                                                        <el-table-column prop="name" width="150" label="name" align="left"></el-table-column>
                                                        <el-table-column prop="data" label="data" align="left" :show-overflow-tooltip="true">
                                                            <template slot-scope="scope">
                                                                <i class="wbs-icon-baocun font-12 copy-public-key" @click="copyPubilcKey(scope.row.data)" title="复制"></i>
                                                                <span>{{scope.row.data}}</span>
                                                            </template>
                                                        </el-table-column>
                                                    </el-table>
                                                </div>
                                            </div>
                                            <div class="item" v-show='item.eventButtonShow'>
                                                <span class="label"></span>
                                                <el-button @click="decode(item)" type="primary">{{item.eventTitle}}</el-button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </el-tab-pane>
                    </el-tabs>
                </div>
            </div>
        </div>
    </div>
</template>
<style scoped>
    .hash-content-info-tran{
        background-color: #2a2c3b;
        color: #fff;
        font-size: 14px;

    }
    .hash-content-info-tran .box{
        width: 100%;
        padding: 20px 40px;
    }
    .hash-content-info-tran .content{
        width: 100%;
        border-top: 1px solid #999;
        box-sizing: border-box;
        background-color: #3b3e54;
    }
    .hash-content-info-tran .content .title{
        border-bottom: 1px solid #999;
        line-height: 40px;
        padding-top: 20px;
        padding-left: 20px;
    }
    .hash-content-info-tran .pre{
        white-space: pre-wrap!important;
        word-wrap: break-word!important;
        width: calc(100% - 50px)!important;
        font-size: 14px;
        line-height: 24px;
    }
    .hash-content-label{
        padding: 5px 30px;
    }
    .hash-content-label>.label-title{
        display: inline-block;
        width: 120px;
        text-align: left;
        padding-right: 10px;
        box-sizing: border-box;
        vertical-align: top;
        color: #111;
    }
    .hash-content-label>.receit-content{
        display: inline-block;
        width: calc(100% - 300px);
        max-width: 950px;
        white-space: pre-wrap!important;
        word-wrap: break-word!important;
        border: none;
    }
    .label-content{
        display: inline-block;
        width: calc(100% - 300px);
        max-width: 950px;
        height: auto;
        padding: 10px;
        border: 1px solid #eaedf3;
        border-radius: 4px;
        font-size: 12px;
    }
    .input-data {
        display: inline-block;
        width: 100%;
        /* padding: 10px; */
        max-height: 200px;
        overflow: auto;
        word-break: break-all;
        word-wrap: break-word;
        box-sizing: border-box;
    }
    .input-data> .item{
        padding: 5px 0;
        font-size: 12px;
    }
    .input-data> .item> .label{
        display: inline-block;
        color:#111;
        font-weight: bold;
        width: 80px;
        vertical-align: top;
        font-size: 12px;
        vertical-align: top;
    }
    .input-label {
        padding-bottom: 15px;
    }
    .input-label>.label {
        display: inline-block;
        color:#111;
        font-weight: bold;
        width: 80px;
        font-size: 12px;
        vertical-align: top;
    }
    .detail-input-content {
        display: inline-block;
        height: auto;
        border-radius: 4px;
        font-size: 12px;
    }
    .tranbox{
        margin: 20px 30px;
        background-color: #fff;
        color: #42b983;
    }
    .content>>>.is-top{
        color: #fff;
    }
    .content>>>.is-active{
        color: #409EFF;
    }
    .content>>>#tab-first{
        padding-left: 15px;
    }
    .content>>>#tab-second{
        padding-left: 30px;
    }
    .content>>>.el-tabs__active-bar{
        padding-left: 20px;
    }
</style>
<script>
    import nav from '@/components/content-nav'
    import {getTbTransactionByPkHash,getTbTransactionReceiptByPkHash,getContractList,getBytecode} from '@/api/api'
    // import {getTbTransactionReceiptByPkHash} from '@/api/api'
    import url from '@/api/url'
    import {message} from '@/util/util'
    import constant from '@/util/constant'
    import errorcode from "@/util/errorCode"
    import '@/assets/css/layout.css'
    import '@/assets/css/public.css'
    export default {
        name: 'transactionStatistics',
        components: {
            'v-nav': nav
        },
        data: function () {
            return{
                PkHash:  this.$route.query.pkHash,
                transactionByPkHash: "",
                transactionReceiptByPkHash: "",
                noData: false,
                noDataTransaction: false,
                contractList: [],
                groupId: localStorage.getItem("groupId"),
                transactionData: constant.TRANSACTION_ITEM_DATA,
                activeName: 'first',
                receiptData: constant.RECEIPT_ITEM_DATA,
                byteCode: null,
                transactionTo: null,
                methodId: null,
                buttonSHow: false,
                abiType: [],
                decodeData: null,
                inputData: [],
                funcData: null,
                buttonTitle: "解码",
                showDecode: false,
                eventLog: [],
                eventTitle: "解码",
                eventContent: true,
            }
        },
        mounted: function () {
            this.$nextTick(function () {
                this.searchTbTransactionByPkHash();
            });
        },
        methods: {
            decode: function(val){
                this.eventContent = false;
                if(val.eventDataShow){
                    this.$set(val,'eventDataShow',false);
                    this.$set(val,'eventTitle','解码');
                    this.eventContent = true;
                }else{
                    this.$set(val,'eventDataShow',true);
                    this.$set(val,'eventTitle','还原');
                    this.eventContent = true;
                }
            },
            decodeChange: function(){
                if (this.showDecode) {
                    this.buttonTitle = "解码";
                    this.showDecode = false;
                } else {
                    this.buttonTitle = "还原";
                    this.showDecode = true;
                }
            },
            decodeInput: function(){
                let input = this.transactionByPkHash.input;
                this.transactionTo = this.transactionByPkHash.to;
                constant.SYSTEM_CONTRACT_ADDRESS.forEach(value => {
                    if(this.transactionTo == value.contractAddress){
                        this.decodefun(input, this.transactionTo,'system');
                    }else if(this.transactionTo != "0x0000000000000000000000000000000000000000"){
                        this.decodefun(input, this.transactionTo);
                    }else{
                        this.decodeDeloy(input);
                    }
                })
            },
            //Transaction Information Query
            searchTbTransactionByPkHash: function () {
                this.noDataTransaction = false;
                let data = {
                    groupId: localStorage.getItem("groupId"),
                    transHash: this.PkHash,    
                };
                getTbTransactionByPkHash(data).then(res => {
                    if(res.data.code === 0){
                        if(res.data.data) {
                            this.noDataTransaction = false;
                            this.transactionByPkHash = res.data.data;
                            if(res.data.data){
                                this.transactionData.forEach(val => {
                                    val.value = this.transactionByPkHash[val.label]
                                })
                            }
                            this.searchTbTransactionReceiptByPkHash();
                            this.getContracts();
                        }else{
                            this.noData = true
                        }
                    }else{
                        message(errorcode[res.data.code].cn,'error');
                        this.noDataTransaction = true;
                    }
                }).catch(err => {
                    if(err.response && err.response.code !== 200){
                        message(constant.ERROR,'error');
                    }
                    this.noDataTransaction = true;
                })
            },
            //Transaction Receipt Information Query
            searchTbTransactionReceiptByPkHash : function () {
                this.noData = false;
                let data = {
                    groupId: localStorage.getItem("groupId"),
                    transHash: this.PkHash,
                };
                getTbTransactionReceiptByPkHash(data).then(res => {
                    if(res.data.code === 0){
                        if(res.data.data){
                            this.noData = false;
                            this.transactionReceiptByPkHash = res.data.data;
                            if(res.data.data){
                                this.receiptData.forEach(val => {
                                    val.value = this.transactionReceiptByPkHash[val.label]
                                })
                            }
                            if(this.transactionByPkHash.to != "0x0000000000000000000000000000000000000000"){
                                this.getCode(this.transactionByPkHash.to)
                            }else{
                                this.getCode(this.transactionReceiptByPkHash.contractAddress)
                            }
                            this.eventLog = res.data.data.logs;
                            
                        }else{
                            this.noData = true
                        }
                    }else{
                        message(errorcode[res.data.code].cn,'error');
                        this.noData = true
                    }
                }).catch(err => {
                    if(err.response && err.response.code !== 200){
                        message(constant.ERROR,'error');
                    }
                    this.noData = true 
                })
            },
            getContracts: function(){
            let data = {
                    groupId: this.groupId,
                    pageNumber: 1,
                    pageSize: 300
                };
            getContractList(data
            ).then(res => {
                if(res.data.code === 0){
                    this.contractList = res.data.data
                }else{
                    message(errorcode[res.data.code].cn,'error')
                }
            }).catch(err => {
               if(err.response && err.response.code !== 200){
                        message(constant.ERROR,'error');
                    }
            })
        },
        handleClick: function(){
            this.decodeEventClick();
        },

        getCode: function(val){
            let data = {
                groupId: localStorage.getItem("groupId"),
                data: [val]
            }
            getBytecode(data,{}).then(res => {
                if(res.data.code === 0){
                    this.byteCode = res.data.data[0];
                    setTimeout(() => {
                        this.decodeInput();
                    },200)
                }else{
                    message(errorcode[res.data.code].cn,'error');
                }
            }).catch(err => {
                if(err.response && err.response.code !== 200){
                    message(constant.ERROR,'error');
                }
            })
        },
        
        /*decode inputs   
        params @input  string
        params @address string
        params @type string  0ptional   decode system contract
        */
        decodefun: function(input, adr,type){
            let web3 = new Web3(Web3.givenProvider);
            let data = input.substring(0, 10);
            this.methodId = data;
            let inputDatas = "0x" + input.substring(10);
            let abi = null;
            let abiData = {};
            if(type == 'system'){
                if (this.contractList.length) {
                    this.contractList.forEach(value => {
                        if (value.contractAddress == adr){
                            abi = value.contractAbi;
                            this.buttonSHow = true;
                            this.showDecode = true;
                            this.buttonTitle = "还原";
                        }
                    });
                }
            }else{
                let code = this.byteCode.substring(0,2)
                if(code == '0x'){
                    this.byteCode = this.byteCode.substring(2);
                    this.byteCode = this.byteCode.substring(0,this.byteCode.length-68)
                }
                if (this.contractList.length) {
                    this.contractList.forEach(value => {
                        if(!value.show && value.contractBin){
                            value.contractBin = value.contractBin.substring(0,value.contractBin.length-68);
                            value.show = true;
                        }
                        if (value.contractBin == this.byteCode){
                            abi = value.contractAbi;
                            this.buttonSHow = true;
                            this.showDecode = true;
                            this.buttonTitle = "还原";
                        }
                    });
                }
            }
            if(abi){
                abiData = JSON.parse(abi);
                abiData.forEach(value => {
                    value.encode = web3.eth.abi.encodeFunctionSignature({
                        name: value.name,
                        type: value.type,
                        inputs: value.inputs
                    });
                });
                abiData.forEach(value => {
                    if (value.encode === data) {
                        value.inputs.forEach((val, index) => {
                            if (val && val.type && val.name) {
                                this.abiType[index] = val.type + " " + val.name;
                            } else if (val && val.name) {
                                this.abiType[index] = val.name;
                            } else if (val && val.type) {
                                this.abiType[index] = val.type;
                            } else if (val) {
                                this.abiType[index] = val;
                            }
                        });
                        this.funcData = value.name;
                        if (value.inputs.length) {
                            this.decodeData = web3.eth.abi.decodeParameters(
                                value.inputs,
                                inputDatas
                            );
                            if (JSON.stringify(this.decodeData) != "{}") {
                                for (const key in this.decodeData) {
                                    value.inputs.forEach((val, index) => {
                                        if (val && val.name && val.type) {
                                            if (key === val.name) {
                                                this.inputData[index] = {};
                                                this.inputData[index].name =
                                                    val.name;
                                                this.inputData[index].type =
                                                    val.type;
                                                this.inputData[
                                                    index
                                                ].data = this.decodeData[key];
                                            }
                                        } else if (val) {
                                            if (index == key) {
                                                this.inputData[index] = {};
                                                this.inputData[
                                                    index
                                                ].type = val;
                                                this.inputData[
                                                    index
                                                ].data = this.decodeData[key];
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    }
                });
            }
        },
        decodeDeloy: function(items) {
            this.methodId = items.substring(0, 10);
            let abi = "";
            let contractName = "";
            let input = {};
            let code = this.byteCode.substring(0,2)
            if(code == '0x'){
                this.byteCode = this.byteCode.substring(2);
                this.byteCode = this.byteCode.substring(0,this.byteCode.length-68)
            }
            if (this.contractList.length) {
                this.contractList.forEach(value => {
                    if(!value.show && value.contractBin){
                        value.contractBin = value.contractBin.substring(0,value.contractBin.length-68);
                        value.show = true;
                    }
                    if (value.contractBin === this.byteCode) {
                        abi = value.contractAbi;
                        contractName = value.contractName;
                        this.buttonSHow = true;
                        this.showDecode = true;
                        this.buttonTitle = "还原";
                    }
                });
            }
            if (abi) {
                input = JSON.parse(abi);
                if (input.length > 0) {
                    input.forEach(value => {
                        if (value.type === "constructor") {
                            this.funcData = contractName;
                            value.inputs.forEach((item, index) => {
                                if (item && item.type && item.name) {
                                    this.abiType[index] =
                                        item.type + " " + item.name;
                                } else if (item && item.name) {
                                    this.abiType[index] = item.name;
                                } else if (item && item.type) {
                                    this.abiType[index] = item.type;
                                } else if (item) {
                                    this.abiType[index] = item;
                                }
                            });
                        }
                    });
                }
            }else{
                this.buttonSHow = false;
                this.showDecode = false;
            }
        },
        //decodeEvent
        decodeEventClick: function() {
            if (this.eventLog.length) {
                this.eventSHow = true;
                for (let i = 0; i < this.eventLog.length; i++) {
                    let data = {
                        groupId: localStorage.getItem("groupId"),
                        data: [this.eventLog[i].address]
                    }
                    getBytecode(data,{}).then(res => {
                        if(res.data.code === 0){
                            let byteCode = res.data.data[0];
                            this.decodeEvent(byteCode,this.eventLog[i],i)
                        }else{
                            message(errorcode[res.data.code].cn,'error');
                        }
                    }).catch(err => {
                        if(err.response && err.response.code !== 200){
                            message(constant.ERROR,'error');
                        }
                    })
                }
            } else {
                this.eventSHow = false;
            }
        },
        decodeEvent: function(byteCode, data, index) {
            let web3 = new Web3(Web3.givenProvider);
            let abi = "";
            let list = data;
            let code = byteCode.substring(0,2)
            if(code == '0x'){
                byteCode = byteCode.substring(2);
                byteCode = byteCode.substring(0,byteCode.length-68)
            }
            for (let i = 0; i < this.eventLog.length; i++) {
                let num = 0;
                this.contractList.forEach(val => {
                    if(!val.show && val.contractBin){
                        val.contractBin = val.contractBin.substring(0,val.contractBin.length-68);
                        val.show = true;
                    }
                    if (val.contractBin === byteCode) {
                        if (val.contractAbi) {
                            list.abi = JSON.parse(val.contractAbi);
                        } else {
                            list.abi = [];
                        }
                    } else {
                        num++;
                    }
                });
                list.eventTitle = '还原'
                list.eventDataShow = true;
                list.eventButtonShow = true;
                if (num == this.contractList.length) {
                    list.eventDataShow = false;
                    list.eventButtonShow = false;
                }
            }
            if (list.abi && list.abi.length) {
                list.abi.forEach(value => {
                    if (value.type == "event") {
                        list.eventName = value.name + "(";
                        for (let i = 0; i < value.inputs.length; i++) {
                            if (i == value.inputs.length - 1) {
                                list.eventName =
                                    list.eventName +
                                    value.inputs[i].type +
                                    " " +
                                    value.inputs[i].name;
                            } else {
                                list.eventName =
                                    list.eventName +
                                    value.inputs[i].type +
                                    " " +
                                    value.inputs[i].name +
                                    ",";
                            }
                        }
                        list.eventName = list.eventName + ")";
                        let eventData = web3.eth.abi.decodeLog(
                            value.inputs,
                            list.data,
                            list.topics
                        );
                        list.outData = {};
                        list.eventLgData = [];
                        for (const key in eventData) {
                            if (+key || +key == 0) {
                                list.outData[key] = eventData[key];
                            }
                        }
                        if (
                            value.inputs.length &&
                            JSON.stringify(list.outData) != "{}"
                        ) {
                            for (const key in list.outData) {
                                value.inputs.forEach((items, index) => {
                                    if (index == key) {
                                        list.eventLgData[index] = {};
                                        list.eventLgData[index].name =
                                            items.name;
                                        list.eventLgData[index].data =
                                            list.outData[key];
                                    }
                                });
                            }
                        }
                    }
                });
            }
            this.$set(this.eventLog, index, list);
        },
    }
    }
</script>
