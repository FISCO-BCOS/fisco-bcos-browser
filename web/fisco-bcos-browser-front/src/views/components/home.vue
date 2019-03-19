<template>
    <div class="home">
        <div class="container">
            <div class="home-head">
                <!--blockChain Statistics-->
                <div class="home-head-data margin-right-10"
                     v-loading="loading1" element-loading-text="数据加载中..."  element-loading-background="rgba(0, 0, 0, 0.8)">
                    <ul>
                        <li v-for="item in totalStatisticsList" :class="item.class" @click="linkPage(item.route,chainType)" :key="item.label" class="lg-width">
                            <span class="home-head-data-label">{{item.label}}</span>
                            <span class="home-head-data-content">{{item.value}}</span>
                        </li>
                    </ul>
                </div>
                <!--Chart statistics-->
                <div class="home-head-chart home-head-data margin-left-10" ref="chart" v-loading="loading2"  element-loading-text="数据加载中..." element-loading-background="rgba(0, 0, 0, 0.8)">
                    <v-chart ref="linechart" :type="'line'" :id="'homeId'" :data="chartStatistics.date"
                             :transactionDataArr="chartStatistics.dataArr" :size="chartStatistics.chartSize" :title="'home'"></v-chart>
                </div>
            </div>
           <!--Node statistics-->
            <div class="home-center">
                <el-table :data="TbNodesList"  :header-cell-style="bgTable" :row-class-name="tableRowClassName" :cell-style="tableCellStyle"
                          v-loading="loading3" element-loading-text="数据加载中..."  element-loading-background="rgba(0, 0, 0, 0.8)">
                    <el-table-column  label="节点Id"  :show-overflow-tooltip="true" prop="nodeId" align="center"></el-table-column>
                    <el-table-column  label="当前块高"  :show-overflow-tooltip="true" prop="blockNumber" align="center"></el-table-column>
                    <el-table-column  label="pbftView"  :show-overflow-tooltip="true" prop="pbftView" align="center"></el-table-column>
                    <el-table-column label="节点状态"  :show-overflow-tooltip="true" prop="active" align="center">
                        <template slot-scope="scope">
                            <span v-if="scope.row.status == 0" style="color: #0F0">正常</span>
                            <span v-if="scope.row.status == 1" style="color: #FF0">异常</span>
                            <span v-if="scope.row.status == 2" style="color: #F00">弃用</span>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div class="home-foot">
                <!--Block list-->
                <div class="home-foot-box margin-right-10">
                    <div class="home-foot-box-nav">
                        <div class="left">
                            <span class="line"></span>
                            <span class="text">区块</span>
                        </div>
                        <div class="right">
                            <span @click="linkPage('block')" class="table-link" style="padding-right: 30px"> 更多 ></span>
                        </div>
                    </div>
                    <div class="home-foot-box-content" v-loading="loading4" element-loading-text="数据加载中..." element-loading-background="rgba(0, 0, 0, 0.8)">
                        <ul>
                            <li class="item" v-for="item in blockList" :key='item.number'>
                                <div class="left">
                                    <div @click="linkPage('blockDetail','blockHash',item.blockHash)" class="table-link">区块 {{item.number}}</div>
                                    <div>{{item.dateTimeStr}}</div>
                                </div>
                                <div class="right">
                                    <div>出块节点&nbsp&nbsp&nbsp
                                        <span class="block-number" :title="item.sealer">{{item.sealer}}</span>
                                    </div>
                                    <div class="txn" @click="linkPage('transaction','blockHeight',item.number)">{{item.txn}} txns</div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                <!--transaction list-->
                <div class="home-foot-box margin-left-10">
                    <div class="home-foot-box-nav">
                        <div class="left">
                            <span class="line"></span>
                            <span class="text">交易</span>
                        </div>
                        <div class="right">
                            <span @click="linkPage('transaction')" class="table-link" style="padding-right: 30px"> 更多 ></span>
                        </div>
                    </div>
                    <div class="home-foot-box-content" v-loading="loading5" element-loading-text="数据加载中..." element-loading-background="rgba(0, 0, 0, 0.8)">
                        <ul>
                            <li class="item" v-for="itemlist in transactionList" :key='itemlist.blockTimesStr'>
                                <div class="left">
                                    <div class="transaction" @click="linkPage( 'transactionDetail','pkHash',itemlist.transHash)">交易 <span class="table-link" :title="itemlist.transHash | toUpperCase">{{itemlist.transHash | toUpperCase}}</span></div>
                                    <div>
                                        <div class="number" :title="itemlist.from">{{itemlist.from}}</div>
                                        <img src="../../assets/images/s-right.png" class="image">
                                        <div class="number" :title="itemlist.to">{{itemlist.to}}</div>
                                    </div>
                                </div>

                                <div class="right">
                                    <div>{{itemlist.blockTimesStr}}</div>
                                    <div>{{itemlist.funcName || ""}}</div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<style>
    .home{
        width: 100%;
        background-color: #2a2c3b;
    }
    .home-head{
        font-size: 0;
    }
    .home-head-data{
        display: inline-block;
        width: calc(41% - 10px);
        box-sizing: border-box;
        padding: 30px;
        background-color: #3b3e54;
        vertical-align: middle;
    }
        .lg-width{
            width: 245px;
        }
    .home-head-data ul{
        list-style: none;
        margin: 0;
        padding: 0;
    }
    .home-head-data ul li{
        display: inline-block;
        height: 110px;
        padding: 20px;
        font-size: 14px;
        cursor: pointer;
        color: #fff;
        box-sizing: border-box;
    }


    .home-head-chart{
        width: calc(59% - 10px) !important;
        height: 310px;
        vertical-align: middle;
    }

    .home-head-chart .el-input__inner{
        background-color: #3b3e54;
        color: #fff;
    }

    .home-head-chart .el-range-input{
        background-color: #3b3e54;
        color: #fff;
    }
   .el-range-separator{
        color: #fff !important;
    }



    .home-head-data-label{
        color: white;
    }
    .home-head-data-content{
        display: block;
        color: white;
        font-size: 46px;
        text-align: right;
    }
    .home-center{
        margin-top: 20px;
    }
    .home-center-table{
        background-color: #3b3e54;
    }
    .home-center .el-table .table-style{
        background-color: #3b3e54;
        color: #fff;
        text-align: center;
        overflow: hidden;
        text-overflow: ellipsis;
        -o-text-overflow: ellipsis;
        -webkit-text-overflow: ellipsis;
        -moz-text-overflow: ellipsis;
        white-space: nowrap; 
        border-color: #666
    }
    .home-center .el-table .table-style:hover{
        color: #333;
    }
    .el-table{
        border-color: #666 !important;
    }
    .el-table__body-wrapper{
        border-color: #666 !important;
    }
    .el-table table{
        border-color: #666 !important;
    }
    .el-table tbody{
        border-color: #666 !important;
    }
    .el-table tr{
        border-color: #666 !important;
    }
    .el-table th{
        border-color: #666 !important;
    }
    .el-table td{
        border-color: #666 !important;
    }
    .el-table--border:after, .el-table--group:after, .el-table:before{
        background-color: #666 !important;
    }
    .home-center .el-table .name-wrapper{
        width: 100%;
        height: 100%;
        display: inline-block;
        /*width: 450px;*/
        overflow: hidden;
        text-overflow: ellipsis !important;
        white-space: nowrap !important; 
    }
    .home-center .el-table .el-table__empty-block{
        background-color: #3b3e54;
    }
    .home-foot{
        margin-top: 20px;
        font-size: 0;
    }
    .home-foot-box{
        display: inline-block;
        width: calc(50% - 10px);
        padding-bottom: 20px;
        background-color: #3b3e54;
        font-size: 14px;
        color: #fff;
        vertical-align: top;
        box-sizing: border-box;
    }
    .home-foot-box-nav{
        width: 100%;
        height: 50px;
        line-height: 50px;
        margin-left: 15px;
        padding-right: 15px;
        overflow: hidden;
        box-sizing: border-box;
    }
    .home-foot-box-nav .left{
        float: left;
        width: 50%;
    }
    .home-foot-box-nav .left .line{
        display: inline-block;
        width: 3px;
        height: 14px;
        margin-right: 12px;
        background-color: #2196f3;
        vertical-align: middle;
    }
    .home-foot-box-nav .right{
        float: right;
        width: 50%;
        text-align: right;
        cursor: pointer;
    }
    .home-foot-box-content ul{
        padding: 0;
        margin: 0;
        list-style: none;
    }
    .home-foot-box-content .item{
        border-bottom: 1px solid #999;
        overflow: hidden;
        line-height: 28px;
        padding: 12px 30px;
    }
    .home-foot-box-content .item .left{
       float: left;
    }
    .home-foot-box-content .item .right{
        float: right;
        text-align: right;
        overflow: hidden;
    }
    .home-foot-box-content .item .block-number{
        display: inline-block;
        width: 116px;
        overflow: hidden;
        text-overflow: ellipsis !important;
        white-space: nowrap !important;  
        vertical-align: middle;
    }
    .home-foot-box-content .item .txn {
        float: right;
        width: 70px;
        height: 28px;
        line-height: 28px;
        background-color: #2196f3;
        color: #fff;
        text-align: center;
        cursor: pointer;
    }
    .home-foot-box-content .item .transaction{
        width: 320px;
        overflow: hidden;
        text-overflow: ellipsis !important;
        white-space: nowrap !important;  
        vertical-align: middle;
    }
    .home-foot-box-content .number{
        display: inline-block;
        width: 148px;
        overflow: hidden;
        text-overflow: ellipsis !important;
        white-space: nowrap !important;  
        vertical-align: middle;
    }
    .home-foot-box-content .image{
        vertical-align: middle;
    }

    table{
        border-color: #999!important;
    }
    .node-false{
        color: #f00 !important;
    }
