<template>
    <div class="search-main" style="height: calc(100% - 120px);overflow: auto;"  v-loading.fullscreen.lock="loading">
        <div class="container" style="padding-bottom: 0;height: 100%;">
            <v-nav :hrTitle="btitle" :hrcontent="btitle"></v-nav>
            <div class="search-nav">
                <div class="hashInput">
                    <el-button type="primary" @click="upLoadFiles">上传合约</el-button>
                    <el-tooltip class="item" effect="dark" content="1、上传合约可以上传sol文件和zip包; 2、sol文件可以批量上传，zip包不能批量上传; 3、zip包里面只能含有一层目录，且不能含有非sol文件" placement="top-start">
                        <i class="el-icon-info"></i>
                    </el-tooltip>
                    <input type="file" ref='file' id="file" class="inputFiles" multiple="multiple" accept=".sol,.zip"  name="chaincodes" @change="upLoadFiles($event)"/>
                </div>
                <div class="hashInput">
                    <el-button type="primary" @click="complie" :disabled="buttonShow">编译合约</el-button>
                    <el-tooltip class="item" effect="dark" content="点击编译按钮批量编译当前页面所有未编译的合约。" placement="top-start">
                        <i class="el-icon-info"></i>
                    </el-tooltip>
                </div>
            </div>
            <div class="search-table" style="font-size: 0; box-sizing: border-box;height: calc(100% - 114px);">
                <div class="contract-menu" style="height: 100%">
                    <v-codeMenu  :data='contractArry'></v-codeMenu>
                </div>
                <div class="contract-content" style="height: 100%;background-color: rgb(39, 40, 34)">
                    <div ref='codeContent' :style="{height: codeHight}">
                        <div  class="ace-editor" ref="ace" v-show='editorShow' id='aceEditor'></div>
                    </div>
                    <div v-if='errorInfo && !contractAbi' class="contract-errorInfo">
                        <span class='title'>错误信息</span><br>
                        <span style="width: 100%;word-break:break-all;">{{errorInfo}}</span>
                    </div>
                </div>
                <div style="clear: both"></div>
            </div>
        </div>
    </div>
