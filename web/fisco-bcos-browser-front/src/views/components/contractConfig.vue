<template>
    <div class="search-main" style="padding-bottom: 0;height: 100%;">
        <div class="container" style="padding-bottom: 0;height: 100%;">
            <v-nav :hrTitle="btitle" :hrcontent="btitle"></v-nav>
            <div class="search-nav">
                <div class="hashInput">
                    <el-button type="primary" @click="upLoadFiles">上传合约</el-button>
                    <el-tooltip class="item" effect="dark" content="上传合约成功后请点击按钮编译合约,合约一次最多上传10条。" placement="top-start">
                        <i class="el-icon-info"></i>
                    </el-tooltip>
                    <input type="file" ref='file' id="file" class="inputFiles" multiple="multiple"  name="chaincodes" @change="upLoadFiles($event)"/>
                </div>
                <div class="hashInput">
                    <el-button type="primary" @click="complie" :disabled="buttonShow">编译合约</el-button>
                    <el-tooltip class="item" effect="dark" content="点击编译按钮批量编译当前页面所有未编译的合约。" placement="top-start">
                        <i class="el-icon-info"></i>
                    </el-tooltip>
                </div>
            </div>
            <div class="search-table" style="font-size: 0;height: calc(100% - 133px); box-sizing: border-box;">
                <div class="contract-menu">
                    <v-codeMenu :data='contractArry'></v-codeMenu>
                </div>
                <div class="contract-content" style="height: 100%;">
                    <div ref='codeContent' :style="{height: codeHight}">
                        <div  class="ace-editor" ref="ace" v-show='editorShow' id='aceEditor'></div>
                    </div>
                    <div v-if='errInfo' style="height: 30%;border-top: 1px solid #fff;background-color: rgb(39, 40, 34)">
                        <span>输出信息</span><br>
                        <span style="width: 100%;word-break:break-all;">{{errInfo}}</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
let Base64 = require("js-base64").Base64;
import navs from '@/components/content-nav'
import { addContract,getContractList,deleteContract,updateContract,uploadData } from "@/api/api"
import {message} from '@/util/util'
import constant from '@/util/constant'
import errorcode from "@/util/errorCode"
import editor from "@/components/editor"
import '@/assets/css/layout.css'
import '@/assets/css/public.css'
import codeMenu from "@/components/codeMenu"
let resData = require('@/contract.json');
import ace from 'ace-builds'
// import 'ace-builds/webpack-resolver' 
import 'ace-builds/src-noconflict/theme-monokai'
import 'ace-builds/src-noconflict/mode-javascript'
import 'ace-builds/src-noconflict/ext-language_tools';  
require('ace-mode-solidity/build/remix-ide/mode-solidity')
import Bus from "@/bus"

