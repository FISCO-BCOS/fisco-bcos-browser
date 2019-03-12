<template>
    <div>
        <el-dialog  :title="title" :visible.sync="configDialog" @close="modelClose" width="750px">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <div class="node-model-conetnt">
                    <el-form-item label="IP:" prop="IP">
                        <el-input v-model="ruleForm.IP" style="width: 200px;" maxlength="16"></el-input>
                    </el-form-item>
                    <el-form-item label="rpc接口:" prop="RPC">
                        <el-input v-model="ruleForm.RPC" style="width: 200px;" maxlength="9"></el-input>
                    </el-form-item>
                    <el-form-item label="p2p接口:" prop="P2P">
                        <el-input v-model="ruleForm.P2P" style="width: 200px;" maxlength="9"></el-input>
                    </el-form-item>
                </div>
                <div class="node-model-foot">
                    <el-form-item>
                        <el-button type="primary" @click="submit('ruleForm')">提交</el-button>
                    </el-form-item>
                </div>
            </el-form>
        </el-dialog>
    </div>
</template>
<style>

</style>
<script>
    // import {addNodeConfigRow} from '@/api/api'
    import {editNodeConfigRow} from '@/api/api'
    import url from '@/api/url'
    import {message} from '@/util/util'
    import constant from '@/util/constant'
    import router from '@/router'
    import '@/assets/css/layout.css'
    import errorcode from "@/util/errorCode"

    export default {
        name: 'configModel',
        props:{
            configModal: {
                type: Boolean,
                default: false,
            },
            title: {
                type: String,
                default: ''
            },
            data: {
                type: Object,
                default: function(){
                    return {
                        IP: "",
                        rpc: "",
                        pk_id: "",
                        p2p: "",
                    }
                }
            },
            type:{
                type: String,
                default: "",
            }
        },
        data: function () {
            return {
                pkIdShow: false,
                ruleForm: {
                    IP: this.data.IP,
                    RPC: this.data.rpc,
                    P2P: this.data.p2p
                },
                rules: {
                    IP: [
                        {required: true, message: '请输入IP', trigger: 'blur'},
                        {pattern:/((25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d)))\.){3}(25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d)))/, message: 'IP格式不正确', trigger: 'blur'}
                    ],
                    RPC: [
                        {required: true, message: '请输入端口号', trigger: 'blur'},
                        {pattern:/^[1-9]\d*$/,message: '请输入正确端口号', trigger: 'blur'}
                    ],
                    P2P: [
                        {required: true, message: '请输入端口号', trigger: 'blur'},
                        {pattern:/^[1-9]\d*$/,message: '请输入正确端口号', trigger: 'blur'}
                    ]
                },
                nodeId: this.data.nodeId,
                configDialog: this.configModal,
                modelType: this.type,
            }
        },
        methods: {
            modelClose: function () {
              this.$emit('close',false)
            },
            submit: function (formName) {
                this.$refs[formName].validate((valid) => {
                        if (valid) {
                            this.$confirm('确认提交？',{center:true}).then(_ => {
                                if (this.modelType === 'edit') {
                                    this.editNodes();
                                } else {
                                    this.addNodes()
                                }
                                this.modelClose(close)
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
                    ip: this.ruleForm.IP,
                    rpcPort: this.ruleForm.RPC,
                    p2pPort: this.ruleForm.P2P,   
                };
                editNodeConfigRow(data).then( res => {
                    if(res.data.code === 0){
                        this.$emit('success');
                        message(constant.ADD_NODE_SUCCESS,'success')
                    }else{
                        this.$emit('success');
                        message(errorcode[res.data.code].cn,'error')
                    }
                }).catch( err => {
                    this.$emit('success');
                    if(err.response && err.response.code !== 200){
                        message(constant.ERROR,'error');
                    }
                })
            },
            editNodes: function () {
                let data ={
                    groupId: localStorage.getItem("groupId"),
                    ip: this.ruleForm.IP,
                    rpcPort: this.ruleForm.RPC,
                    nodeP2pPort: this.ruleForm.P2P,
                    nodeId: this.nodeId,   
                };
                editNodeConfigRow(data).then(res => {
                    if(res.data.status === 0){
                        this.$emit('success');
                        message(constant.EDIT_NODE_SUCCESS,'success')
                    }else{
                        this.$emit('success');
                        message(errorcode[res.data.code].cn,'error')
                    }
                }).catch(err => {
                    this.$emit('success');
                    if(err.response && err.response.code !== 200){
                        message(constant.ERROR,'error');
                    }
                })
            }
        }
    }
</script>
