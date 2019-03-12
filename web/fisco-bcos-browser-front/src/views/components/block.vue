<template>
    <div class="search-main">
        <div class="container">
           <v-nav :hrTitle="btitle" :hrcontent="btitle" :route="'block'"></v-nav>
            <div class="search-nav">
                <div class="hashInput">
                    <span>哈希值 : </span>
                    <el-input v-model="hashData" class="input" maxlength="128"></el-input>
                    <el-tooltip content="不支持模糊查询" placement="top">
                        <i class="el-icon-info iconInfo"></i>
                    </el-tooltip>
                </div>
                <div class="hashInput">
                    <span>块高：</span>
                    <el-input v-model="blockNumber" class="input"  maxlength="128"></el-input>
                </div>
                <div class="hashInput">
                    <el-button type="primary" @click="searchTbBlockInfo"  :disabled="submitDisabled"><i class="el-icon-search"></i> 查询</el-button>
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
                groupId: null,
                btitle: '区块',
                hashData: this.$route.query.blockData || "",
                blockList: [],
                pagination: {
                    currentPage: 1,
                    pageSize: 10,
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
                return goPage(name,label,data);
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
                this.searchTbBlockInfo();
            },
            handleCurrentChange(val) {
                this.pagination.currentPage = val;
                this.searchTbBlockInfo();
            }
        }
    }
</script>
