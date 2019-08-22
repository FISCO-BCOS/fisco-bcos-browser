
<template>
    <div class="search-main" style="height: auto;">
        <div class="container">
            <v-nav :hrTitle="title" :hrcontent="title" :route="'transaction'"></v-nav>
            <div class="search-nav">
                <div class="hashInput">
                    <el-input placeholder="请输入交易哈希或块高" v-model="searchKeyValue" class="input-with-select">
                        <el-button slot="append" icon="el-icon-search" @click="search" :disabled="submitDisabled"></el-button>
                    </el-input>   
                </div>
            </div>
            <div class="search-table">
                <el-table :data="transactionList"  v-loading="loading"
                          element-loading-text="数据加载中..."
                          element-loading-background="rgba(0, 0, 0, 0.8)">
                    <el-table-column prop="pkHash" label="哈希"  :show-overflow-tooltip="true" align="center" min-width="350px">
                        <template slot-scope="scope">
                            <span @click="linkPage('transactionDetail','pkHash',scope.row.transHash)" class="table-link" >{{scope.row.transHash}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="blockNumber" label="所属块" align="center" :show-overflow-tooltip="true" min-width="100px">
                        <template slot-scope="scope">
                            <span @click="linkPage('blockDetail','blockHash',scope.row.blockHash)" class="table-link" >{{scope.row.blockNumber}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="transIndex" label="交易块内ID" align="center" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column prop="blockTimesStr" label="交易时间" align="center" :show-overflow-tooltip="true" min-width="150px"></el-table-column>
                    <el-table-column prop="from" label="发送者"  :show-overflow-tooltip="true" align="center"></el-table-column>
                    <el-table-column   align="center" width="30px">
                        <template>
                            <img src="../../assets/images/s-right.png">
                        </template>
                    </el-table-column>
                    <el-table-column label="接受者"  :show-overflow-tooltip="true" align="center">
                        <template slot-scope="scope">
                            <span>{{scope.row.to}}</span>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="search-pagation">
                    <div style="line-height: 40px">
                        <span>查询结果 : </span>
                        <span>共计{{pagination.total}}条数据</span>
                    </div>
                    <el-pagination
                        layout="sizes,prev, pager, next"
                        :total="pagination.total"
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page.sync="pagination.currentPage"
                        :page-sizes="[10, 20, 30, 50]"
                        :page-size="pagination.pageSize">
                    </el-pagination>
                </div>
            </div>
        </div>
    </div>
</template>
<script type="es6">
    import nav from '@/components/content-nav'
    import {getTbTransactionInfo} from '@/api/api'
    import url from '@/api/url'
    import router from '@/router'
    import constant from '@/util/constant'
    import {message} from '@/util/util'
    import {goPage} from '@/util/util'
    // import router from "@/router"
    import {timeState,MonthState} from '@/util/util'
    import errorcode from "@/util/errorCode"
    import '@/assets/css/layout.css'
    import '@/assets/css/public.css'
    let minMonthDate = null;
    let maxMonthDate = null;
    let months = MonthState((new Date()).getTime())

    export default {
        name: 'transaction',
        components: {
            'v-nav': nav
        },
        data: function () {
            return{
                searchKeyValue: "",
                title: '交易',
                transactionData: this.$route.query.transactionData || "",
                blockHeight: this.$route.query.blockHeight || "",
                // transactionTime: months,
                transactionList: [],
                chainType: this.$route.query.chainType || "01",
                pagination: {
                    currentPage:  this.$route.query.pageNumber || 1,
                    pageSize: this.$route.query.pageSize || 10,
                    total: 0,
                },
                submitDisabled: false,
                loading: false,
                changeDateDisabled: {
                    onPick: function (picker) {
                        minMonthDate = picker.minDate.getTime() - 30 * 24 * 3600 * 1000;
                        maxMonthDate = picker.minDate.getTime() + 30 * 24 * 3600 * 1000;
                    },
                    disabledDate: function (time) {
                        let nowDate = (new Date()).getTime();
                        let Time = time.getTime();
                        if (Time < nowDate || Time === nowDate) {
                            if (minMonthDate) {
                                // debugger
                                if (Time > minMonthDate && Time < maxMonthDate) {
                                    return false
                                } else {
                                    return true
                                }
                            }
                        } else {
                            return true
                        }
                    }
                },
                setIntervalTime: null,
            }
        },
        mounted: function () {
            this.searchTbTransactionInfo();
            this.pagination.currentPage = this.$route.query.pageNumber || 1;
            this.pagination.pageSize = this.$route.query.pageSize || 10;
        },
        beforeDestroy: function () {
            window.clearInterval(this.setIntervalTime);
        },
        methods: {
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
            clear: function () {
                window.clearInterval(this.setIntervalTime);
            },
            linkPage: function (name,label,data) {
                let resData = {
                    pageSize: this.pagination.pageSize,
                    pageNumber: this.pagination.currentPage,
                };
                resData[label] = data
                router.push({
                    name: name,
                    query: resData
                })
            },
            search: function(){
                let reg=/^[0-9]+.?[0-9]*$/;
                if(this.searchKeyValue.length > 60){
                    this.blockHeight = "";
                    this.transactionData = this.searchKeyValue
                }else if(reg.test(this.searchKeyValue) && this.searchKeyValue.substring(0,2) != "0x"){
                    this.blockHeight = this.searchKeyValue;
                    this.transactionData = ""
                }else if(this.searchKeyValue){
                    message("请输入块高或完整的哈希",'error')
                }
                this.searchTbTransactionInfo()
                this.searchKeyValue = "";
                router.push({
                    query: {
                        pageSize: this.pagination.pageSize,
                        pageNumber: this.pagination.currentPage,
                        blockNumber: this.blockHeight,
                        transHash: this.transactionData
                    }
                })
            },
            searchTbTransactionInfo: function () {
                this.submitDisabled = true;
                this.loading = true;
                let data = {
                    groupId: localStorage.getItem("groupId"),
                    pageNumber: this.pagination.currentPage,
                    pageSize: this.pagination.pageSize
                };
                let query = {
                    blockNumber: this.blockHeight,
                    transHash: this.transactionData,
                }
                
                getTbTransactionInfo(data,query).then(res => {
                    window.clearInterval(this.setIntervalTime);
                    this.setIntervalTime = window.setInterval(() => {this.searchTbTransactionInfo()}, constant.INTERVALTIME);
                    this.submitDisabled = false;
                    this.loading = false;
                    if(res.data.code === 0){
                        if(res.data.data && res.data.data.length) {
                            for (let i = 0; i < res.data.data.length; i++) {
                                if(res.data.data[i].blockTimesStr){
                                    res.data.data[i].blockTimesStr = res.data.data[i].blockTimesStr.slice(0, 19);
                                }
                            }
                            this.transactionList = res.data.data;
                            this.pagination.total = res.data.totalCount;
                        }else{
                            this.transactionList = [];
                            this.pagination.totalCount = res.data.totalCount
                        }
                    }else{
                        this.transactionList = [];
                        message(errorcode[res.data.code].cn,'error')
                    }
                    this.blockHeight = "";
                    this.transactionData = "";
                }).catch(err => {
                    this.transactionList = [];
                    this.submitDisabled = false;
                    this.loading = false;
                    if(err.response && err.response.status !== 200){
                        message(constant.ERROR,'error');
                    }
                    window.clearInterval(this.setIntervalTime);
                    this.blockHeight = "";
                    this.transactionData = "";
                });
                minMonthDate = null;
                maxMonthDate = null;
            },
            handleSizeChange(val) {
                this.pagination.pageSize = val;
                this.pagination.currentPage = 1;
                router.push({
                    // name: "transaction",
                    query: {
                        pageSize: this.pagination.pageSize,
                        pageNumber: this.pagination.currentPage,
                        blockNumber: this.blockHeight,
                        transHash: this.transactionData
                    }
                })
                this.searchTbTransactionInfo();
            },
            handleCurrentChange(val) {
                this.pagination.currentPage = val;
                router.push({
                    // name: "transaction",
                    query: {
                        pageSize: this.pagination.pageSize,
                        pageNumber: this.pagination.currentPage,
                        blockNumber: this.blockHeight,
                        transHash: this.transactionData
                    }
                })
                this.searchTbTransactionInfo();
            }
        }
    }
</script>