</template>
<script>
let Base64 = require("js-base64").Base64;
import navs from '@/components/content-nav'
import { addContract,getContractList,deleteContract,updateContract,uploadData,addAbiFunction } from "@/api/api"
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
import web3 from "@/util/ethAbi"

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
            codeHight: 'calc(100% - 26px)',
            errorInfo: "",
            compliteData: null,
            contractAbi: "",
            contractData: null,
        }
    },
    beforeDestroy: function(){
        Bus.$off("check")
        Bus.$off("deleteFile")
        Bus.$off("deleteFolder")
        Bus.$off("open")
        Bus.$off("complite")
    },
    beforeMount() {
        var head = document.head;
        var script = document.createElement("script");
        if(localStorage.getItem("encryptionId") == 1){
            script.src = "./static/js/soljson-v0.4.25-gm.js";
        }else{
            script.src = "./static/js/soljson-v0.4.25+commit.59dbf8f1.js";
        }
        script.setAttribute('id', 'soljson');
        if (!document.getElementById('soljson')) {
            head.append(script)
        }
    },
    mounted: function(){
        this.groupId = localStorage.getItem("groupId")
        this.initEditor();
        this.getContracts();
        this.getAllGroups();
        Bus.$on("check",data => {
            this.selectData(data)
        })
        Bus.$on("deleteFile",data => {
            let reqData = data.contractId.toString()
            this.deleteCode(reqData)
        })
        Bus.$on("deleteFolder",data => {
            let arry = [];
            this.resultList.forEach(value => { 
                if(value.realFolder == data.contractName){
                    arry.push(value)
                }
            })
            let reqlist = [];
            
            for(let i = 0; i < arry.length; i++){
                reqlist.push(arry[i].contractId)
            }
            let reqData = reqlist.join(",")
            this.deleteCode(reqData)
        })
        Bus.$on('open',data => {
            this.resultList.forEach(value => {
                if(value.contractId == data.contractId){
                    if(data.fileIcon == 'wbs-icon-jiantouarrow487'){
                        this.$set(value,'fileIcon','wbs-icon-jiantouarrow483')
                        value.fileIcon = 'wbs-icon-jiantouarrow483';
                    }else{
                        this.$set(value,'fileIcon','wbs-icon-jiantouarrow487')
                        value.fileIcon = 'wbs-icon-jiantouarrow487'
                    }
                }
            })
            this.contractArry = this.setContractList(this.resultList)
        })
        Bus.$on("complite",data => {
             this.loading = true
            for(let i = 0; i < data.length; i++){
                this.complieContract(data[i])
                if(i == data.length -1 ){
                    setTimeout(() => {
                        this.editContract(data)
                    },100)
                }
            }
        })
    },
    methods: {
        selectData: function(data){
            this.setContent(data);
            this.resultList.forEach(value => {
                if(value.contractId == data.contractId && !data.contarctType){
                    value.contractActive = true
                }else{
                    value.contractActive = false
                }
            })
            this.contractArry = this.setContractList(this.resultList)
        },
        initEditor: function(){
            this.aceEditor = ace.edit('aceEditor', {
                maxLines: Math.ceil((this.$refs.codeContent.offsetHeight) / 17) + 1,
                minLines: Math.ceil((this.$refs.codeContent.offsetHeight) / 17)  + 1,
                fontSize: 14, 
                fontFamily: 'Consolas,Monaco,monospace',

                theme: this.themePath, 
                mode: this.modePath, 
                tabSize: 4,
                useSoftTabs: true,
            })
            this.aceEditor.setOptions({
                enableSnippets: true,
                enableLiveAutocompletion: true,
                enableBasicAutocompletion: true
            })
            let editor = this.aceEditor.alignCursors();
            this.aceEditor.getSession().setUseWrapMode(true);
            this.aceEditor.resize();  
            this.aceEditor.setReadOnly(true);  
        },
        setContent: function(val){
            this.codeHight = 'calc(100% - 26px)';
            this.errorInfo = "";
            this.contractAbi = val.contractAbi
            if(val.errorInfo && !val.contractAbi){
                this.errorInfo = val.errorInfo;
                this.codeHight = 'calc(70% - 26px)'
            }
            setTimeout(() => {
                this.aceEditor.setOptions({
                    maxLines: Math.ceil((this.$refs.codeContent.offsetHeight)/17) + 1,
                    minLines: Math.ceil((this.$refs.codeContent.offsetHeight)/17) + 1,
                })
                let data = Base64.decode(val.contractSource)
                this.aceEditor.setValue(data)
            },300)
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
            this.$refs.file.value ="";
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
                }else{
                    message(errorcode[res.data.code].cn,'error')
                }
            }).catch(err => {
                 this.$message({
                        message: '系统错误！',
                        type: "error"
                    });
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
                    this.allContractList = res.data.data;
                    this.changeAllcontarctList(this.allContractList)
                    if(res.data.data && res.data.data.length){
                        this.changeContractData(res.data.data)
                        this.selectData(res.data.data[0]);
                        this.getAbiMethod(res.data.data)
                    }else{
                        this.contractArry = []
                    }
                    this.pagination.total = res.data.totalCount;
                }else{
                    message(errorcode[res.data.code].cn,'error')
                }
            }).catch(err => {
                message(constant.ERROR,'error');
            })
        },
        //Get all ABI methods
        getAbiMethod: function(list){
            let arry = [];
            let methodArry = []
            list.forEach(value => {
                if(value && value.contractAbi){
                    let abi = JSON.parse(value.contractAbi)
                    arry.push(abi)
                }
            })
            arry.forEach(value => {
                if(value && value.length){
                    value.forEach(item => {
                        methodArry.push(item)
                    })
                }
            })
            this.decodeMethodId(methodArry)
        },
        decodeMethodId: function(list){
            let Web3EthAbi = web3;
            let arry = []
            list.forEach((value,index) => {
                if(value.name && value.type =='function'){
                    let data = {}
                    let methodId;
                    if(localStorage.getItem("encryptionId") == 1){
                        methodId = Web3EthAbi.smEncodeFunctionSignature({
                            name: value.name,
                            type: value.type,
                            inputs: value.inputs
                        });
                    }else{
                        methodId = Web3EthAbi.encodeFunctionSignature({
                            name: value.name,
                            type: value.type,
                            inputs: value.inputs
                        });
                    }
                    data.methodId = methodId;
                    data.abiInfo = JSON.stringify(value);
                    data.type = value.type
                    arry.push(data)
                }else if(value.name && value.type =='event'){
                    let data = {}
                    let methodId;
                    if(localStorage.getItem("encryptionId") == 1){
                        methodId = Web3EthAbi.smEncodeEventSignature({
                            name: value.name,
                            type: value.type,
                            inputs: value.inputs
                        });
                    }else{
                        methodId = Web3EthAbi.encodeEventSignature({
                            name: value.name,
                            type: value.type,
                            inputs: value.inputs
                        });
                    }
                    data.methodId = methodId;
                    data.abiInfo = JSON.stringify(value);
                    data.type = value.type
                    arry.push(data)
                }
            })
            this.addMethod(arry)
        },
        addMethod: function(list){
            let data = {
                data: list
            }
            addAbiFunction(data).then(res => {
                if(res.data.code === 0 ){
                    console.log("method 保存成功！")
                }else{
                    message(errorcode[res.data.code].cn,'error')
                }
            }).catch(err => {
                 message(constant.ERROR,'error');
            })
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
                value.pathArry = value.contractPath.split("/");
                if(value.pathArry && value.pathArry.length > 1){
                    value.contractFolder = value.pathArry[0];
                    value.realFolder = value.pathArry[value.pathArry.length-2]
                }else{
                    value.contractFolder = ""
                }
            })
            arry = []
            list.forEach(value => {
                value.pathArry.forEach(item => {
                    arry.push(item)
                })
            })
            let newArry = []
            for(let i = 0; i < arry.length; i++){
                if(newArry.indexOf(arry[i]) == -1 && arry[i]){
                    newArry.push(arry[i])
                }
            }
            folderArry = this.createFolder(newArry);
            this.resultList = list.concat(folderArry);
            this.contractArry = this.setContractList(this.resultList);
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
            arry = data;
            for(let i = 0; i < arry.length; i++){
                let obj = {};
                obj.contractName = arry[i];
                obj.contractType = 'folder';
                obj.fileActive = false;
                obj.fileIcon = 'wbs-icon-jiantouarrow487';
                if(i){
                    obj.contractId = result[i-1].contractId + 1;
                }else{
                    obj.contractId = (new Date()).getTime();
                }
                obj.realFolder = ""
                obj.contractFolder = ""
                if(obj.contractName){
                    result.push(obj)
                }
            }
            return result
        },
        setContractList: function(arry){
            let childArry = [];
            let folderArry = [];
            let finalArry = [];
            arry.forEach(value => {
                if(value.realFolder == "" && !value.contractType){
                    childArry.push(value)
                }else if(value.realFolder == "" && value.contractType){
                    folderArry.push(value)
                }
            })
            folderArry.forEach(value => {
                value.child = [];
                arry.forEach(item => {
                    if(item.realFolder == value.contractName){
                        value.child.push(item)
                    }
                })
            })
            finalArry = childArry.concat(folderArry)
            return finalArry;
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
                    this.changeAllcontarctList(this.allContractList)
                }else{
                    message(errorcode[res.data.code].cn,'error')
                }
            }).catch(err => {
                 message(constant.ERROR,'error');
            })
        },
        changeAllcontarctList: function(list){
            list.forEach(value => {
                value.realPath == value.contractPath + value.contractName;
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
                    this.codeHight = '100%';
                    this.errorInfo = "";
                    this.aceEditor.setValue("")
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
                    this.compliteData = value;
                    this.complieContract(value);
                }    
            });
            setTimeout(() => {
                this.editContract()
            },1500)   
        },
        complieContract: function(val){
            // this.loading = false;
            // this.loading = true
            console.log((new Date()).getTime())
            this.contractData = val
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
            // let newpath = "";
            let newpath = arry[arry.length - 1];
            let num = 0;
            if(arry.length > 1){
                let newPath = arry[0]
                let oldPath = arry[arry.length - 1]
                let importArry = [];
                this.allContractList.forEach(value => {
                    if(value.realFolder == newPath){
                        importArry.push(value)
                    }
                })
                if(importArry.length){
                    for(let i = 0; i < importArry.length; i++){
                        if(oldPath == importArry[i].contractName + ".sol"){
                            return {
                                contents: Base64.decode(
                                    importArry[i].contractSource
                                )
                            };
                        }
                    }
                }else{
                    return { error: "File not found" };
                }
    
            }else{
                let newpath = arry[arry.length - 1];
                let newArry = []
                this.allContractList.forEach(value => {
                    if(value.contractPath == this.contractData.contractPath){
                        newArry.push(value)
                    }
                })
                if(newArry.length > 1){
                    for(let i = 0; i < newArry.length; i++){
                        if(newpath == newArry[i].contractName + ".sol"){
                            return {
                                contents: Base64.decode(
                                    newArry[i].contractSource
                                )
                            };
                        }
                    }
                    for (let i = 0; i < this.allContractList.length; i++) {
                        if (newpath == this.allContractList[i].contractName + ".sol") {
                            return {
                                contents: Base64.decode(this.allContractList[i].contractSource)
                            };
                        } else {
                            num++;
                        }
                    }
                    if(num){
                        return { error: "File not found" };
                    }
                }else{
                    for (let i = 0; i < this.allContractList.length; i++) {
                        if (newpath == this.allContractList[i].contractName + ".sol") {
                            return {
                                contents: Base64.decode(this.allContractList[i].contractSource)
                            };
                        } 
                    }
                }
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
                    val.bin = arry[0].evm.bytecode.object;
                    val.bin = val.bin.substring(0,val.bin.length-69)
                }else{
                    val.errorInfo = "合约编译失败！";
                }
            }else{
                val.errorInfo = "合约编译失败！";
            }
            return val
        },
        // update a  contact
        editContract: function(val){
            
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
            if(val){
                val.forEach(value => {
                    if(typeof(value.contractAbi) == "string"){
                        value.contractAbi = value.contractAbi
                    }else if(value.contractAbi){
                        value.contractAbi = JSON.stringify(value.contractAbi)
                    }else{
                        value.contractAbi = ""
                    }
                    constant.SYSTEM_CONTRACT_ADDRESS.forEach(val => {
                        if(val.contractName == value.contractName){
                            value.contractAddress = val.contractAddress
                        }
                    })
                })
                arry = val
            }
            let data = {
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
    /* padding-bottom: 9999px;
    margin-bottom: -9999px; */
    /* float: left; */
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
    /* min-height: 400px; */
    font-size: 14px;
    color: #fff;
}
.contract-errorInfo{
    height: 30%;
    border-top: 1px solid #fff;
    background-color: rgb(39, 40, 34);
    z-index: 999999;
    color: #fff;
    padding: 10px 20px;
}
.contract-errorInfo .title{
    display: inline-block;
    padding-bottom: 20px;   
}
.contract-content>>>.ace_print-margin{
    width: 0
}


</style>



