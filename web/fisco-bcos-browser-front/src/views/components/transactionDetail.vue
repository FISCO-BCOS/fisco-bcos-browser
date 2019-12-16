<template>
    <div>
        <div class="search-main padding-bottom-0">
            <div class="container padding-bottom-0">
                <v-nav :page='page' :hrTitle="'交易'" :navContent="PkHash" :navSubtitle="'交易'" :hrcontent="'交易信息'" :route="'transaction'"></v-nav>
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
                                                    <el-table-column prop="data" label="data" align="center" :show-overflow-tooltip="true">
                                                        <template slot-scope="scope">
                                                            <i class="wbs-icon-baocun font-12 copy-public-key" @click="copyPubilcKey(scope.row.data)" title="复制"></i>
                                                            <span>{{scope.row.data}}</span>
                                                        </template>
                                                    </el-table-column>
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
                                        <span class="input-data" v-if="!eventSHow">{{transactionReceiptByPkHash.logs}}</span>
                                        <div class="input-data" v-for="item in eventLog" v-if="eventSHow" :key='item.address'>
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
        /* max-height: 200px; */
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
    import {getTbTransactionByPkHash,getTbTransactionReceiptByPkHash,getContractList,getBytecode,getAbiFunction,getAbi} from '@/api/api'
    // import {getTbTransactionReceiptByPkHash} from '@/api/api'
    import url from '@/api/url'
    import {message} from '@/util/util'
    import constant from '@/util/constant'
    import errorcode from "@/util/errorCode"
    import '@/assets/css/layout.css'
    import '@/assets/css/public.css'
    import web3 from "@/util/ethAbi"
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
                eventSHow: false,
                page: {
                    pageSize: this.$route.pageSize || 10,
                    pageNumber: this.$route.pageNumber || 1
                }
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
                let num1 = 0;
                let num2 = 0;
                if(this.transactionByPkHash.to == "0x0000000000000000000000000000000000000000"){
                    this.getDeloyAbi(input);
                }else{
                    this.getMethod(input)
                    // this.decodefun(input, this.transactionTo);
                }
            },
           
            getMethod: function(id){
                let data = id.substring(0, 10);
               
                getAbiFunction(data).then(res => {
                    if(res.data.code == 0){
                        this.decodefun(id,res.data.data)
                    }else{
                    message(errorcode[res.data.code].cn,'error')
                }
                }).catch(err => {
                    message(constant.ERROR,'error');
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
                    pageNumber: 1,
                    pageSize: 500
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
        copyPubilcKey: function(val) {
            if (!val) {
                this.$message({
                    type: "fail",
                    showClose: true,
                    message: "key为空，不复制。",
                    duration: 2000
                });
            } else {
                this.$copyText(val).then(e => {
                    this.$message({
                        type: "success",
                        showClose: true,
                        message: "复制成功",
                        duration: 2000
                    });
                });
            }
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
        decodefun: function(input,abiData){
            let Web3EthAbi = web3;
            this.methodId = input.substring(0, 10);
            // this.methodId = data;
            let inputDatas = "0x" + input.substring(10);
            if(abiData){
                abiData.abiInfo = JSON.parse(abiData.abiInfo)
                abiData.abiInfo.inputs.forEach((val, index) => {
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
                this.funcData = abiData.abiInfo.name;
                if (abiData.abiInfo.inputs.length) {
                    this.decodeData = Web3EthAbi.decodeParameters( abiData.abiInfo.inputs,inputDatas);
                    if (JSON.stringify(this.decodeData) != "{}") {
                        for (const key in this.decodeData) {
                            abiData.abiInfo.inputs.forEach((val, index) => {
                                if (val && val.name && val.type) {
                                    if (key === val.name) {
                                        this.inputData[index] = {};
                                        this.inputData[index].name = val.name;
                                        this.inputData[index].type = val.type;
                                        this.inputData[index].data = this.decodeData[key];
                                    }
                                } else if (val) {
                                    if (index == key) {
                                        this.inputData[index] = {};
                                        this.inputData[index].type = val;
                                        this.inputData[index].data = this.decodeData[key];
                                    }
                                }
                            });
                        }
                    }
                }
                this.showDecode = true;
                this.buttonTitle = "还原";
            }
        },
        getDeloyAbi: function(val){
            if(val && val != "0x"){
                let data = {
                        input: val.substring(2)
                    }
                getAbi(data).then(res => {
                    if(res.data.code == 0){
                        if(res.data.data){
                            this.decodeDeloy(res.data.data)
                        }
                    }else{
                    message(errorcode[res.data.code].cn,'error');
                }
                }).catch(err => {
                    if(err.response && err.response.code !== 200){
                        message(constant.ERROR,'error');
                    }
                })
            }       
        },
        decodeDeloy: function(items) {
            if (items) {
                let input = JSON.parse(items.contractAbi);
                this.funcData = items.contractName;
                input.forEach(value => {
                    if(value.type == "constructor"){
                        value.inputs.forEach((item,index) => {
                            if (item && item.type && item.name) {
                                this.abiType[index] = item.type + " " + item.name;
                            } else if (item && item.name) {
                                this.abiType[index] = item.name;
                            } else if (item && item.type) {
                                this.abiType[index] = item.type;
                            } else if (item) {
                                this.abiType[index] = item;
                            }
                        })
                    }
                })
                this.showDecode = true;
                this.buttonTitle = "还原";
            }else{
                this.buttonSHow = false;
                this.showDecode = false;
            }
        },
        //decodeEvent
        decodeEventClick: function() {
            this.eventSHow = false;
            if (this.eventLog.length) {
                this.getEventData()
            }
        },
        getEventData: function(){
            for (let i = 0; i < this.eventLog.length; i++) {
                getAbiFunction(this.eventLog[i].topics[0]).then(res => {
                    if(res.data.code == 0 && res.data.data){
                        this.eventLog[i] = this.decodeEvent(res.data.data,this.eventLog[i])
                        setTimeout(() => {
                            this.eventSHow = true;
                        },200)
                    }else if(res.data.code !== 0){
                    message(errorcode[res.data.code].cn,'error')
                }
                }).catch(err => {
                    message(constant.ERROR,'error');
                })
            }
        },
        decodeEvent: function(eventData, data) {
            let Web3EthAbi = web3;
            let abi = "";
            eventData.abiInfo = JSON.parse(eventData.abiInfo)
            let list = data;
                list.eventTitle = '还原'
                list.eventDataShow = true;
                list.eventButtonShow = true;
                list.eventName = eventData.abiInfo.name + "(";
                for (let i = 0; i < eventData.abiInfo.inputs.length; i++) {
                    if (i == eventData.abiInfo.inputs.length - 1) {
                        list.eventName =  list.eventName + eventData.abiInfo.inputs[i].type +" " + eventData.abiInfo.inputs[i].name;
                    }else{
                        list.eventName = list.eventName + eventData.abiInfo.inputs[i].type + " " + eventData.abiInfo.inputs[i].name + ",";
                    }   
                }
                list.eventName = list.eventName + ")";
                let eventResult = Web3EthAbi.decodeLog(eventData.abiInfo.inputs,list.data,list.topics);
                list.outData = {};
                list.eventLgData = [];
                for (const key in eventResult) {
                    if (+key || +key == 0) {
                        list.outData[key] = eventResult[key];
                    }
                }
                if (eventData.abiInfo.inputs.length && JSON.stringify(list.outData) != "{}") {
                    for (const key in list.outData) {
                        eventData.abiInfo.inputs.forEach((items, index) => {
                            if (index == key) {
                                list.eventLgData[index] = {};
                                list.eventLgData[index].name = items.name;
                                list.eventLgData[index].data = list.outData[key];
                            }
                        });
                    }
                }
            return list
        },
    }
    }
</script>
