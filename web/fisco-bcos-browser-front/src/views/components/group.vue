<template>
    <div class="search-main">
        <div class="container">
            <v-nav :hrTitle="btitle" :hrcontent="btitle"></v-nav>
             <div class="search-nav">
                 <div class="hashInput">
                    <el-button type="primary" @click="add"><i class="el-icon-plus"></i>新增群组</el-button>
                </div>
             </div>
             <div class="search-table">
                 <el-table :data="grouplist" v-loading="loading"  element-loading-text="数据加载中..."
                          element-loading-background="rgba(0, 0, 0, 0.8)">
                    <el-table-column type="index" label="序号" align="center" min-width='60px'></el-table-column>
                    <el-table-column prop="groupId" label="群组id" align="center"></el-table-column>
                    <el-table-column prop="groupName" label="群组名称" align="center"></el-table-column>
                    <el-table-column prop="groupDesc" label="群组描述" align="center"></el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <i class="el-icon-delete" style=" cursor:pointer" @click="deleteData(scope.row)"></i>
                        </template>
                    </el-table-column>
                </el-table>
             </div>
        </div>
        <add-group @close="addGroups" v-if="addGroupShow" @success='addSuccess' :show='addGroupShow'></add-group>
    </div>
</template>
<script>
import nav from '@/components/content-nav'
import { getGroupList,deleteGroup } from "@/api/api"
import addGroup from "@/components/addGroup"
import {message} from '@/util/util'
import constant from '@/util/constant'
import errorcode from "@/util/errorCode"
import Bus from "@/bus"

export default {
    name: "group",
    components: {
        'v-nav': nav,
        "add-group": addGroup
    },
    data: function(){
        return {
            btitle: "群组配置",
            grouplist: [],
            addGroupShow: false,
            loading: false,
        }
    },
    mounted: function(){
        this.$nextTick(function () {
            if(localStorage.getItem("groupList")){
                this.GetgroupList();
            }else{
                this.add();
            }   
        })
    },
    methods: {
        GetgroupList: function(val){
            let data = {};
            getGroupList(data).then(res => {
                if(res.data.code === 0){
                    if(res.data.data.length){
                        this.grouplist = res.data.data;
                        if(!localStorage.getItem("groupId")){
                            this.groupId = res.data.data[0].groupId;
                            localStorage.setItem("groupId",this.groupId);
                        }
                        localStorage.setItem("groupList",JSON.stringify(res.data.data))
                        Bus.$emit('change',res.data.data)
                        if(val){
                            this.$emit('addGroup');
                        }    
                    }else{
                        this.addGroupShow = true;
                    }
                }else{
                    message(errorcode[res.data.code].cn,'error')
                }
            }).catch(err => {
                message(constant.ERROR,'error');
            })
        },
        add: function(){
            this.addGroupShow = true;
        },
        addGroups: function(){
            this.addGroupShow = false;
            let add = "add"
            this.GetgroupList(add);
        },
        addSuccess: function(){
            this.addGroups();
        },
        deleteData: function(val){
            this.$confirm('此操作不可恢复，请确认！','删除群组',{center:true}).then(_ => {
                    this.deleteItem(val);
                }).catch(_ => {
            });
        },
        deleteItem: function(val){
            if(val.groupId == localStorage.getItem("groupId")){
                localStorage.setItem("groupId","")
            }
            deleteGroup(val.groupId).then(res => {
                if(res.data.code === 0){
                    let item = 'delete'
                    this.GetgroupList(item);
                }else{
                    message(errorcode[res.data.code].cn,'error')
                }
            }).catch(err => {
                 message(constant.ERROR,'error');
            })
        }
    }
}
</script>

