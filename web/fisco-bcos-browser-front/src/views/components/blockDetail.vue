<template>
    <div>
        <div class="search-main padding-bottom-0">
            <div class="container padding-bottom-0" >
                <v-nav  v-if="v_page == action" :page='page' :hrTitle="hrTitle" :navContent="navContent" :navSubtitle="navSubtitle" :hrcontent="hrcontent" :route="route"></v-nav>
                <div class="detail-content">
                   <div class="c-title">
                       <div>区块信息</div>
                   </div>
                </div>
            </div>
        </div>
        <div class="hash-content-info">
            <div class="container">
                <div class="content">
                    <div >
                        <json-viewer
                        :value="blcokContent"
                        :expand-depth='5'
                        copyable
                        sort></json-viewer>
                        <div style="text-align: center" v-if="noData">暂无数据</div>
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
    import {getTbBlockByBlockHash} from '@/api/api'
    import url from '@/api/url'
    import {message} from '@/util/util'
    import constant from '@/util/constant'
    import errorcode from "@/util/errorCode"
    import '@/assets/css/layout.css'
    import '@/assets/css/public.css'

    export default {
        name: 'blockDetail',
        components: {
            'v-nav': nav,
        },
        data: function () {
            return {
                blockName: '',
                blcokContent: "",
                noData: false,
                page: {
                    pageSize: this.$route.pageSize || 10,
                    pageNumber: this.$route.pageNumber || 1
                },
                v_page:'', 
                action:'',
                hrTitle:"",
                navSubtitle:"",
                hrcontent:"",
                route:""
            }
        },

         beforeMount(){ 
        this.page.param = this.$route.query.param;
        this.v_page = this.$route.query.v_page;
        let navlist = [
            {    
                action : "",
                hrTitle : "区块",
                navSubtitle : this.blockName,
                hrcontent : "区块信息",
                route : "block"

            },{  
                action : "transaction",
                hrTitle : "交易",
                navSubtitle : "交易",
                hrcontent : "区块信息",
                route : "transaction"
            },{       
                action : "contractTransaction",
                hrTitle : "合约",
                navSubtitle : "合约交易列表",
                hrcontent : "区块信息",
                route : "contractTransactionList"
            },{       
                action : "userTransaction",
                hrTitle : "用户",
                navSubtitle : "用户交易列表",
                hrcontent : "区块信息",
                route : "userTransactionList"
            }
        ];

        navlist.forEach((item)=>{ 
            if(this.v_page == item.action){
                this.action = item.action;
                this.hrTitle = item.hrTitle;
                this.navSubtitle = item.navSubtitle;
                this.hrcontent = item.hrcontent;
                this.route = item.route;
            }
        }); 
    },

        created: function () {
            this.blockName = "#"+ this.$route.query.blockHash;
        },
        mounted: function () {
            this.searchBlockDetail();
        },
        methods: {
            searchBlockDetail: function () {
                this.noData = false;
                let data ={
                    groupId: localStorage.getItem("groupId"),
                    blockHash: this.$route.query.blockHash || "",
                };
                getTbBlockByBlockHash(data,{}).then(res => {
                    if(res.data.code === 0){
                        if(res.data.data){
                            this.noData = false;
                            this.blcokContent = res.data.data
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
            }
        }
    }
</script>
