<template>
    <div class="search-main">
        <div class="container">
            <v-nav :hrTitle="btitle" :hrcontent="btitle"></v-nav>
            <div class="search-nav" style="display: flex;">
                <div class="hashInput">
                    <el-input placeholder="" v-model="searchKeyValue" class="input-with-select">
                        <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
                    </el-input>
                </div>
                <div class="hashInput">
                    <el-button type="primary" @click="importKey"><i class="el-icon-plus"></i> 导入</el-button>
                </div>
            </div>
            <div class="search-table">
                <el-table :data="configList" v-loading="loading" element-loading-text="数据加载中..." element-loading-background="rgba(0, 0, 0, 0.8)">
                    <el-table-column v-for="head in privateKeyHead" :label="head.name" :key="head.enName" show-overflow-tooltip align="center">
                        <template slot-scope="scope">
                            <template v-if="head.enName!='operate'">
                                <span v-if="head.enName ==='userStatus'" :style="{'color': statusColor(scope.row[head.enName])}">{{userStatus(scope.row[head.enName])}}</span>
                                <span v-else-if="head.enName ==='address'">
                                    <i class="wbs-icon-copy font-12 copy-public-key" v-show="scope.row[head.enName]" @click="copyPubilcKey(scope.row[head.enName])" title="复制"></i>
                                    {{scope.row[head.enName]}}
                                </span>
                                <span v-else-if="head.enName ==='userId'">
                                    <el-tooltip :content="scope.row['hasPk'] == 1 ?  '私钥':'公钥'" placement="top" effect="dark">
                                        <i class="wbs-icon-key-b font-12" :style="{'color': scope.row['hasPk'] == 1 ? '#FFC31F':'#4F9DFF'}"></i>
                                    </el-tooltip>
                                    {{scope.row[head.enName]}}
                                </span>
                                <span v-else>{{scope.row[head.enName]}}</span>
                            </template>
                            <template v-else>
                                <el-button type="text" size="small" @click="modifyDescription(scope.row)">修改</el-button>
                                <el-button type="text" size="small" @click="deleteUser(scope.row)">删除</el-button>
                            </template>
                        </template>

                    </el-table-column>
                </el-table>
                <!-- <el-pagination class="page" @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage" :page-sizes="[10, 20, 30, 50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
                </el-pagination> -->
                <el-dialog :visible.sync="addShow" :title="`${btnType}用户`" width="640px" :append-to-body="true" class="dialog-wrapper" v-if="addShow" center>
                    <add-user @success="success" @modelClose="modelClose" :userInfo="userInfo" :btnType='btnType'></add-user>
                </el-dialog>
            </div>
        </div>
    </div>
</template>
<script type="es6">
import nav from '@/components/content-nav'
import { userList, userDelete } from '@/api/api'
import addUser from '@/components/addUser.vue'
import url from '@/api/url'
import { message } from '@/util/util'
import constant from '@/util/constant'
import errorcode from "@/util/errorCode"
import '@/assets/css/layout.css'

export default {
    name: 'userConfig',
    components: {
        'v-nav': nav,
        addUser
    },
    data() {
        return {
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
            btitle: '用户配置',
            ipConfig: "",
            p2pConfig: "",
            rpcConfig: "",
            configList: [],
            submitDisabled: false,
            loading: false,
            setIntervalTime: null,
            privateKeyHead: [
                {
                    enName: "userName",
                    name: "用户名"
                },
                {
                    enName: "userId",
                    name: "用户ID"
                },

                {
                    enName: "address",
                    name: "公钥地址"
                },
                {
                    enName: "description",
                    name: "描述"
                },
                {
                    enName: "operate",
                    name: "操作"
                }
            ],
            currentPage: 1,
            pageSize: 10,
            total: 0,   
            btnType: '',
            userInfo: {}
        }
    },
    mounted() {
        if(this.$route.query.userName){
            this.searchKeyValue = this.$route.query.userName
        }
        this.getUserInfoData()
    },
    methods: {
        importKey() {
            this.addShow = true;
            this.btnType = '导入'
            this.userInfo = {}
        },
        getUserInfoData() {
            this.loading  = true
            let data = {
                groupId: localStorage.getItem('groupId'),
                pageNumber: this.currentPage,
                pageSize: this.pageSize,
            }
            let reqParam ={}
            if(this.searchKeyValue){
                reqParam = {
                    userParam: this.searchKeyValue
                }
            }
            userList(data, reqParam)
                .then(res => {
                    this.loading = false
                    if (res.data.code === 0) {
                        this.configList = res.data.data
                        this.total = res.data.totalCount || 0;
                    } else {
                        message(errorcode[res.data.code].cn, 'error')
                    }
                }).catch(err => {
                    message(constant.ERROR, 'error');
                })
        },
        success() {
            this.getUserInfoData()
        },
        modelClose() {
            this.addShow = false
        },
        copyPubilcKey(val) {
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
        modifyDescription(val) {
            this.userInfo = val;
            this.btnType = '修改'
            this.addShow = true
        },
        deleteUser(val){
            this.$confirm(`确认删除？`, {
                center: true,
                dangerouslyUseHTMLString: true
            })
                .then(() => {
                    this.queryUserDetele(val);
                })
                .catch(() => {
                    console.log('')
                });
        },
        queryUserDetele(val) {
            userDelete(val.userId)
                .then(res => {
                    this.loading = false
                    if (res.data.code === 0) {
                       this.getUserInfoData()
                        message('成功', 'success')
                    } else {
                        message(errorcode[res.data.code].cn, 'error')
                    }
                }).catch(err => {
                    message(constant.ERROR, 'error');
                })
        },
        handleSizeChange(val) {
            this.pageSize = val;
            this.currentPage = 1;
            this.getUserInfoData();
        },
        handleCurrentChange(val) {
            this.currentPage = val;
            this.getUserInfoData();
        },
        search(){
            this.currentPage = 1
            this.getUserInfoData()
        }
    }
}
</script>
<style scoped>
@media screen and (max-width: 1150px) {
    .hashInput {
        position: relative;
        display: inline-block;
        padding: 10px 0 0 30px;
    }
}
</style>

