<template>
    <el-dialog  :title="title" :visible.sync="configDialog" @close="modelClose" width="900px">
        <el-form :model="nodesForm"  ref="nodesForm" label-width="100px" class="demo-ruleForm">
            <div class="node-model-conetnt" style="padding-left: 0">
                <div v-for="(item,index) in nodesForm" :key="item.key">
                <el-form-item label="IP:" :prop="index + '.ip'" style="display: inline-block" :rules="[
                        {required: true, message: '请输入IP', trigger: 'blur'},
                    {pattern:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/, message: 'IP格式不正确', trigger: 'blur'}
                    ]">
                    <el-input v-model="item.ip" style="width: 150px;" maxlength="16"></el-input>
                </el-form-item>
                    <el-form-item label="rpc接口:" :prop="index + '.rpcPort'" style="display: inline-block" :rules=" [
                        {required: true, message: '请输入端口号', trigger: 'blur'},
                        {pattern:/^[1-9]\d*$/,message: '请输入正确端口号', trigger: 'blur'}
                    ]">
                        <el-input v-model="item.rpcPort" style="width: 150px;" maxlength="9"></el-input>
                    </el-form-item>
                <el-form-item label="p2p接口:" :prop="index + '.p2pPort'" style="display: inline-block" :rules=" [
                        {required: true, message: '请输入端口号', trigger: 'blur'},
                        {pattern:/^[1-9]\d*$/,message: '请输入正确端口号', trigger: 'blur'}
                    ]">
                    <el-input v-model="item.p2pPort" style="width: 150px;" maxlength="9"></el-input>
                </el-form-item>

                    <span class="el-icon-plus" style="cursor: pointer;display: inline-block;padding-left: 20px" @click="add()"></span>
                    <span v-if="nodesForm.length > 1" class="el-icon-minus" style="cursor: pointer;display: inline-block;padding-left: 10px" @click="delet(item)"></span>
                </div>
            </div>
        </el-form>
         <div slot="footer" class="dialog-footer group-footer">
                <el-button type="primary" @click="submit('nodesForm')">提交</el-button>
            </div>
    </el-dialog>
</template>
<style>
.el-dialog__footer{
    background-color: #2a2c3b !important;
    text-align: center;
}
.el-form-item__label{
    color: #fff
}
</style>
<script>
    import url from '@/api/url'
    import {addNodeConfigRow} from "@/api/api"
    import {message} from '@/util/util'
    import constant from '@/util/constant'
    import router from '@/router'
    import '@/assets/css/layout.css'
    import errorcode from "@/util/errorCode"

    export default {
        name: 'addNodes',
        props: ['configModal'],
        data: function () {
            return {
                title: "新增节点",
                configDialog:  this.configModal,
                nodesForm: [
                    {
                        ip: "",
                        rpcPort: "",
                        p2pPort: "",
                        key: Date.now()
                    }
                ],
                rules: {
                    ip: [
                        {required: true, message: '请输入IP', trigger: 'blur'},
                        {pattern:/((25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d)))\.){3}(25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d)))/, message: 'IP格式不正确', trigger: 'blur'}
                    ],
                    rpcPort: [
                        {required: true, message: '请输入端口号', trigger: 'blur'},
                        {pattern:/^[1-9]\d*$/,message: '请输入正确端口号', trigger: 'blur'}
                    ],
                    p2pPort: [
                        {required: true, message: '请输入端口号', trigger: 'blur'},
                        {pattern:/^[1-9]\d*$/,message: '请输入正确端口号', trigger: 'blur'}
                    ],
                },
            }
        },
        methods: {
            add: function () {
                let value = {
                    ip: "",
                    rpcPort: "",
                    nodeP2pPort: "",
                    key: Date.now()
                };
                this.nodesForm.push(value)
            },
            delet: function (item) {
                let index = this.nodesForm.indexOf(item)
                if(index !== -1){
                    this.nodesForm.splice(index,1)
                }
            },
            submit: function (formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交？',{center:true}).then(_ => {
                            this.addNodes();
                        }).catch(_ => {
                        });
                    }else{
                        return false;
                    }
                })
            },
            addNodes: function () {
                let data ={
                    groupId: localStorage.getItem("groupId"),
                    data: this.nodesForm,   
                };
                addNodeConfigRow(data).then( res => {
                    if(res.data.code === 0){
                        this.$emit('success');
                        message(constant.ADD_NODE_SUCCESS,'success')
                        this.modelClose(close)
                    }else{
                        message(errorcode[res.data.code].cn,'error')
                    }
                }).catch( err => {
                    if(err.response && err.response.status !== 200){
                        message(constant.ERROR,'error');
                    }
                })
                
            },
            modelClose: function () {
                this.$emit('close',false)
            },
        }
    }
</script>



