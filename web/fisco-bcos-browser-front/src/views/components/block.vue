<template>
    <div class="search-main" style="height: auto;">
        <div class="container">
           <v-nav :hrTitle="btitle" :hrcontent="btitle" :route="'block'"></v-nav>
            <div class="search-nav">
                <div class="hashInput">
                    <el-input placeholder="请输入区块哈希或块高" v-model="searchKeyValue" class="input-with-select">
                        <el-button slot="append" icon="el-icon-search" @click="search" :disabled="submitDisabled"></el-button>
                    </el-input>
                </div>
            </div>
            <div class="search-table">
                <el-table :data="blockList"  v-loading="loading" element-loading-text="数据加载中..." element-loading-background="rgba(0, 0, 0, 0.8)">
                    <el-table-column prop="number" label="块高" align="center" :class-name="'table-link'" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <span @click="linkPage('blockDetail','blockHash',scope.row.blockHash)">{{scope.row.number}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="dateTimeStr" label="生成时间" min-width="120px" align="center" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column prop="txn" label="交易数量" align="center" :class-name="'table-link'" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <span @click="linkPage('transaction','blockHeight',scope.row.number)">{{scope.row.txn}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="sealer" label="出块者" min-width="100px" :show-overflow-tooltip="true" align="center"></el-table-column>
                    <el-table-column prop="pkHash" label="哈希" min-width="350px" :show-overflow-tooltip="true" align="center" :class-name="'table-link'">
                        <template slot-scope="scope">
                            <span @click="linkPage('blockDetail','blockHash',scope.row.blockHash)">{{scope.row.blockHash}}</span>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="search-pagation">
                    <div style="line-height: 40px;">
                    <span>查询结果 : </span>
                    <span>共计{{pagination.total}}条数据</span>
                    </div>
                    <el-pagination style="display: inline-block"
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
<style>
</style>
<script type="es6">
    import nav from '@/components/content-nav'
    import {getTbBlockInfo} from '@/api/api'
    import url from '@/api/url'
    import router from '@/router'
    import constant from '@/util/constant'
    import {message} from '@/util/util'
    import {timeState,MonthState} from '@/util/util'
    import {goPage} from '@/util/util'
    import errorcode from "@/util/errorCode"
    import '@/assets/css/layout.css'
    import '@/assets/css/public.css'

    let minMonthDate = null;
    let maxMonthDate = null;
    let months = MonthState((new Date()).getTime())
    export default {
        name: 'block',
        components: {
            'v-nav': nav
        },
        data: function () {
            return{
                searchKeyValue: "",
                groupId: null,
                btitle: '区块',
                hashData: this.$route.query.blockData || "",
                blockList: [],
                pagination: {
                    currentPage:  this.$route.query.pageNumber || 1,
                    pageSize: this.$route.query.pageSize || 10,
                    total: 0,
                },
                submitDisabled: false,
                loading: false,
                blockNumber: null,
                setIntervalTime: null,
            }
        },
        mounted: function () {
            this.groupId = localStorage.getItem("groupId")
            this.searchTbBlockInfo();
            this.pagination.currentPage = this.$route.query.pageNumber || 1;
            this.pagination.pageSize = this.$route.query.pageSize || 10;
        },
        beforeDestroy: function () {
            this.clear()
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
                    pageSize: this.pageSize,
                    pageNumber: this.pageNumber,
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
                    this.hashData = this.searchKeyValue;
                    this.blockNumber = ""
                }else if(reg.test(this.searchKeyValue) && this.searchKeyValue.substring(0,2) != "0x"){
                    this.hashData = "";
                    this.blockNumber = this.searchKeyValue
                }else if(this.searchKeyValue){
                    message("请输入块高或完整的哈希",'error')
                }
                this.searchTbBlockInfo();
                this.searchKeyValue = "";
                router.push({
                    // name: "block",
                    query: {
                        pageSize: this.pagination.pageSize,
                        pageNumber: this.pagination.currentPage,
                        blockNumber: this.blockNumber,
                        blockHash: this.hashData,
                    }
                })
            },
            searchTbBlockInfo: function () {
                this.submitDisabled = true;
                this.loading = true;
                let data = {
                    groupId: this.groupId,
                    pageNumber: this.pagination.currentPage,
                    pageSize: this.pagination.pageSize
                };
                let query = {
                    blockNumber: this.blockNumber,
                    blockHash: this.hashData,
                }
               getTbBlockInfo(data,query).then(res => {
                    window.clearInterval(this.setIntervalTime);
                    this.setIntervalTime = window.setInterval(() => {this.searchTbBlockInfo()}, constant.INTERVALTIME);
                    this.submitDisabled = false;
                    this.loading = false;
                    if(res.data.code === 0){
                        if(res.data.data && res.data.data.length) {
                            for (let i = 0; i < res.data.data.length; i++) {
                                if(res.data.data[i].dateTimeStr){
                                    res.data.data[i].dateTimeStr = res.data.data[i].dateTimeStr.slice(0, 19);
                                }
                            }
                            this.blockList = res.data.data;
                            this.pagination.total = res.data.totalCount;

                        }else{
                            this.blockList = [];
                            this.pagination.total = res.data.totalCount;
                        }
                    }else{
                        window.clearInterval(this.setIntervalTime);
                        this.blockList = [];
                        message(errorcode[res.data.code].cn,'error')
                    }
                    this.blockNumber = "";
                    this.hashData = "";
                }).catch(err => {
                    window.clearInterval(this.setIntervalTime);
                    this.blockList = [];
                    this.submitDisabled = false;
                    this.loading = false;
                    if(err.response && err.response.code !== 200){
                        message(constant.ERROR,'error');
                    }
                    this.clear();
                    this.blockNumber = "";
                    this.hashData = "";
                });
                minMonthDate = null;
                maxMonthDate = null;
            },
            handleSizeChange(val) {
                this.pagination.pageSize = val;
                this.pagination.currentPage = 1;
                router.push({
                    // name: "block",
                    query: {
                        pageSize: this.pagination.pageSize,
                        pageNumber: this.pagination.currentPage,
                        blockNumber: this.blockNumber,
                        blockHash: this.hashData,
                    }
                })
                this.searchTbBlockInfo();
            },
            handleCurrentChange(val) {
                this.pagination.currentPage = val;
                router.push({
                    // name: "block",
                    query: {
                        pageSize: this.pagination.pageSize,
                        pageNumber: this.pagination.currentPage,
                        blockNumber: this.blockNumber,
                        blockHash: this.hashData,
                    }
                })
                this.searchTbBlockInfo();
            }
        }
    }
</script>
