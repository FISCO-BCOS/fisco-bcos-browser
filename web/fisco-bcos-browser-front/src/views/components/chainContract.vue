<template>
    <div class="search-main" style="height: auto;">
        <div class="container">
           <v-nav :hrTitle="btitle" :hrcontent="btitle" :route="'chainContract'"></v-nav>
            <div class="search-nav">
                <div class="hashInput">
                    <el-input placeholder="请输入合约地址" v-model="searchKeyValue" class="input-with-select">
                        <el-button slot="append" icon="el-icon-search" @click="search" :disabled="submitDisabled"></el-button>
                    </el-input>
                </div>
            </div>
            <div class="search-table">
                <el-table :data="chainContractList"  v-loading="loading" element-loading-text="数据加载中..." element-loading-background="rgba(0, 0, 0, 0.8)">
                  
                    <el-table-column prop="contractAddress" label="合约地址" min-width="100px" :show-overflow-tooltip="true" align="center"></el-table-column>

                    <el-table-column prop="transFrom" label="合约创建者" min-width="100px" :show-overflow-tooltip="true" align="center"></el-table-column>

                    <el-table-column prop="blockHeight" label="块高" min-width="100px" :show-overflow-tooltip="true" align="center"></el-table-column>

                   <el-table-column prop="transHash" label="交易hash" min-width="100px" :show-overflow-tooltip="true" align="center"></el-table-column>

                   <el-table-column prop="blockTimeStr" label="合约创建时间" min-width="100px" :show-overflow-tooltip="true" align="center"></el-table-column>
                 
                    <el-table-column prop="pkHash" label="操作" min-width="100px" :show-overflow-tooltip="true" align="center" :class-name="'table-link'">
                        <template slot-scope="scope">
                            <span @click="linkPage('contractTransactionList','param',scope.row.contractAddress)">交易列表查看</span>
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
    import {getChainContarctInfo} from '@/api/api'
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
        name: 'chainContract',
        components: {
            'v-nav': nav
        },
        data: function () {
            return{
                searchKeyValue: "",
                groupId: null,
                btitle: '合约信息',
                hashData: this.$route.query.blockData || "",
                chainContractList: [],
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
            this.searchChainContractInfo();
            this.pagination.currentPage = this.$route.query.pageNumber || 1;
            this.pagination.pageSize = this.$route.query.pageSize || 10;
        },
        beforeDestroy: function () {
            this.clear()
        },
        methods: {
            clear: function () {
                window.clearInterval(this.setIntervalTime);
            },
            linkPage: function (name,label,data) {
                let resData = {
                    pageSize: this.pageSize,
                    pageNumber: this.pageNumber 
                };
                resData[label] = data 
                router.push({
                    name: name,
                    query: resData
                })
            },
            search: function(){
                this.pagination.currentPage = 1;
                this.searchChainContractInfo(); 
                router.push({
                    // name: "block",
                    query: {
                        pageSize: this.pagination.pageSize,
                        pageNumber: this.pagination.currentPage,
                        contractAddress: this.searchKeyValue
                    }
                })
            },
            searchChainContractInfo: function () {
                this.submitDisabled = true;
                this.loading = true;
                let data = {
                    groupId: this.groupId,
                    pageNumber: this.pagination.currentPage,
                    pageSize: this.pagination.pageSize
                };
                let query = {
                   contractAddress: this.searchKeyValue.trim()
                }
               getChainContarctInfo(data,query).then(res => {
                    window.clearInterval(this.setIntervalTime);
                    this.setIntervalTime = window.setInterval(() => {this.searchChainContractInfo()}, constant.INTERVALTIME);
                    this.submitDisabled = false;
                    this.loading = false;
                    if(res.data.code === 0){
                        if(res.data.data && res.data.data.length) {
                            for (let i = 0; i < res.data.data.length; i++) {
                                if(res.data.data[i].dateTimeStr){
                                    res.data.data[i].dateTimeStr = res.data.data[i].dateTimeStr.slice(0, 19);
                                }
                            }
                            this.chainContractList = res.data.data;
                            this.pagination.total = res.data.totalCount;

                        }else{
                            this.chainContractList = [];
                            this.pagination.total = res.data.totalCount;
                        }
                    }else{
                        window.clearInterval(this.setIntervalTime);
                        this.chainContractList = [];
                        this.pagination.total = 0;
                        message(errorcode[res.data.code].cn,'error')
                    }
                    this.blockNumber = "";
                    this.hashData = "";
                }).catch(err => {
                    window.clearInterval(this.setIntervalTime);
                    this.chainContractList = [];
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
                        contractAddress: this.searchKeyValue
                    }
                })
                this.searchChainContractInfo();
            },
            handleCurrentChange(val) {
                this.pagination.currentPage = val;
                router.push({
                    // name: "block",
                    query: {
                        pageSize: this.pagination.pageSize,
                        pageNumber: this.pagination.currentPage,
                         contractAddress: this.searchKeyValue
                    }
                })
                this.searchChainContractInfo();
            }
        }
    }
</script>
