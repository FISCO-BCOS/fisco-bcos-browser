<template>
    <div class="search-main">
        <div class="container">
            <v-nav :hrTitle="btitle" :hrcontent="btitle"></v-nav>
            <div class="search-nav">
                <div class="hashInput">
                    <el-button type="primary" @click="upLoad">上传合约</el-button>
                    <el-tooltip class="item" effect="dark" content="上传合约成功后请点击按钮编译合约,合约一次最多上传10条。" placement="top-start">
                        <i class="el-icon-info"></i>
                    </el-tooltip>
                    <input type="file" ref='file' id="file" class="inputFiles" multiple="multiple"  name="chaincodes" @change="upLoad($event)"/>
                </div>
                <div class="hashInput">
                    <el-button type="primary" @click="complie" :disabled="buttonShow">编译合约</el-button>
                    <el-tooltip class="item" effect="dark" content="点击编译按钮批量编译当前页面所有未编译的合约。" placement="top-start">
                        <i class="el-icon-info"></i>
                    </el-tooltip>
                </div>
            </div>
            <div class="search-table">
                <el-table :data="contractList" v-loading="loading"  element-loading-text="数据加载中..." element-loading-background="rgba(0, 0, 0, 0.8)">
                    <el-table-column prop="contractName" label="合约名称" align="center" :class-name="'table-link'">
                        <template slot-scope="scope">
                            <span @click='openEditor(scope.row)'>{{scope.row.contractName}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="contractStatus" label="合约状态" align="center">
                        <template slot-scope="scope">
                            <span v-if="scope.row.contractStatus == 0">未编译</span>
                            <span v-if="scope.row.contractStatus == 1" style="color: #0F0">已编译</span>
                            <span v-if="scope.row.contractStatus == 2" @click='openAlert(scope.row)' style="color: #F00;cursor:pointer">编译失败
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column  fixed="right" label="操作" width="100" align="center">
                        <template slot-scope="scope">
                                <i class="el-icon-delete"  style=" cursor:pointer" @click="deleteRow(scope.row)"></i>
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
        <v-editor v-if='editorShow' :show='editorShow' :data='editorData' @close='closeEditor'></v-editor>
    </div>
</template>
<script>
let Base64 = require("js-base64").Base64;
import navs from '@/components/content-nav'
import { addContract,getContractList,deleteContract,updateContract } from "@/api/api"
import {message} from '@/util/util'
import constant from '@/util/constant'
import errorcode from "@/util/errorCode"
import editor from "@/components/editor"
import '@/assets/css/layout.css'
import '@/assets/css/public.css'

export default {
    name: "contractConfig",
    components: {
        'v-nav': navs,
        'v-editor': editor
    },
    data: function(){
        return {
            btitle: "合约配置",
            filename: "",
            fileString: "",
            contractList: [],
            allContractList: [],
            ipConfig: "",
            loading: false,
            groupId: null,
            fileList: [],
            files: [],
            editorShow: false,
            editorData: "",
            pagination: {
                currentPage: 1,
                pageSize: 10,
                total: 0,
            },
            buttonShow: false,
        }
    },
    mounted: function(){
        this.groupId = localStorage.getItem("groupId")
        this.getContracts();
        this.getAllGroups();
    },
    methods: {
        openEditor: function(val){
            this.editorShow = true;
            this.editorData = val.contractSource
        },
        closeEditor: function(){
            this.editorShow = false;
            this.editorData = '';
        },
        openAlert: function(row){
            this.$confirm(`<div style="width: 380px; max-height: 250px;padding: 10px 5px;overfolw-y:auto; white-space: pre-wrap;word-wrap: break-word;">${row.errorInfo}</div>
            <div>编译失败，请先删除该合约，修改后再上传编译</div>`,'错误信息', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                dangerouslyUseHTMLString: true
            });
        },
        // upload a contract and save this
        upLoad: function(e) {
            this.fileList = [];
            this.files = [];
            this.filename = "";
            this.fileString = "";
            let files = [];
            let filessize = [];
            let filetype = [];
            if(e.target.files.length< 11){
                for(let i = 0; i < e.target.files.length; i++){
                    files[i] = e.target.files[i];
                    filessize[i] = Math.ceil(files[i].size / 1024);
                    filetype[i] = files[i].name.split(".")[1];
                    if (filessize[i] > 400) {
                        this.$message({
                            message: '文件大小超过400k，请上传小于400k的文件',
                            type: "error"
                        });
                    } else if (filetype[i] !== "sol") {
                        this.$message({
                            message: '请上传.sol格式的文件',
                            type: "error"
                        });
                    } else{
                        let filsObj = {};
                        filsObj.filename = files[i].name.split(".")[0];
                        filsObj.file = files[i].name;
                        let reader = new FileReader(); //add a FileReader
                        reader.readAsText(files[i], "UTF-8"); //read file
                        let _this = this;
                        reader.onload = function(evt) {
                            filsObj.fileString = Base64.encode(evt.target.result); // read file content
                            if(filsObj.fileString){
                                _this.fileList.push(filsObj)
                            }
                        };
                    }
                }
                this.$refs.file.value = "";
                setTimeout(() => {
                    this.saveContract();
                },200)
            }else{
                this.$message({
                    message: '同时上传合约不能超过10条',
                    type: "error"
                });
            }
        },
        saveContract: function(){
            let data = {
                groupId: this.groupId
            }
            if(this.fileList.length){
                data.data = [];
                this.fileList.forEach((value,index) => {
                    data.data[index] = {};
                    data.data[index].contractName = value.filename;
                    data.data[index].contractSource = value.fileString;
                })
            }else{
               this.$message({
                    message: `请选择合约！`,
                    type: "error"
                }); 
                return
            }
            addContract(data).then(res => {
                this.fileList = [];
                this.files = [];
                if(res.data.code === 0){
                    message('合约保存成功！','success')
                    this.getContracts();
                }else{
                   message(errorcode[res.data.code].cn,'error')
                }
            }).catch(err => {
                this.fileList = [];
                this.files = [];
                message(constant.ERROR,'error');
            }) 
        },
        getContracts: function(type){
            let data = {
                    groupId: this.groupId,
                    pageNumber: this.pagination.currentPage,
                    pageSize: this.pagination.pageSize
                };
            getContractList(data,{}).then(res => {
                if(res.data.code === 0){
                    this.contractList = res.data.data;
                    this.pagination.total = res.data.totalCount;
                }else{
                    message(errorcode[res.data.code].cn,'error')
                }
            }).catch(err => {
                 message(constant.ERROR,'error');
            })
        },
        deleteRow: function(val){
            this.$confirm('此操作不可恢复，请确认！','删除合约',{center:true}).then(_ => {
                    this.deleteCode(val)
                }).catch(_ => {
                });
        },
        getAllGroups: function(){
            let data = {
                    groupId: this.groupId,
                    pageNumber: 1,
                    pageSize: 500
                };
            getContractList(data,{}).then(res => {
                if(res.data.code === 0){
                    this.allContractList = res.data.data;
                }else{
                    message(errorcode[res.data.code].cn,'error')
                }
            }).catch(err => {
                 message(constant.ERROR,'error');
            })
        },
        // delete a contract
        deleteCode: function(val){
            let data = {
                groupId: localStorage.getItem("groupId"),
                contractId: val.contractId
            }
            deleteContract(data,{}).then(res => {
                if(res.data.code === 0){
                    this.getContracts();
                    message('删除成功！','success')
                }else{
                    message(errorcode[res.data.code].cn,'error')
                }
            }).catch(err => {
                message('系统错误','error')
            })
        },
        // lots of Compilation Contracts
        complie: function(){
            this.buttonShow = true;
            this.loading = true;
            this.contractList.forEach(value => {
                if(value.contractStatus != 1){
                    this.complieContract(value);
                }    
            });
            setTimeout(() => {
                this.editContract()
            },1000)   
        },
        complieContract: function(val){
            let wrapper = require("solc/wrapper");
            let solc = wrapper(window.Module);
            let content = "";
            let output;
            let input = {
                language: "Solidity",
                settings: {
                    outputSelection: {
                        "*": {
                            "*": ["*"]
                        }
                    }
                }
            };
            input.sources = {};
            input.sources[val.contractName + ".sol"] = {};
            input.sources[val.contractName + ".sol"] = {
                content: Base64.decode(val.contractSource)
            };
            try {
                output = JSON.parse(solc.compileStandard(JSON.stringify(input),this.findImports))
                
            } catch (error) {
                if(typeof error != 'string'){
                    val.errorInfo = JSON.stringify(error) ;
                }else{
                    val.errorInfo = error;
                }
            }
            if (output && JSON.stringify(output.contracts) != "{}" && output.contracts[val.contractName + ".sol"]) {
                let outputData = this.changeOutput(output.contracts[val.contractName + ".sol"])
                val.contractAbi = outputData.abiFile
                val.contractBin = outputData.bin
                if(outputData.errorInfo){
                    val.contractErrorINfo = outputData.errorInfo
                }
            }else{
                if(typeof output.errors[0] != 'string'){
                    val.errorInfo = JSON.stringify(output.errors[0]) ;
                }else{
                    val.errorInfo = output.errors[0];
                }
            }
        },
        // Compile contract callback function
        findImports: function(path){
            let arry = path.split("/");
            let newpath = arry[arry.length-1]
            let num = 0;
            for(let i = 0; i < this.allContractList.length; i++){
                 if (newpath == (this.allContractList[i].contractName + '.sol')){
                    return {contents: Base64.decode(this.allContractList[i].contractSource)}
                }else{
                    num++;
                } 
            }
            if(num == this.allContractList.length){
                return { error: "File not found" };
            }
        },
        //Processing compiled data
        changeOutput: function(obj){
            let val ={}
            let arry = [];
            let data = null;
            if(JSON.stringify(obj) !== "{}"){
                for (const key in obj) {
                    arry.push(obj[key])
                }
                if(arry.length){
                    val.abiFile = arry[0].abi;
                    val.bin = arry[0].evm.deployedBytecode.object;
                }else{
                    val.errorInfo = "合约编译失败！";
                }
            }else{
                val.errorInfo = "合约编译失败！";
            }
            return val
        },
        // update a  contact
        editContract: function(){
            let arry = [];
            this.contractList.forEach(value => {
                if(value.contractAbi && value.contractStatus != 1){
                    if(typeof(value.contractAbi) == "string"){
                        value.contractAbi = value.contractAbi
                    }else{
                        value.contractAbi = JSON.stringify(value.contractAbi)
                    }
                    constant.SYSTEM_CONTRACT_ADDRESS.forEach(val => {
                        if(val.contractName == value.contractName){
                            value.contractAddress = val.contractAddress
                        }
                    })
                    arry.push(value)
                }else if(value.errorInfo && value.contractStatus != 1){
                    arry.push(value)
                }
            })
            let data = {
                groupId: this.groupId,
                data: arry
            }
            if(arry.length){
                updateContract(data).then(res => {
                    this.loading = false;
                    this.buttonShow = false;
                    if(res.data.code === 0){
                        this.getContracts();
                        message('合约编译完成！','success')
                    }else{
                        message(errorcode[res.data.code].cn,'error')
                    }
                }).catch(err => {
                    this.loading = false;
                    this.buttonShow = false;
                    message('系统错误','error')
                })
            }else{
                this.buttonShow = false;
                this.loading = false;
            }    
        },
        handleSizeChange(val) {
            this.pagination.pageSize = val;
            this.pagination.currentPage = 1;
            this.getContracts();
        },
        handleCurrentChange(val) {
            this.pagination.currentPage = val;
            this.getContracts();
        }
    },
    filters: {
        Status: function(value){
            if(value == 0){
                return "未编译"
            }else if(value == 1){
                return '已编译'
            }else{
                return '编译失败'
            }
        }
    }
}
</script>
<style scoped>
.hashInput>.label{
    display: inline-block;
    width: 80px;
}
.inputFiles{
    position: absolute;
    opacity: 0;
    left: 30px;
    top: 0;
    width: 100px;
    height: 40px;
   
}
</style>



