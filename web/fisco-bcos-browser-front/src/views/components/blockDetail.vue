<template>
    <div>
        <div class="search-main padding-bottom-0">
            <div class="container">
                <div class="padding-bottom-0" style="padding-top: 40px">
                    <!-- <v-nav :page='page' :hrTitle="'区块'" :navContent="blockName" :navSubtitle="'区块'" :hrcontent="'区块信息'" :route="'block'"></v-nav> -->
                    <div class="detail-content">
                    <div class="c-title">
                        <span>区块 {{blockName}}</span>
                    </div>
                    </div>
                </div>
                <div class="hash-content-info">
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
    </div>
</template>
<style scoped>
    .content>>>.jv-code{
        max-height: none;
    }
    .hash-content-info>>> .jv-container{
        background: none;
    }
    .hash-content-info>>> .jv-key{
        color: #fff
    }
    .hash-content-info>>> .jv-array{
        color: #fff !important
    }
    .hash-content-info>>> .jv-object{
        color: #fff !important
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
                }
            }
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
