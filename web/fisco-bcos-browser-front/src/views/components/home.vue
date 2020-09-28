<template>
    <div class="home">
        <div class="container">
            <div class="home-head">
                <!--blockChain Statistics-->
                <div class="home-head-data" >
                    <ul>
                        <li v-for="item in totalStatisticsList"  @click="linkPage(item.route,chainType)" :key="item.label" class="lg-width background-common-color">
                            <div class="bg-filter"></div>
                            <div class="home-head-content header-content-pad">
                                <span class="home-head-data-label">
                                    <svg-icon :icon-class='item.icon' class="font-32"></svg-icon>
                                    {{item.label}}</span>
                                <span class="home-head-data-content"> {{item.value}}</span>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="home-part2">
                <div class="home-foot-box home-block background-common-color">
                    <div class="bg-filter"></div>
                    <div class="home-head-content">
                        <div class="home-foot-box-nav">
                            <div class="left">
                                <svg-icon icon-class='block2' class="font-24"></svg-icon>
                                <span class="text">区块</span>
                            </div>
                            
                        </div>
                        <div class="home-foot-box-content" >
                            <ul>
                                <li class="item" v-for="item in blockList" :key='item.number'>
                                    <div class="item-bg">
                                        <div class="left">
                                            <div class="block-table-link" @click="linkPage('blockDetail','blockHash',item.blockHash)">区块 {{item.number}}</div>
                                            <div class="block-table-data">{{item.dateTimeStr}}</div>
                                        </div>
                                        <div class="right">
                                            <div>出块节点&nbsp&nbsp&nbsp
                                                <span class="block-number" :title="item.sealer">{{item.sealer}}</span>
                                            </div>
                                            <div class="txn" @click="linkPage('transaction','blockHeight',item.number)">{{item.txn}} txns</div>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                        <div class="right">
                            <span @click="linkPage('block')" class="table-link" style="padding-right: 30px;"> 更多 ...</span>
                        </div>
                    </div>
                </div>
                <!--Chart statistics-->
                <div class="home-head-chart background-common-color" ref="chart">
                    <div class="bg-filter"></div>
                    <div class="home-head-content">
                        <div class="home-foot-box-nav">
                            <div class="left">
                                <svg-icon icon-class='transaction15' class="font-24"></svg-icon>
                                <span class="text">近15日交易量</span>
                            </div>
                            
                        </div>
                        <v-chart ref="linechart" :type="'line'" :id="'homeId'" :data="chartStatistics.date" :transactionDataArr="chartStatistics.dataArr" :size="chartStatistics.chartSize" :title="'home'"></v-chart>
                    </div>
                </div>
            </div>
            <!--Node statistics-->
            <div class="home-part2" style="margin-top: 50px">
                 <div class="home-foot-box transaction-data background-common-color">
                    <div class="bg-filter"></div>
                    <div class="home-head-content">
                        <div class="home-foot-box-nav">
                            <div class="left">
                                <svg-icon icon-class='transaction2' class="font-24"></svg-icon>
                                <span class="text">交易</span>
                            </div>
                            
                        </div>
                        <div class="home-foot-box-content" >
                            <ul>
                                <li class="item" v-for="itemlist in transactionList" :key='itemlist.transHash'>
                                    <div class="item-bg">
                                        <div class="left">
                                            <div class="transaction" @click="linkPage( 'transactionDetail','pkHash',itemlist.transHash)">交易 <span class="table-link" :title="itemlist.transHash | toUpperCase">{{itemlist.transHash | toUpperCase}}</span></div>
                                            <div class="block-table-data">
                                                <div>{{itemlist.blockTimesStr}}</div>
                                                <!-- <div>{{itemlist.funcName || ""}}</div> -->
                                            </div>
                                        </div>

                                        <div class="right" style="margin-top: 15px;">
                                            <div class="number" :title="itemlist.from" >{{itemlist.from}}</div>
                                            <svg-icon icon-class='transactionTo' class="font-24"></svg-icon>
                                            <div class="number" :title="itemlist.to">{{itemlist.to}}</div>
                                            
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                        <div class="right" style="padding-bottom: 14px;">
                            <span @click="linkPage('transaction')" class="table-link" style="padding-right: 30px;"> 更多 ...</span>
                        </div>
                    </div>
                </div>
                <div class="home-foot-box node-data background-common-color">
                    <div class="bg-filter"></div>
                    <div class="home-head-content">
                    <div class="home-foot-box-nav">
                        <div class="left">
                            <svg-icon icon-class='node' class="font-24"></svg-icon>
                            <span class="text">节点</span>
                        </div>
                        
                    </div>
                    <ul class="node-ul">
                        <li class="node-title">
                            <div class="node-tile-item node-th1">节点Id</div>
                            <div class="node-tile-item node-th2">当前块高</div>
                            <div class="node-tile-item node-th3">pbftView</div>
                            <div class="node-tile-item node-th4">节点状态</div>
                        </li>
                        <li v-for='item in TbNodesList' :key='item.nodeId'>
                            <div class="node-item-data">
                                <div class="node-tile-item node-th1">
                                    <svg-icon icon-class='nodeName' class="font-16" style="cursor:pointer" @click="copyPubilcKey(scope.row.nodeId)" title="复制"></svg-icon>
                                    <!-- <i class="wbs-icon-baocun copy-public-key" style="font-size: 15px;cursor:pointer"  title="复制"></i> -->
                                    <span class="font-overflow">{{item.nodeId}}</span>
                                </div>
                                <div class="node-tile-item node-th2">{{item.blockNumber}}</div>
                                <div class="node-tile-item node-th3">{{item.pbftView}}</div>
                                <div class="node-tile-item node-th4">
                                    <span v-if="item.status == 0" class="node-button" style="background-image: linear-gradient(46deg, #B4EC51 0%, #429321 100%);">正常</span>
                                    <span v-if="item.status == 1" class="node-button" style="background-image: linear-gradient(65deg, #F16912 0%, #E43B58 100%);">异常</span>
                                    <span v-if="item.status == 2" class="node-button" style="background-image: linear-gradient(65deg, #F16912 0%, #E43B58 100%);">弃用</span>
                                </div>
                            </div>
                            <div style="height: 8px"></div>
                        </li>
                    </ul>
                    <!-- <el-table :data="TbNodesList" :header-cell-style="bgTable" :row-class-name="tableRowClassName"  v-loading="loading3" element-loading-text="数据加载中..." element-loading-background="rgba(0, 0, 0, 0.8)">
                        <el-table-column label="节点Id" :show-overflow-tooltip="true" prop="nodeId" align="center">
                            <template slot-scope="scope">
                                <i class="wbs-icon-baocun copy-public-key" style="font-size: 15px;cursor:pointer" @click="copyPubilcKey(scope.row.nodeId)" title="复制"></i>
                                <span>{{scope.row.nodeId}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="当前块高" :show-overflow-tooltip="true" prop="blockNumber" align="center"></el-table-column>
                        <el-table-column label="pbftView" :show-overflow-tooltip="true" prop="pbftView" align="center"></el-table-column>
                        <el-table-column label="节点状态" :show-overflow-tooltip="true" prop="active" align="center">
                            <template slot-scope="scope">
                                <span v-if="scope.row.status == 0" style="color: #0F0">正常</span>
                                <span v-if="scope.row.status == 1" style="color: #FF0">异常</span>
                                <span v-if="scope.row.status == 2" style="color: #F00">弃用</span>
                            </template>
                        </el-table-column>
                    </el-table> -->
                    </div>
                </div>
            </div>
            <div class="home-foot">
                <!--Block list-->
                
                <!--transaction list-->
               
            </div>
        </div>
    </div>
</template>
<style>
.home {
    width: 100%;
    min-height: calc(100% - 90px);
    /* background-color: #2a2c3b; */
    /* overflow: hidden; */
    box-sizing: border-box;
}
.home-head {
    font-size: 0;
}
.home-head-data {
    /* display: inline-block; */
    width: 100%;
    box-sizing: border-box;
    /* padding: 30px; */
    /* background-color: #3b3e54; */
    vertical-align: middle;
    margin: 0 0 40px 0;
    padding-top: 40px;
}
.lg-width {
    position: relative;
    width: 47%;
}
.home-head-data ul {
    list-style: none;
    margin: 0;
    padding: 0;
    display: flex;
    justify-content: space-between;
}
.home-head-data ul li {
    
    width: 23.2%;
    min-width: 400px;
    height: 140px;
    /* padding: 20px 15px 40px 30px; */
    cursor: pointer;
    color: #fff;
    border-radius: 16px 4px 16px 4px;
    box-sizing: border-box;
    vertical-align: middle;
}
.home-head-content{
    width: 100%;
    height: 100%;
    position: absolute;
    left: 0px;
    top:0px;
    padding: 20px 32px 32px 32px;
    font-size: 14px;
    z-index: 2;
    box-sizing: border-box;
}
.header-content-pad{
    padding: 20px 40px 40px 15px;
}
.home-part2{
    display: flex;
    justify-content: space-between;
}
.home-block{
    position: relative;
    width: 37.2%;
    min-width: 640px;
}
.home-head-chart {
    position: relative;
    width: 60.5%;
    min-width: 1040px;
    height: 340px;
    border-radius: 16px 4px 16px 4px;
    vertical-align: middle;
}
.home-head-chart .home-foot-box-nav{
    /* padding: 20px 0 0 30px; */
    color: #fff
}

.home-head-chart .el-input__inner {
    background-color: #3b3e54;
    color: #fff;
}

.home-head-chart .el-range-input {
    background-color: #3b3e54;
    color: #fff;
}
.el-range-separator {
    color: #fff !important;
}

.home-head-data-label {
    font-size: 16px;
    color: white;
}
.home-head-data-content {
    display: block;
    color: white;
    padding-top: 20px;
    font-family: 'futura';
    font-size: 36px;
    /* font-style: italic;
    font-weight: 500; */
    text-align: right;
}


.node-ul{
    list-style: none;
    width: 100%;
    padding: 0;
    padding-top: 10px;
    font-size: 0;
}
.node-title{
    color: #9B9B9B;
}
.node-tile-item{
    display: inline-block;
    padding-left: 20px;
    font-size: 14px;
    height: 40px;
    line-height: 40px;
    box-sizing: border-box;
}
.node-th1{
    width: 50%;
}
.node-th2{
    width: 15%;
}
.node-th3{
    width: 15%;
}
.node-th4{
    width: 20%;
}
.node-item-data{
    background-image: linear-gradient(49deg, rgba(22, 167, 252,0.2) 0%, rgba(200, 109, 215,0.2) 100%);
    border-radius: 8px;
    border-radius: 8px;
}
.node-button{
    display: inline-block;
    width: 80px;
    height: 20px;
    text-align: center;
    line-height: 20px;
    border-radius: 10px;
    color:  #121212
}




.home-center {
    margin-top: 20px;
    display: flex;
}
.transaction-data{
    position: relative;
    width: 60.5%;
    min-width: 1040px;
}
.node-data{
    position: relative;
    width: 37.2%;
    min-width: 640px;
}
.home-center-table {
    background-color: rgba(69, 54, 187,0.7) !important;
    filter: blur(4);
}
.home-center .el-table .table-style {
    background-color: rgba(69, 54, 187,0.7) !important;
    filter: blur(4);
    color: #fff;
    text-align: center;
    overflow: hidden;
    text-overflow: ellipsis;
    -o-text-overflow: ellipsis;
    -webkit-text-overflow: ellipsis;
    -moz-text-overflow: ellipsis;
    white-space: nowrap;
    border-color: #666;
}
.home-center .el-table .table-style:hover {
    color: #333;
}
.el-table {
    border-color: #666 !important;
}
.el-table__body-wrapper {
    border-color: #666 !important;
}
.el-table table {
    border-color: #666 !important;
}
.el-table tbody {
    border-color: #666 !important;
}
.el-table tr {
    border-color: #666 !important;
}
.el-table th {
    border-color: #666 !important;
}
.el-table td {
    border-color: #666 !important;
}
.el-table--border:after,
.el-table--group:after,
.el-table:before {
    background-color: #666 !important;
}
.home-center .el-table .name-wrapper {
    width: 100%;
    height: 100%;
    display: inline-block;
    /*width: 450px;*/
    overflow: hidden;
    text-overflow: ellipsis !important;
    white-space: nowrap !important;
}
.home-center .el-table .el-table__empty-block {
    background-color: rgba(69, 54, 187,0.7);
    filter: blur(4);
}
.el-table th, .el-table tr{
    background-color: rgba(69, 54, 187,0.7) !important;
    background-image: linear-gradient(49deg, rgba(22, 167, 252,0.2) 0%, rgba(200, 109, 215,0.2) 100%) !important;
    color: #fff
}
.home-foot {
    margin-top: 20px;
    font-size: 0;
}
.home-foot-box {
    padding: 20px 30px 20px 30px;
    /* background-color: #3b3e54; */
    height: 340px;
    font-size: 14px;
    color: #fff;
    vertical-align: top;
    border-radius: 16px 4px 16px 4px;
    border-radius: 16px 4px 16px 4px;
    box-sizing: border-box;
}
.home-foot-box .right{
    float: right;
}
.home-foot-box-nav {
    width: 100%;
    /* height: 50px;
    line-height: 50px; */
    /* margin-left: 15px;
    padding-right: 15px; */
    overflow: hidden;
    box-sizing: border-box;
}
.home-foot-box-nav .left {
    float: left;
    width: 50%;
}
.home-foot-box-nav .left .text{
    font-size: 16px;
}
.home-foot-box-nav .left .line {
    display: inline-block;
    width: 3px;
    height: 14px;
    margin-right: 12px;
    background-color: #2196f3;
    vertical-align: middle;
}
.home-foot-box-nav .right {
    float: right;
    width: 50%;
    font-size: 14px;
    text-align: right;
    cursor: pointer;
}
.home-foot-box-content ul {
    padding: 30px 0 0 0;
    margin: 0;
    list-style: none;
}
.home-foot-box-content .item {
    display: block;
    height: 64px;
    background-image: linear-gradient(49deg, rgba(22, 167, 25,0.2) 0%, rgba(200, 109, 215,0.2) 100%);
    overflow: hidden;
    border-radius: 8px;
    /* line-height: 28px; */
    padding: 10px 15px;
    margin-bottom: 8px;
    box-sizing: border-box;
}
.home-foot-box-content .item .left {
    float: left;
    font-size: 12px;
    opacity: 1;
}
.block-table-link{
    font-size: 16px;
    color: #25CEFE;
    letter-spacing: 0.44px;
    text-align: justify;
    line-height: 16px;
}
.block-table-data{
    padding-top: 15px;
    color: #9B9B9B
}
.home-foot-box-content .item .right {
    float: right;
    text-align: right;
    overflow: hidden;
}
.home-foot-box-content .item .block-number {
    display: inline-block;
    width: 116px;
    overflow: hidden;
    text-overflow: ellipsis !important;
    white-space: nowrap !important;
    vertical-align: middle;
}
.home-foot-box-content .item .txn {
    margin-top: 5px;
    float: right;
    width: 80px;
    height: 20px;
    line-height: 20px;
    background-image: linear-gradient(132deg, #DE22FE 0%, #7C17F7 100%);
    border-radius: 10px;
    color: #121212;
    text-align: center;
    cursor: pointer;
}
.home-foot-box-content .item .transaction {
    width: 320px;
    overflow: hidden;
    font-size: 16px;
    color: #25CEFE;
    letter-spacing: 0.44px;
    text-align: justify;
    line-height: 16px;
    text-overflow: ellipsis !important;
    white-space: nowrap !important;
    vertical-align: middle;
}
.home-foot-box-content .number {
    display: inline-block;
    width: 200px;
    overflow: hidden;
    text-overflow: ellipsis !important;
    white-space: nowrap !important;
    vertical-align: 2px;
}
.home-foot-box-content .image {
    vertical-align: middle;
}

table {
    border-color: #999 !important;
}
.node-false {
    color: #f00 !important;
}
</style>
<script type="text/babel">
import { getTbBlcokChainInfo, getTxnByLastFourteenDay, getTbBlockInfo, getTbTransactionInfo, getTbNodeConnection, getAbiFunction, getTbTransactionByPkHash, getAbi } from '@/api/api'
import { Message } from 'element-ui';
import { message } from '@/util/util'
import { timeState, MonthState, MonthNumber, intiDate } from '@/util/util'
import { goPage } from '@/util/util'
import { spliceData } from '@/util/util'
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
let dataTimeEnd = timeState((new Date()).getTime() - 14 * 24 * 3600 * 1000);
let monthNumber = MonthNumber((new Date()).getTime());

export default {
    name: 'home',
    components: {
        'v-chart': charts
    },
    data: function () {
        return {
            bgTable: {
                backgroundColor: 'rgba(69, 54, 187,1)',
                filter: 'blur(4)',
                color: '#999',
                textAlign: 'center',
                // borderColor: '#666'
            },
            tableCellStyle: {
                width: '450px',
                borderColor: '#666'
            },
            totalStatisticsList: constant.TOTAL_STATISTICS_LIST,
            chartStatistics: {
                chartSize: {
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
            newData: [],
            oladata: ""
        }
    },
    mounted: function () {
        this.$nextTick(function () {
            this.groupId = localStorage.getItem("groupId")
            if (this.groupId) {
                // this.getContracts();
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
        window.localStorage.setItem('project', "");
        window.localStorage.setItem('chain', "");
    },
    watch: {
        setSetIntervaling: function () {
            this.setSetInterval();
        }
    },
    methods: {
        copyPubilcKey: function (val) {
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
        changChart: function (val) {
            this.chartStatistics.time = MonthState(val);
            this.searchTxnByLastFourteenDay()
        },
        dateBlur: function (val) {
            if (val.value && val.value.length) {
                let time1 = (new Date(val.value[0])).getTime();
                let time2 = (new Date(val.value[1])).getTime();
                let times = time2 - time1;
                let monthTime = 29 * 24 * 3600 * 1000;
                if (times > monthTime) {
                    time2 = time1 + monthTime
                }
                val.value[1] = timeState(time2)
            }
        },
        //create timer
        setSetInterval: function () {
            this.setInterval.timer1 = window.setInterval(() => { this.searchTxnByLastFourteenDay() }, constant.INTERVALTIME);
            this.setInterval.timer2 = window.setInterval(() => { this.searchTbBlcokChainInfo() }, constant.INTERVALTIME);
            this.setInterval.timer3 = window.setInterval(() => { this.searchTbNodesInfo() }, constant.INTERVALTIME);
            this.setInterval.timer4 = window.setInterval(() => { this.searchTbBlockInfo() }, constant.INTERVALTIME);
            this.setInterval.timer5 = window.setInterval(() => { this.searchTbTransactionInfo() }, constant.INTERVALTIME);
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
        linkPage: function (name, label, data) {
            return goPage(name, label, data);
        },
        tableRowClassName: function () {
            return 'table-style'
        },
        nodeFalse: function (item) {
            if (item.row.active) {
                if (item.row.active === 'false') {
                    return 'node-false'
                }
            }
        },
        //get blockChain overview ，  start the timer after receiving the correct response
        searchTbBlcokChainInfo: function () {
            this.loading1 = true;
            getTbBlcokChainInfo(this.groupId).then((res) => {
                this.setSetIntervaling = true;
                this.loading1 = false;
                if (res.data.code === 0) {
                    if (res.data.data) {
                        this.totalStatisticsList[0].value = res.data.data.latestNumber;
                        this.totalStatisticsList[1].value = res.data.data.txn;
                        this.totalStatisticsList[2].value = res.data.data.pendingTxn;
                        this.totalStatisticsList[3].value = res.data.data.pbftView
                    } else {
                        for (let i = 0; i < this.totalStatisticsList.length; i++) {
                            this.totalStatisticsList[i].value = 0;
                        }
                    }
                } else {
                    this.$message({
                        type: 'error',
                        message: errorcode[res.data.code].cn
                    })
                    this.$message({
                        type: 'error',
                        message: errorcode[res.data.code].cn
                    });
                    for (let i = 0; i < this.totalStatisticsList.length; i++) {
                        this.totalStatisticsList[i].value = 0;
                    }
                }
            }).catch(err => {
                this.clear();
                this.loading1 = false;
                for (let i = 0; i < this.totalStatisticsList.length; i++) {
                    this.totalStatisticsList[i].value = 0;
                }
                if (err.response && err.response.status !== 200) {
                    message(constant.ERROR, 'error');
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
                dateTimeEnd: dataTimeEnd,
                dateTimeBegin: dateTimeBegin
            };
            getTxnByLastFourteenDay(data, {}).then(res => {
                this.loading2 = false;
                if (res.data.code === 0) {
                    if (res.data.data && res.data.data.length) {
                        res.data.data = intiDate(res.data.data);
                        for (let i = 0; i < res.data.data.length; i++) {
                            this.chartStatistics.date.push(res.data.data[i].dateStr);
                            this.chartStatistics.dataArr.push(res.data.data[i].txn);
                        }
                    } else {
                        res.data.data = intiDate(res.data.data);
                        for (let i = 0; i < res.data.data.length; i++) {
                            this.chartStatistics.date.push(res.data.data[i].dateStr);
                            this.chartStatistics.dataArr.push(res.data.data[i].txn);
                        }
                    }
                    this.chartStatistics.show = true;
                } else {
                    this.chartStatistics.show = true;
                    this.$message({
                        type: 'error',
                        message: errorcode[res.data.code].cn
                    });
                }
            }).catch(err => {
                this.clear();
                this.loading2 = false;
                if (err.response && err.response.status !== 200) {
                    message(constant.ERROR, 'error');
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
            getTbNodeConnection(data, query).then(res => {
                this.loading3 = false;
                if (res.data.code === 0) {
                    if (res.data.data && res.data.data.length) {
                        this.TbNodesList = res.data.data
                    } else {
                        message('无可用节点，请在节点配置添加节点！', 'warning')
                    }
                } else {
                    this.TbNodesList = []
                    this.$message({
                        type: 'error',
                        message: errorcode[res.data.code].cn
                    });
                }
            }).catch(err => {
                this.clear();
                this.loading3 = false;
                if (err.response && err.response.status !== 200) {
                    message(constant.ERROR, 'error');
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
            getTbBlockInfo(data, {}).then(res => {
                this.loading4 = false;
                if (res.data.code === 0) {
                    if (res.data.data && res.data.data.length) {
                        this.blockList = [];
                        this.blockList = res.data.data;
                        if (this.blockList.length > 3) {
                            this.blockList.length = 3
                        }
                    }
                } else {
                    this.$message({
                        type: 'error',
                        message: errorcode[res.data.code].cn
                    })
                }
            }).catch(err => {
                this.clear();
                this.loading4 = false;
                if (err.response && err.response.code !== 200) {
                    message(constant.ERROR, 'error');
                }
            })
        },
        //transaction list, showing only four
        searchTbTransactionInfo: function () {
            this.loading5 = true;
            // this.transactionList = [];
            let list = [];
            let data = {
                groupId: this.groupId,
                pageNumber: 1,
                pageSize: 4
            };
            getTbTransactionInfo(data, {}).then(res => {
                this.loading5 = false;
                if (res.data.code === 0) {
                    if (res.data.data && res.data.data.length) {
                        for (let i = 0; i < res.data.data.length; i++) {
                            res.data.data[i].blockTimesStr = res.data.data[i].blockTimesStr.slice(0, 19);
                            let item = {
                                transHash: res.data.data[i].transHash
                            }
                            list.push(item)
                        }
                        this.transactionList = res.data.data;
                        if (this.transactionList.length > 3) {
                            this.transactionList.length = 3
                        }
                        this.transactionList.forEach((value, index) => {
                            if (value.to) {
                                this.newData[index] = null
                                this.getTransationDetail(value.transHash, 'to', index)
                                // setTimeout(() => {
                                //     this.$set(value,'funcName',this.newData[index])
                                // },400)
                            } else {
                                this.newData[index] = null;
                                this.getTransationDetail(value.transHash, "", index)
                                // setTimeout(() => {
                                //     this.$set(value,'funcName',this.newData[index])
                                // },400)
                            }
                        })
                        // this.GetAnalyzeData(list);
                    }
                } else {
                    this.$message({
                        type: 'error',
                        message: errorcode[res.data.code].cn
                    })
                }
            }).catch(err => {
                this.clear();
                this.loading5 = false;
                if (err.response && err.response.code !== 200) {
                    message(constant.ERROR, 'error');
                }
            })
        },
        getTransationDetail: function (val, type, index) {
            let data = {
                groupId: localStorage.getItem("groupId"),
                transHash: val,
            };
            getTbTransactionByPkHash(data).then(res => {
                if (res.data.code === 0) {
                    if (res.data.data.input && type) {
                        this.getMethod(res.data.data.input, index)

                    } else if (res.data.data.input) {
                        this.getCodeabi(res.data.data.input, index)
                    }
                } else {
                    this.$message({
                        type: 'error',
                        message: errorcode[res.data.code].cn
                    })
                }
            }).catch(err => {
                message(constant.ERROR, 'error');
            })
        },
        getMethod: function (val, index) {
            let funcName = ""
            let data = val.substring(0, 10);
            getAbiFunction(data).then(res => {
                if (res.data.code === 0) {
                    if (res.data.data) {
                        let inputData = JSON.parse(res.data.data.abiInfo)
                        funcName = inputData.name + "("
                        inputData.inputs.forEach((item, indexs) => {
                            if (indexs == inputData.inputs.length - 1) {
                                funcName = funcName + item.type + " " + item.name;
                            } else {
                                funcName = funcName + item.type + " " + item.name + ",";
                            }
                        })
                        funcName = funcName + ")"
                        this.newData[index] = funcName;
                        this.$set(this.transactionList[index], 'funcName', this.newData[index])
                    }
                } else {
                    this.$message({
                        type: 'error',
                        message: errorcode[res.data.code].cn
                    })
                }
            }).catch(err => {
                message(constant.ERROR, 'error');
            })
        },
        getCodeabi: function (val, index) {
            if (val && val != "0x") {
                let data = {
                    input: val.substring(2)
                }
                getAbi(data).then(res => {
                    if (res.data.code == 0) {
                        if (res.data.data) {
                            this.decodeDeloy(res.data.data, index)
                        }
                    } else {
                        this.$message({
                            type: 'error',
                            message: errorcode[res.data.code].cn
                        })
                    }
                }).catch(err => {
                    message(constant.ERROR, 'error');
                })
            }
        },
        decodeDeloy: function (items, index) {
            if (items) {
                let input = JSON.parse(items.contractAbi);
                let funcName = items.contractName + "(";
                input.forEach(value => {
                    if (value.type == "constructor") {
                        value.inputs.forEach((item, indexs) => {
                            if (indexs == value.inputs.length - 1) {
                                funcName = funcName + item.type + " " + item.name;
                            } else {
                                funcName = funcName + item.type + " " + item.name + ",";
                            }
                        })
                    }
                })
                funcName = funcName + ")"
                this.newData[index] = funcName;
                this.$set(this.transactionList[index], 'funcName', this.newData[index])
            }
        },

    },
    filters: {
        toUpperCase: function (value) {
            return value.toUpperCase();
        }
    }
}
</script>
