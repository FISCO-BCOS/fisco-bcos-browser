<template>
    <div class="search-main">
        <div class="container" style="padding-top: 40px;">
           <!-- <v-nav :hrTitle="btitle" :hrcontent="btitle" :route="'block'"></v-nav> -->
           <div class="background-common-color block-data">
               <!-- <div class="bg-filter"></div>
                <div class="home-head-content"> -->
               <div class="block-header-title">
                   <svg-icon icon-class='block2' class="font-24"></svg-icon>
                   <span class="block-text">区块</span>
               </div>
            <div class="search-nav">
                <div class="hashInput">
                    <el-input placeholder="请输入区块哈希或块高" v-model="searchKeyValue" class="input-with-select">
                        <svg-icon icon-class='search' slot='prefix' class="font-24 search-position" @click="search" :disabled="submitDisabled"></svg-icon>
                        <!-- <el-button slot="append" icon="el-icon-search" @click="search" :disabled="submitDisabled"></el-button> -->
                    </el-input>
                </div>
            </div>
            <div class="search-table">
                <ul class="block-ul">
                        <li class="block-title">
                            <div class="block-tile-item block-th1">块高</div>
                            <div class="block-tile-item block-th2">生成时间</div>
                            <div class="block-tile-item block-th3">交易数量</div>
                            <div class="block-tile-item block-th4">出块者</div>
                            <div class="block-tile-item block-th5">哈希</div>
                        </li>
                        <li v-for='item in blockList' :key='item.blockHash' class="block-content">
                            <div class="block-item-data">
                                <div class="block-tile-item block-th1">
                                    <span class="cursor" @click="linkPage('blockDetail','blockHash',item.blockHash)">{{item.number}}</span>
                                </div>
                                <div class="block-tile-item block-th2">{{item.dateTimeStr}}</div>
                                <div class="block-tile-item block-th3">
                                    <span class="cursor" @click="linkPage('transaction','blockHeight',item.number)">{{item.txn}}</span>
                                </div>
                                <div class="block-tile-item block-th4">
                                    <span class="font-overflow1">{{item.sealer}}</span>
                                </div>
                                <div class="block-tile-item block-th5">
                                    <span class="cursor" @click="linkPage('blockDetail','blockHash',item.blockHash)">{{item.blockHash}}</span>
                                </div>
                            </div>
                            <div style="height: 8px"></div>
                        </li>
                    </ul>
                <div class="search-pagation">
                    <span class="search-pagation-total">共计{{pagination.total}}条数据</span>
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
        <!-- </div> -->
    </div>
</template>
<style scoped>
.block-data{
    position: relative;
    height: 100%;
    padding: 20px 30px;
    background-color: rgba(69, 54, 187,0.5);
    border-radius: 16px 4px 16px 4px;
    overflow: auto;
}
.block-header-title{
    padding-bottom: 10px;
}
.block-text{
    color: #fff;
    font-size: 16px;
}
.block-ul{
    list-style: none;
    width: 100%;
    padding: 0;
    padding-top: 10px;
    font-size: 0;
    color: #fff;
}
.block-title{
    color: #9B9B9B;
}
.block-content{
    font-size: 0;
    box-sizing: border-box;
}
.block-tile-item{
    display: inline-block;
    padding-left: 20px;
    font-size: 14px;
    height: 40px;
    line-height: 40px;
    box-sizing: border-box;
}
.block-th1{
    width: 10%;
}
.block-th2{
    width: 20%;
}
.block-th3{
    width: 10%;
}
.block-th4{
    width: 20%;
}
.block-th5{
    width: 40%;
}
.block-item-data{
    background-image: url("../../assets/images/list-bg-1.svg");
    background-size: 100% 100%;
    /* background-image: linear-gradient(49deg, rgba(22, 167, 252,0.2) 0%, rgba(200, 109, 215,0.2) 100%); */
    border-radius: 8px;
    border-radius: 8px;
    box-sizing: border-box;
}
.block-item-data:hover{
    background-image: url("../../assets/images/list-bg-2.svg");
    background-size: 100% 100%;
    /* box-sizing: border-box; */
}
.font-overflow1{
    display: inline-block;
    width: 100%;
    max-width: 180px;
    overflow: hidden;
    text-overflow: ellipsis !important;
    white-space: nowrap !important;
    -o-text-overflow: ellipsis;
    -webkit-text-overflow: ellipsis;
    -moz-text-overflow: ellipsis;
    vertical-align: middle;
}
.search-pagation-total{
    display: inline-block;
    padding-right: 20px;
    vertical-align: -6px;
    color: #9B9B9B;
}
.input-with-select >>>.el-input__inner{
    padding-left: 50px;
    color: #fff
}
.home-head-content{
    width: 100%;
    height: 100%;
    position: absolute;
    left: 0px;
    top:0px;
    /* padding: 20px 15px 40px 30px; */
    font-size: 14px;
    z-index: 2;
    box-sizing: border-box;
    overflow: auto;
}

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
            this.$emit("nav")
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
