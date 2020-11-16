<template>
    <div>
        <el-form :model="userForm" :rules="rules" ref="userForm" label-width="142px" class="demo-ruleForm" size="medium">
            <el-form-item label="用户名称" prop="name" style="width: 546px;">
                <el-input v-model="userForm.name" placeholder="请输入用户名" maxlength="12" clearable></el-input>
            </el-form-item>
            <el-form-item label="公钥地址" prop="publicKey" style="width: 546px;">
                <el-input v-model="userForm.publicKey" placeholder="请输入公钥地址" clearable></el-input>
            </el-form-item>
            <el-form-item label="用户描述" style="width: 546px">
                <el-input type="textarea" v-model="userForm.description" maxlength="120" placeholder="120个字符以内"></el-input>
            </el-form-item>
        </el-form>
        <div class="text-right">
            <el-button @click="modelClose">取消</el-button>
            <el-button type="primary" @click="submit('userForm')" :loading="loading">确定</el-button>
        </div>
    </div>
</template>

<script>
import errorcode from "@/util/errorCode"
import { message } from '@/util/util'
import { userAdd, userUpdate } from '@/api/api'
import constant from '@/util/constant'
export default {
    name: 'importPublicKey',

    components: {
    },

    props: ['userInfo', 'btnType'],

    data() {
        return {
            loading: false,
            userForm: {
                name: this.userInfo.userName || '',
                description: this.userInfo.description || "",
                publicKey: this.userInfo.address || ""
            },
        }
    },

    computed: {
        rules() {
            let data = {
                name: [
                    {
                        required: true,
                        message: '请输入用户名称',
                        trigger: "blur"
                    },
                    {
                        min: 1,
                        max: 12,
                        message: '长度在 1 到 12 个字符',
                        trigger: "blur"
                    }
                ],
                publicKey: [
                    {
                        required: true,
                        message: '请输入公钥信息',
                        trigger: "blur"
                    }
                ]
            };
            return data
        }
    },

    watch: {
    },

    created() {
    },

    mounted() {
    },

    methods: {
        submit: function (formName) {
            this.$refs[formName].validate(valid => {
                if (valid) {
                    this.$confirm('确认提交？', {
                        center: true
                    })
                        .then(() => {
                            this.loading = true;
                            switch (this.btnType) {
                                case '导入':
                                    this.queryUserAdd()
                                    break;

                                case '修改':
                                    this.queryUserUpdate()
                                    break;
                                
                            }
                        })
                        .catch(() => {
                            this.modelClose();
                        });
                } else {
                    return false;
                }
            });
        },
        queryUserAdd() {
            console.log('add');
            let data = {
                groupId: localStorage.getItem("groupId"),
                userName: this.userForm.name,
                address: this.userForm.publicKey,
                description: this.userForm.description
            }
            userAdd(data)
                .then(res => {
                    this.loading = false
                    if (res.data.code === 0) {
                        this.modelClose()
                        this.$emit('success')
                        message('成功', 'success')
                    } else {
                        message(errorcode[res.data.code].cn, 'error')
                    }
                }).catch(err => {
                    message(constant.ERROR, 'error');
                })
        },
        queryUserUpdate() {
            console.log('Update');
            let data = {
                userId: this.userInfo.userId,
                userName: this.userForm.name,
                address: this.userForm.publicKey,
                description: this.userForm.description
            }
            userUpdate(data)
                .then(res => {
                    this.loading = false
                    if (res.data.code === 0) {
                        this.modelClose()
                        this.$emit('success')
                        message('成功', 'success')
                    } else {
                        message(errorcode[res.data.code].cn, 'error')
                    }
                }).catch(err => {
                    message(constant.ERROR, 'error');
                })

        },
        modelClose() {
            this.userForm = {
                name: '',
                description: "",
                publicKey: ""
            }
            this.$emit('modelClose')
        }
    }
}
</script>

<style scoped>
.demo-ruleForm >>> .el-form-item__label {
    color: #ffffff;
}
</style>
