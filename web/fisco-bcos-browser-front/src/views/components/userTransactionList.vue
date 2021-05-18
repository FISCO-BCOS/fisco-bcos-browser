<template>
    <div>
        <div class="search-main padding-bottom-0">
            <div class="container padding-bottom-0">
                <v-nav :page='page' :navSubtitle="'用户'" :hrcontent="'交易列表'" :route="'chainUser'"></v-nav>
            </div>
            <div class="search-main" style="height: auto;">
                <div class="container"> 
                    <div class="search-nav">
                         <div class="hashInput">
                            <el-input placeholder="请输入哈希" v-model="searchKeyValue" class="input-with-select">
                                <el-button slot="append" icon="el-icon-search" @click="search" :disabled="submitDisabled"></el-button>
                            </el-input>
                     </div>
                    </div>
                    <div class="search-table">
                    <el-table :data="list"  v-loading="loading" element-loading-text="数据加载中..." element-loading-background="rgba(0, 0, 0, 0.8)">
                            <el-table-column prop="pkHash" label="哈希"  :show-overflow-tooltip="true" align="center" min-width="350px">
                                    <template slot-scope="scope">
                                    <span @click="linkPage('transactionDetail','pkHash','userTransaction',scope.row.transHash)" class="table-link" >{{scope.row.transHash}}</span>
                                </template>
                            </el-table-column>

                            <el-table-column prop="blockNumber" label="所属块" align="center" :show-overflow-tooltip="true" min-width="100px">
                                <template slot-scope="scope">
                                    <span @click="linkPage('blockDetail','blockHash','userTransaction',scope.row.blockHash)" class="table-link" >{{scope.row.blockNumber}}</span>
                                </template>
                            </el-table-column>

                             <el-table-column   label="交易块内ID" align="center" :show-overflow-tooltip="true">
                                <template slot-scope="scope">
                                    <span  v-if="scope.row.transIndex == 0">-</span>
                                    <span  v-else>{{scope.row.transIndex}}</span>
                                </template>
                             </el-table-column> 

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
        </div> 
    </div>
</template>
<style scoped>
    .content>>>.jv-code{
        max-height: none;
    }
</style>
<script>
    import nav from '@/components/content-nav'
    import {getChainUserList} from '@/api/api'
    import url from '@/api/url'
    import {message} from '@/util/util'
    import constant from '@/util/constant'
    import errorcode from "@/util/errorCode"
     import router from '@/router'
    import '@/assets/css/layout.css'
    import '@/assets/css/public.css'

    export default {
        name: 'chainUserList',
        components: {
            'v-nav': nav,
        },
        data: function () {
            return {
                page:{},
                list: [],
                pagination: {
                    currentPage:  this.$route.query.pageNumber || 1,
                    pageSize: this.$route.query.pageSize || 10,
                    total: 0,
                },
                 page: {
                    pageSize: this.$route.pageSize || 10,
                    pageNumber: this.$route.pageNumber || 1
                },
                searchKeyValue:'',
                address:this.$route.query.param,
                submitDisabled: false,
                loading: false,
                blockNumber: null,
                setIntervalTime: null, 
            }
        },
        mounted: function () {
            this.groupId = localStorage.getItem("groupId")
            this.searchTransationInfo();
            this.pagination.currentPage = this.$route.query.pageNumber || 1;
            this.pagination.pageSize = this.$route.query.pageSize || 10; 
        },
        beforeDestroy: function () {
            this.clear()
        },
        created: function () {
            
        },
         
        methods: {
               clear: function () {
                window.clearInterval(this.setIntervalTime);
            },
            linkPage: function (name,label,v,data) {
                let resData = { 
                    param: this.address,
                    pageSize: this.pagination.pageSize,
                    pageNumber: this.pagination.currentPage,
                    v_page:v,
                };
                resData[label] = data
                router.push({
                    name: name,
                    query: resData
                })
            },

            search: function(){
                this.pagination.currentPage = 1;
                this.searchTransationInfo(); 
                router.push({
                    // name: "block",
                    query: {
                        pageSize: this.pagination.pageSize,
                        pageNumber: this.pagination.currentPage,
                        address: this.address
                    }
                })
            },
            searchTransationInfo: function () { 
                this.submitDisabled = true;
                this.loading = true;
             
                let data = {
                    groupId: this.groupId,
                    address: this.address,
                    pageNumber: this.pagination.currentPage,
                    pageSize: this.pagination.pageSize
                }; 
                let query = {
                     userParam: this.searchKeyValue.trim()
                }
               getChainUserList(data,query).then(res => {  
                    window.clearInterval(this.setIntervalTime);
                    this.setIntervalTime = window.setInterval(() => {this.searchTransationInfo()}, constant.INTERVALTIME);
                    this.submitDisabled = false;
                    this.loading = false;
                    if(res.data.code === 0){
                        if(res.data.data && res.data.data.length) {
                            for (let i = 0; i < res.data.data.length; i++) {
                                if(res.data.data[i].dateTimeStr){
                                    res.data.data[i].dateTimeStr = res.data.data[i].dateTimeStr.slice(0, 19);
                                }
                            }
                            this.list = res.data.data;
                            this.pagination.total = res.data.totalCount;

                        }else{
                            this.list = [];
                            this.pagination.total = res.data.totalCount;
                        }
                    }else{
                        window.clearInterval(this.setIntervalTime);
                        this.list = [];
                        this.pagination.total = 0;
                        message(errorcode[res.data.code].cn,'error')
                    }
                    this.blockNumber = "";
                    this.hashData = "";
                }).catch(err => {
                    window.clearInterval(this.setIntervalTime);
                    this.list = [];
                    this.submitDisabled = false;
                    this.loading = false;
                    if(err.response && err.response.code !== 200){
                        message(constant.ERROR,'error');
                    }
                    this.clear();
                    this.blockNumber = "";
                    this.hashData = "";
                });
            },
            handleSizeChange(val) { 
                this.pagination.pageSize = val;
                this.pagination.currentPage = 1;
                router.push({
                    // name: "block",
                    query: {
                        pageSize: this.pagination.pageSize,
                        pageNumber: this.pagination.currentPage, 
                    }
                })
                this.searchTransationInfo();
            },
            handleCurrentChange(val) {  
                this.pagination.currentPage = val; 
                router.push({
                    // name: "block",
                    query: {
                        pageSize: this.pagination.pageSize,
                        pageNumber: this.pagination.currentPage
                    }
                })
              
                this.searchTransationInfo();
            }
        }
    }
</script>
