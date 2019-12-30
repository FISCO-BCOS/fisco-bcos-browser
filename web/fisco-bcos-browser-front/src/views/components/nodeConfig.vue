<template>
    <div class="search-main">
        <div class="container">
            <v-nav :hrTitle="btitle" :hrcontent="btitle"></v-nav>
            <div class="search-nav">
                <div class="hashInput">
                    <el-input placeholder="请输入节点ip" v-model="searchKeyValue" class="input-with-select">
                        <el-button slot="append" icon="el-icon-search" @click="search" :disabled="submitDisabled"></el-button>
                    </el-input>
                    <!-- <span>IP : </span>
                    <el-input v-model="ipConfig" class="input"  maxlength="128"></el-input> -->
                </div>
                <!-- <div class="hashInput">
                    <span>rpc接口 : </span>
                    <el-input v-model="rpcConfig" class="input" maxlength="128"></el-input>
                </div>
                <div class="hashInput">
                    <span>p2p接口 : </span>
                    <el-input v-model="p2pConfig" class="input" maxlength="128"></el-input>
                </div>
                <div class="hashInput">
                    <el-button type="primary" @click="searchTbNodeConnection" :disabled="submitDisabled"><i class="el-icon-search"></i>查询</el-button>
                </div> -->
                <div class="hashInput">
                    <el-button type="primary" @click="add"><i class="el-icon-plus"></i> 新增节点</el-button>
                </div>
            </div>
            <v-model v-if="modeldata.show" ref="addNode" :title="modeldata.title" :config-modal="modeldata.Dialog" @close="closeModel($event)" :data="modeldata.data"  @success="submitModel($event)" :type="modeldata.type"></v-model>
            <div class="search-table">
                <el-table :data="configList" v-loading="loading"  element-loading-text="数据加载中..."
                          element-loading-background="rgba(0, 0, 0, 0.8)">
                    <el-table-column prop="nodeId" label="节点Id" :show-overflow-tooltip="true" align="center"></el-table-column>
                    <el-table-column prop="ip" label="IP" align="center"></el-table-column>
                    <el-table-column prop="rpcPort" label="rpc接口" align="center"></el-table-column>
                    <el-table-column prop="p2pPort" label="p2p接口" align="center"></el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <i class="el-icon-delete" @click="delet(scope.row)"></i>
                        </template>
                    </el-table-column>
                </el-table>
                <add-node v-if="addShow"  :config-modal="addDialog"  @success="submitModel($event)" @close="closeModel($event)"></add-node>
            </div>
        </div>
    </div>
</template>
<script type="es6">
    import nav from '@/components/content-nav'
    import {getTbNodeConnection,deleteNodeConfigRow,getEncryptType} from '@/api/api'
    import addNodes from '@/components/add-nodes'
    import url from '@/api/url'
    import {message} from '@/util/util'
    import constant from '@/util/constant'
    import model from '@/components/config-model'
    import errorcode from "@/util/errorCode"
    import '@/assets/css/layout.css'

    export default {
        name: 'nodeConfig',
        components: {
            'v-nav': nav,
            'v-model': model,
            'add-node': addNodes
            // operations: operation
        },
        data: function () {
            return{
                searchKeyValue: "",
                addDialog: false,
                addShow: false,
                modeldata: {
                    data: {
                        IP: "",
                        rpc: "",
                        pk_id: "",
                    },
                    Dialog: false,
                    title: '新增节点',
                    type: 'add',
                    show: false,
                },
                btitle: '节点配置',
                ipConfig: "",
                p2pConfig: "",
                rpcConfig: "",
                configList: [],
                submitDisabled: false,
                loading: false,
                setIntervalTime: null,
            }
        },
        mounted: function () {
            this.searchTbNodeConnection();
        },
        beforeDestroy: function () {
            this.clear()
        },
        methods: {
            clear: function () {
                window.clearInterval(this.setIntervalTime);
            },
            closeModel: function (value) {
                this.modeldata.Dialog = value;
                this.modeldata.show = value;
                this.addShow = false;
                setTimeout(() => {
                    this.searchTbNodeConnection();
                },500)
            },
            submitModel: function (value) {
                this.searchTbNodeConnection();
            },
            search: function(){
                this.ipConfig = this.searchKeyValue;
                this.searchTbNodeConnection();
                this.searchKeyValue = "";
            },
            searchTbNodeConnection: function () {
                this.submitDisabled = true;
                this.loading = true;
                let data = {
                    groupId: localStorage.getItem("groupId"),
                    pageNumber: 1,
                    pageSize: 100,
                    
                };
                let query = {
                    type: 0,
                    ip: this.ipConfig,
                    rpcPort: this.rpcConfig,
                    p2pPort: this.p2pConfig,
                }
               getTbNodeConnection(data,query).then(res => {
                    window.clearInterval(this.setIntervalTime);
                    this.setIntervalTime = window.setInterval(() => {this.searchTbNodeConnection()}, constant.INTERVALTIME);
                    this.submitDisabled = false;
                    this.loading = false;
                    if(res.data.code === 0){
                        if(res.data.data && res.data.data.length > 0){
                            this.configList = res.data.data;
                            this.getEncrypt();
                        }else{
                            this.configList = []
                            // message('无可用节点，请在节点配置添加节点！','warning')
                        }
                    }else{
                        message(errorcode[res.data.code].cn,'error')
                    }
                    this.rpcConfig = "";
                    this.p2pConfig = "";
                }).catch(err => {
                    this.submitDisabled = false;
                    this.loading = false;
                    if(err.response.code !== 200){
                        message(constant.ERROR,'error');
                    }
                    this.clear();
                    this.rpcConfig = "";
                    this.p2pConfig = "";
                })
            },
            getEncrypt: function(){
                getEncryptType(localStorage.getItem("groupId")).then(res => {
                    if(res.data.code === 0){
                        localStorage.setItem("encryptionId",res.data.data)
                    }else{
                        message(errorcode[res.data.code].cn,'error')
                    }
                }).catch(err => {
                    message(constant.ERROR,'error');
                })
            },
            add: function () {
                this.addShow = true;
                this.addDialog = true
            },
            delet: function (row) {
                this.$confirm('此操作不可恢复，请确认！','删除节点',{center:true}).then(_ => {
                    this.deleteNodes(row)
                }).catch(_ => {
                });
            },
            deleteNodes: function (data) {
                let reqData = {
                    groupId: localStorage.getItem("groupId"),
                    nodeId: data.nodeId
                };
                if(this.configList.length == 1){
                    message('最后一个节点不允许删除！','error');
                }else{
                    deleteNodeConfigRow(reqData,{}).then(res => {
                        if(res.data.code === 0){
                            message(constant.DELETE_NODE_SUCCESS,'success');
                            this.searchTbNodeConnection();
                        }else{
                            message(errorcode[res.data.code].cn,'error');
                        }
                    }).catch(function (err) {
                        if(err.response && err.response.code !== 200){
                            message(constant.ERROR,'error');
                        }
                    })
                } 
            }
        }
    }
</script>
<style scoped>
@media screen and (max-width: 1150px){
    .hashInput{
        position: relative;
        display: inline-block;
        padding: 10px 0 0 30px;
    }
}
</style>