export default {
    name: "contractConfig",
    components: {
        'v-nav': navs,
        'v-editor': editor,
        "v-codeMenu": codeMenu,
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
            editorData: "",
            pagination: {
                currentPage: 1,
                pageSize: 10,
                total: 0,
            },
            buttonShow: false,
            contractArry: [],

            editorShow: true,
            aceEditor: null,
            themePath: 'ace/theme/monokai', 
            modePath: 'ace/mode/solidity',
            indexData: null,
            resultList: [],
            codeHight: '100%',
            errInfo: "",
        }
    },
    mounted: function(){
        this.groupId = localStorage.getItem("groupId")
        this.initEditor();
        this.getContracts();
        this.getAllGroups();
        Bus.$on("check",data => {
            this.setContent(data.data);
            this.changeContractList(data.data,data.list)
        })
        Bus.$on("deleteFile",data => {
            let reqData = data.contractId.toString()
            this.deleteCode(reqData)
        })
        Bus.$on("deleteFolder",data => {
            let arry = [];
            arry = this.setContractList(data.contractName,this.resultList);
            let reqlist = [];
            
            for(let i = 0; i < arry.length; i++){
                reqlist.push(arry[i].contractId)
            }
            let reqData = reqlist.join(",")
            this.deleteCode(reqData)
        })
    },
    methods: {
        initEditor: function(){
            this.aceEditor = ace.edit('aceEditor', {
                maxLines: Math.ceil((this.$refs.codeContent.offsetHeight)/17) + 1,
                minLines: Math.ceil((this.$refs.codeContent.offsetHeight)/17) + 1,
                fontSize: 14, 
                fontFamily: 'Consolas,Monaco,monospace',

                theme: this.themePath, 
                mode: this.modePath, 
                tabSize: 4,
                useSoftTabs: true
            })
            this.aceEditor.setOptions({
                enableSnippets: true,
                enableLiveAutocompletion: true,
                enableBasicAutocompletion: true
            })
            let editor = this.aceEditor.alignCursors();
            this.aceEditor.getSession().setUseWrapMode(true);
            this.aceEditor.resize();  
        },
        setContent: function(val){
            if(val.errorInfo){
                this.errInfo = val.errorInfo;
                this.codeHight = '70%'
            }
            let data = Base64.decode(val.contractSource)
            this.aceEditor.setValue(data)
        },
        openAlert: function(row){
            this.$confirm(`<div style="width: 380px; max-height: 250px;padding: 10px 5px;overfolw-y:auto; white-space: pre-wrap;word-wrap: break-word;">${row.errorInfo}</div>
            <div>编译失败，请先删除该合约，修改后再上传编译</div>`,'错误信息', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                dangerouslyUseHTMLString: true
            });
        },
        upLoadFiles: function(e){
            let num = 0;
            if(e.target.files.length == 1){
                if(e.target.files[0].name.split(".")[1] == 'zip'){
                    this.uploadZip(e)
                }else if(e.target.files[0].name.split(".")[1] == 'sol'){
                    let num =0 ;
                    this.contractList.forEach(value => {
                        if(value.contractName == e.target.files[0].name.split(".")[0] && value.contractPath == "/"){
                            num ++
                            this.$message({
                            message: `合约${value.contractName}与已上传的合约名称相同，请修改后在上传！`,
                            type: "error"
                        });
                        }
                    })
                    if(num == 0){
                        this.upLoad(e)
                    }
                }
            }else if(e.target.files.length > 1){
                for(let i = 0; i < e.target.files.length; i++){
                    if(e.target.files[i].name.split(".")[1] == 'zip'){
                        num++;
                        this.$message({
                            message: '不能同时上传sol文件和zip包,且不能上传多个zip包。',
                            type: "error"
                        });
                    }
                    this.contractList.forEach(value => {
                        if(value.contractName == e.target.files[i].name.split(".")[0] && value.contractPath == "/"){
                            num++
                            this.$message({
                            message: `合约${value.contractName}与已上传的合约名称相同，请修改后在上传！`,
                            type: "error"
                        });
                        }
                    })
                }
                if(num == 0){
                    this.upLoad(e)
                }
            }
        },
        uploadZip: function(e){
            let file = document.getElementById("file").files[0]
            let files = e.target.files[0],
                fileName = e.target.files[0].name
            let fileFormData = new FormData();
            fileFormData.append('zipFile',file,file.name);
            uploadData(fileFormData).then(res => {
                if(res.data.code == 0){
                    this.getContracts()
                }
            }).catch(err => {
                alert(1111)
            })
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
            }
            if(this.fileList.length){
                data.data = [];
                this.fileList.forEach((value,index) => {
                    data.data[index] = {};
                    data.data[index].contractName = value.filename;
                    data.data[index].contractPath = "/";
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
                    pageNumber:1,
                    pageSize: 500
                };
            getContractList(data,{}).then(res => {
                if(res.data.code === 0){
                    this.contractList = res.data.data;
                    if(res.data.data && res.data.data.length){
                        this.changeContractData(res.data.data)
                    }
                    this.pagination.total = res.data.totalCount;
                }else{
                    message(errorcode[res.data.code].cn,'error')
                }
            }).catch(err => {
                message(constant.ERROR,'error');
            })
        },
        changeContractList: function(item,list){
            this.resultList.forEach(value => {
                value.contractActive = false;
                list.forEach(val => {
                    if(val.contractId == value.contractId){
                        if(val.fileActive){ 
                            value.fileActive = true;
                            value.fileIcon = 'el-icon-caret-bottom';
                        }else if(val.fileActive == false){
                            val.fileActive = false;
                            val.fileIcon = 'el-icon-caret-right';
                        }
                    }
                })
                if(value.contractId == item.contractId){
                    value.contractActive = true
                }
            });
            this.contractArry = this.setContractList("",this.resultList)
        },
        changeContractData: function(list){
            let arry = [];
            let folderArry = [];
            list.forEach((value,index) => {
                if(value.contractActive){
                    value.contractActive = true
                }else{
                    value.contractActive = false;
                }
               
                value.realPath = value.contractPath + value.contractName + '.sol';
                value.pathArry = value.realPath.split("/");
                // debugger
                if(value.pathArry && value.pathArry.length > 1){
                    value.contractFolder = value.pathArry[0];
                    value.realFolder = value.pathArry[value.pathArry.length-2]
                }else{
                    value.contractFolder = ""
                }
            })
            arry = list[0]
            list.forEach(value => {
                if(value.pathArry.length > arry.pathArry.length){
                    arry = value
                }
            })
            // console.log(arry)
            folderArry = this.createFolder(arry);
            this.resultList = list.concat(folderArry);
            this.contractArry = this.setContractList("",this.resultList);
        },
        checkOne: function(val){
            val.forEach(value => {
                if(!value.contractType){
                    this.setContent(value);
                    return
                }
            })
        },
        //create folder
        createFolder: function(data){
            let arry = [];
            let result = [];
            arry = data.pathArry;
            arry.pop();
            for(let i = 0; i < arry.length; i++){
                let obj = {};
                obj.contractName = arry[i];
                obj.contractType = 'folder';
                obj.fileActive = false;
                obj.fileIcon = 'el-icon-caret-right';
                obj.contractId = (new Date()).getTime();
                if(i){
                    obj.realFolder = arry[i-1]
                }else{
                    obj.realFolder = ""
                    obj.contractFolder = ""
                }
                if(obj.contractName){
                    result.push(obj)
                }
            }
            return result
        },
        getParentArry: function(name, arry){
            var newArry = new Array();
            for (var i in arry) {
                if (arry[i].realFolder && arry[i].realFolder == name){
                    newArry.push(arry[i]);
                }else if(!arry[i].realFolder && arry[i].realFolder == name){
                    newArry.push(arry[i]);
                }
            }
            return newArry;
        },
        setContractList: function(name,arry){
            let childArry = this.getParentArry(name,arry);
            if(childArry.length){
               childArry.forEach(value => {
                   if(value.contractType == 'folder'){
                       value.child = [];
                       value.child = this.setContractList(value.contractName,arry)
                   }
               }) 
            }
            return childArry;
        },   
        deleteRow: function(val){
            this.$confirm('此操作不可恢复，请确认！','删除合约',{center:true}).then(_ => {
                    this.deleteCode(val)
                }).catch(_ => {
                });
        },
        getAllGroups: function(){
            let data = {
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
                contractId: val
            }
            deleteContract(data).then(res => {
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
.contract-menu{
    display: inline-block;
    width: 300px;
    height: 100%;
    font-size: 14px;
    color: #fff;
    vertical-align: top;
    background-color: #3b3e54;
    overflow: auto;
    box-sizing: border-box;
}
.contract-content{
    display: inline-block;
    width: calc(100% - 300px);
    height: 100%;
    min-height: 400px;
    font-size: 14px;
    color: #fff;
}

</style>