</style>
<script type="text/babel">
    import {getTbBlcokChainInfo,getTxnByLastFourteenDay,getTbBlockInfo,getTbTransactionInfo,getTbNodeConnection,getAnalyzeData,getBytecode,getContractList} from '@/api/api'
    import { Message } from 'element-ui';
    import {message} from '@/util/util'
    import {timeState,MonthState,MonthNumber,intiDate} from '@/util/util'
    import {goPage} from '@/util/util'
    import {spliceData} from '@/util/util'
    import url from '@/api/url'
    import charts from '@/components/chart'
    import router from '@/router'
    import constant from '@/util/constant'
    import errorcode from "@/util/errorCode"
    import '@/assets/css/layout.css'
    import '@/assets/css/public.css'
    let minMonthDate = null;
    let maxMonthDate = null;
    let dateTimeBegin = timeState((new Date()).getTime());
    let dataTimeEnd = timeState((new Date()).getTime()-14*24*3600*1000);
    let monthNumber = MonthNumber((new Date()).getTime());

    export default {
        name: 'home',
        components: {
            'v-chart': charts
        },
        data: function () {
            return {
                bgTable: {
                    backgroundColor: '#3b3e54',
                    color: '#999',
                    textAlign: 'center',
                    borderColor: '#666'
                },
                tableCellStyle: {
                    width: '450px',
                    borderColor: '#666'
                },
                totalStatisticsList: constant.TOTAL_STATISTICS_LIST,
                chartStatistics: {
                    chartSize:{
                        width: 0,
                        height: 0
                    },
                    date: [],
                    dataArr: [],
                    show: false,
                    // time: months,
                },
                TbNodesList: [],
                blockList: [],
                transactionList: [],
                loading1: false,
                loading2: false,
                loading3: false,
                loading4: false,
                loading5: false,
                chainType: this.$route.query.chainType || "01",
                setInterval: {
                    timer1: null,
                    timer2: null,
                    timer3: null,
                    timer4: null,
                    timer5: null,
                },
                setSetIntervaling: false,
                groupId: null,
                tranList: [],
                codeList: [],
            }
        },
        mounted: function () {
            this.$nextTick(function () {
                this.groupId = localStorage.getItem("groupId")
                if(this.groupId){
                    this.getContracts();
                    this.chartStatistics.chartSize.width = this.$refs.chart.offsetWidth;
                    this.chartStatistics.chartSize.height = this.$refs.chart.offsetHeight;
                    this.searchTxnByLastFourteenDay();
                    this.searchTbBlcokChainInfo();
                    this.searchTbNodesInfo();
                    this.searchTbBlockInfo();
                    this.searchTbTransactionInfo();
                }    
            });
        },
        //Clear all timers before component destruction
        beforeDestroy: function () {
            this.clear();
            window.localStorage.setItem('project',"");
            window.localStorage.setItem('chain',"");
        },
        watch: {
            setSetIntervaling: function () {
                this.setSetInterval();
            }
        },
        methods: {
            changChart: function (val) {
                this.chartStatistics.time = MonthState(val);
                this.searchTxnByLastFourteenDay()
            },
            dateBlur: function (val) {
                if(val.value && val.value.length){
                    let time1 = (new Date(val.value[0])).getTime();
                    let time2 = (new Date(val.value[1])).getTime();
                    let times = time2 - time1;
                    let monthTime = 29*24*3600*1000;
                    if(times > monthTime){
                        time2 = time1 + monthTime
                    }
                   val.value[1] = timeState(time2)
                }
            },
            //create timer
            setSetInterval: function () {
                this.setInterval.timer1 = window.setInterval(() => {this.searchTxnByLastFourteenDay()}, constant.INTERVALTIME);
                this.setInterval.timer2 = window.setInterval(() => {this.searchTbBlcokChainInfo()}, constant.INTERVALTIME);
                this.setInterval.timer3 = window.setInterval(() => {this.searchTbNodesInfo()}, constant.INTERVALTIME);
                this.setInterval.timer4 = window.setInterval(() => {this.searchTbBlockInfo()}, constant.INTERVALTIME);
                this.setInterval.timer5 = window.setInterval(() => {this.searchTbTransactionInfo()}, constant.INTERVALTIME);
            },
            //clear timer
            clear: function () {
                window.clearInterval(this.setInterval.timer1);
                window.clearInterval(this.setInterval.timer2);
                window.clearInterval(this.setInterval.timer3);
                window.clearInterval(this.setInterval.timer4);
                window.clearInterval(this.setInterval.timer5);
                Message.closeAll()
            },
            //Page Jump
            linkPage: function (name,label,data) {
              return goPage(name,label,data);
            },
            tableRowClassName: function () {
              return 'table-style'
            },
            nodeFalse: function (item) {
                if(item.row.active){
                    if(item.row.active === 'false'){
                        return 'node-false'
                    }
                }
            },
            //get blockChain overview ，  start the timer after receiving the correct response
            searchTbBlcokChainInfo : function () {
                this.loading1 = true;
                getTbBlcokChainInfo(this.groupId).then((res) =>{
                    this.setSetIntervaling = true;
                    this.loading1 = false;
                    if(res.data.code === 0){
                        if(res.data.data){
                            this.totalStatisticsList[0].value = res.data.data.latestNumber;
                            this.totalStatisticsList[1].value = res.data.data.txn;
                            this.totalStatisticsList[2].value = res.data.data.pendingTxn;
                            this.totalStatisticsList[3].value = res.data.data.pbftView
                        }else{
                            for (let i = 0; i < this.totalStatisticsList.length; i++){
                                this.totalStatisticsList[i].value = 0;
                            }
                        }
                    }else{
                        message(errorcode[res.data.code].cn,'error');
                        for (let i = 0; i < this.totalStatisticsList.length; i++){
                            this.totalStatisticsList[i].value = 0;
                        }
                    }
                }).catch(err => {
                    this.clear();
                    this.loading1 = false;
                    for (let i = 0; i < this.totalStatisticsList.length; i++){
                        this.totalStatisticsList[i].value = 0;
                    }
                    if(err.response && err.response.status !== 200){
                        message(constant.ERROR,'error');
                    }
                })
            },
            //The amount of transaction information to obtain the current group for a certain period of time
            searchTxnByLastFourteenDay: function () {
                this.chartStatistics.show = false;
                this.loading2 = true;
                this.chartStatistics.date = [];
                this.chartStatistics.dataArr = [];
                let data = {
                    groupId: this.groupId,
                    dateTimeEnd:dataTimeEnd,
                    dateTimeBegin: dateTimeBegin
                };
                getTxnByLastFourteenDay(data,{}).then( res => {
                    this.loading2 = false;
                    if(res.data.code === 0) {
                        if (res.data.data && res.data.data.length){
                            res.data.data = intiDate(res.data.data);
                            for (let i = 0; i < res.data.data.length; i++) {
                                this.chartStatistics.date.push(res.data.data[i].dateStr);
                                this.chartStatistics.dataArr.push(res.data.data[i].txn);
                            }
                        }else{
                            res.data.data = intiDate(res.data.data);
                            for (let i = 0; i < res.data.data.length; i++) {
                                this.chartStatistics.date.push(res.data.data[i].dateStr);
                                this.chartStatistics.dataArr.push(res.data.data[i].txn);
                            }
                        }
                        this.chartStatistics.show = true;
                    }else{
                        this.chartStatistics.show = true;
                        message(errorcode[res.data.code].cn,'error');
                    }
                }).catch(err => {
                    this.clear();
                    this.loading2 = false;
                    if(err.response && err.response.status !== 200){
                        message(constant.ERROR,'error');
                    }
                    this.chartStatistics.show = true;
                })
            },
            dateChange: function () {
                this.searchTxnByLastFourteenDay();
                minMonthDate = null;
                maxMonthDate = null;
            },
            //Node Query
            searchTbNodesInfo: function () {
                this.loading3 = true;
                let data = {
                    groupId: this.groupId,
                    pageSize: 1,
                    pageNumber: 100
                };
                let query = {
                    type: 1,
                }
                getTbNodeConnection(data,query).then(res =>{
                    this.loading3 = false;
                    if(res.data.code === 0){
                        if(res.data.data&&res.data.data.length) {
                            this.TbNodesList = res.data.data
                        }else{
                            message('无可用节点，请在节点配置添加节点！','warning')
                        }
                    }else{
                        this.TbNodesList = []
                        message(errorcode[res.data.code].cn,'error');
                    }
                }).catch(err => {
                    this.clear();
                    this.loading3 = false;
                    if(err.response && err.response.status !== 200){
                        message(constant.ERROR,'error');
                    }
                })
            },
            //Block list, showing only four
            searchTbBlockInfo: function () {
                this.loading4 = true;
                let data = {
                    groupId: this.groupId,
                    pageNumber: 1,
                    pageSize: 4
                };
                getTbBlockInfo(data,{}).then(res => {
                    this.loading4 = false;
                    if(res.data.code === 0){
                        if(res.data.data && res.data.data.length) {
                            this.blockList = [];
                            this.blockList = res.data.data;
                            if(this.blockList.length > 4){
                                this.blockList.length = 4
                            }
                        }
                    }else{
                        message(errorcode[res.data.code].cn,'error')
                    }
                }).catch(err => {
                    this.clear();
                    this.loading4 = false;
                    if(err.response && err.response.code !== 200){
                        message(constant.ERROR,'error');
                    }
                })
            },
            //transaction list, showing only four
            searchTbTransactionInfo: function () {
                this.loading5 = true;
                this.transactionList = [];
                let list = [];
                let data = {
                    groupId: this.groupId,
                    pageNumber: 1,
                    pageSize: 4
                };
                getTbTransactionInfo(data,{}).then(res =>{
                    this.loading5 = false;
                    if(res.data.code === 0){
                        if(res.data.data&&res.data.data.length) {
                            for (let i = 0; i < res.data.data.length; i++) {
                                res.data.data[i].blockTimesStr = res.data.data[i].blockTimesStr.slice(0, 19);
                                let item = {
                                    transHash: res.data.data[i].transHash
                                }
                                list.push(item)
                            }
                            this.transactionList = res.data.data;
                            if(this.transactionList.length > 4){
                                this.transactionList.length = 4
                            }
                            this.GetAnalyzeData(list);
                        }
                    }else{
                        message(errorcode[res.data.code].cn,'error')
                    }
                }).catch(err => {
                    this.clear();
                    this.loading5 = false;
                    if(err.response && err.response.code !== 200){
                        message(constant.ERROR,'error');
                    }
                })
            },
            GetAnalyzeData: function(list){
                let data = {
                    groupId: this.groupId,
                    data: list
                }
                getAnalyzeData(data).then(res => {
                    if(res.data.code === 0){
                        this.tranList = res.data.data
                        if(res.data.data.length){
                           this.decode(); 
                        }
                    }else{
                        message(errorcode[res.data.code].cn,'error')
                    }
                }).catch(err => {
                    message(constant.ERROR,'error');
                })
            },
            getContracts: function(){
                let data = {
                    groupId: this.groupId,
                    pageNumber: 1,
                    pageSize: 300
                };
                getContractList(data).then(res => {
                    if(res.data.code === 0){
                        this.contractList = res.data.data;
                        
                    }else{
                        message(errorcode[res.data.code].cn,'error')
                    }
                }).catch(err => {
                    message(constant.ERROR,'error');
                })
            },
            decode: function(){
                let list = [];
                let arry = [];
                this.tranList.forEach(value => {
                    if(value && value.transactionFromChain.to){
                        constant.SYSTEM_CONTRACT_ADDRESS.forEach(item => {
                            if(value.transactionFromChain.to == item.contractAddress){
                                this.decodeInputs(value.transactionFromChain.hash,value.transactionFromChain.input,value.transactionFromChain.to,'system');
                            }else if(value.transactionFromChain.to != "0x0000000000000000000000000000000000000000" ){
                                list.push(value);
                                arry.push(value.transactionFromChain.to)
                            }else{
                                list.push(value);
                                arry.push(value.receiptFromChain.contractAddress)
                            }
                        })
                    }
                });
                if(list.length && arry.length){
                    this.getCodeList(arry,list)
                }
                
               
            },
            decodeInputs: function(hash,input,adr,type){
                let web3 = new Web3(Web3.givenProvider);
                let abi = "";
                let abiData = [];
                let data = input.substring(0, 10);
                let funcData = "";
                if(type){
                    if (this.contractList.length) {
                        this.contractList.forEach(value => {
                            if (value.contractAddress == adr){
                                abi = value.contractAbi;
                            }
                        });
                    }
                }else{
                    let code = adr.substring(0,2)
                    if(code == '0x'){
                        adr = adr.substring(2);
                        adr = adr.substring(0,adr.length-68)
                    }
                    if (this.contractList.length) {
                        this.contractList.forEach(value => {
                            if(!value.show && value.contractBin){
                                value.contractBin = value.contractBin.substring(0,value.contractBin.length-68);
                                value.show = true;
                            }
                            if (value.contractBin == adr){
                                abi = value.contractAbi;
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
                            funcData = value.name;
                            if(value.inputs.length){
                                funcData = funcData + '(';
                                value.inputs.forEach((items,index) => {
                                    funcData = `${funcData}${items.type} ${items.name},`
                                })
                                funcData = funcData.substring(0,funcData.length-1) + ")"
                            }
                        }
                    })
                };
                if(funcData){
                    this.transactionList.forEach((value,index) => {
                        if(value.transHash == hash){
                            this.$set(this.transactionList[index],'funcName',funcData)
                        }
                    })
                }
            },
            decodeDeloy: function(hash,input,bin){
                let data = input.substring(0, 10);
                let abi = "";
                let contractName = "";
                let code = bin.substring(0,2);
                let funcData = '';
                let abiData = [];
                if(code == '0x'){
                    bin = bin.substring(2);
                    bin = bin.substring(0,bin.length-68)
                }
                if (this.contractList.length) {
                    this.contractList.forEach(value => {
                        if(!value.show && value.contractBin){
                            value.contractBin = value.contractBin.substring(0,value.contractBin.length-68);
                            value.show = true;
                        }
                        if (value.contractBin === bin) {
                            abi = value.contractAbi;
                            funcData = value.contractName;
                        }
                    });
                }
                if(abi){
                    abiData = JSON.parse(abi);
                    abiData.forEach(value => {
                        if(value.type == 'constructor'){
                           if(value.inputs.length){
                                funcData = funcData + '('
                                value.inputs.forEach((items,index) => {
                                    funcData = `${funcData}${items.type} ${items.name},`
                                })
                                funcData = funcData.substring(0,funcData.length-1) + ")"
                            } 
                        }
                    })
                }
                if(funcData){
                    this.transactionList.forEach((value,index) => {
                        if(value.transHash == hash){
                            this.$set(this.transactionList[index],'funcName',funcData)
                        }
                    })
                }
            },
            getCodeList: function(list,itemList){
                let data = {
                    groupId: this.groupId,
                    data: list
                }
                getBytecode(data).then(res => {
                    if(res.data.code === 0){
                        this.codeList = res.data.data;
                        itemList.forEach((value,index) => {
                            value.bin = this.codeList[index];
                            if(value.transactionFromChain.to != '0x0000000000000000000000000000000000000000'){
                                this.decodeInputs(value.transactionFromChain.hash,value.transactionFromChain.input,value.bin)
                            }else{
                               this.decodeDeloy(value.transactionFromChain.hash,value.transactionFromChain.input,value.bin) 
                            }
                            
                        });

                    }else{
                        message(errorcode[res.data.code].cn,'error')
                    }
                }).catch(err => {
                    message(constant.ERROR,'error');
                })
            },

        },
        filters: {
            toUpperCase: function(value){
                return value.toUpperCase();
            }
        }
    }
</script>
