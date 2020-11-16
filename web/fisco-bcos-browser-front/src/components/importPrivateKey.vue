<template>
    <div>
        <el-form :model="keyForm" :rules="rules" ref="keyForm" label-width="142px" class="demo-ruleForm" size="medium">
            <el-form-item label="文件类型" prop="fileType" style="width: 546px;">
                <el-radio-group v-model="keyForm.fileType" @change="changeFileType">
                    <el-radio :label="item.enName" :key="item.enName" v-for="item in fileTypeList" style="color:#fff;">{{item.enName}}</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="私钥用户名" prop="fileName" style="width: 546px;">
                <el-input v-model="keyForm.fileName" placeholder="请输入私钥用户名" maxlength="12"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password" style="width: 546px;" v-if="keyForm.fileType==='.p12'">
                <el-input type="password" v-model="keyForm.password" placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item label="用户描述" prop="description" style="width: 546px;">
                <el-input v-model="keyForm.description" placeholder="请输入用户描述"></el-input>
            </el-form-item>
            <el-form-item label="文件" prop="file" style="width: 546px;">
                <el-upload ref="upload" :accept="keyForm.fileType" action="" :http-request="uploadFile" :auto-upload="false" :file-list="fileList" show-file-list :limit="1">
                    <el-button slot="trigger" type="primary">选择文件</el-button>
                </el-upload>
            </el-form-item>

        </el-form>
        <div class="text-right">
            <el-button class="footer-button" @click="modelClose">取消</el-button>
            <el-button style="margin-left: 10px;" type="primary" @click="submitUploadList">确认</el-button>
        </div>
    </div>
</template>

<script>
let Base64 = require("js-base64").Base64;
import errorcode from "@/util/errorCode"
import { message } from '@/util/util'
export default {
    name: 'importPrivatekey',

    components: {
    },

    props: {
    },

    data() {
        return {
            loading: false,
            keyForm: {
                fileName: "",
                fileType: ".txt",
                password: "",
                description: ""
            },
            fileTypeList: [
                {
                    enName: '.txt',
                },
                {
                    enName: '.pem',
                },
                {
                    enName: '.p12',
                }
            ],
            fileList: []
        }
    },

    computed: {
        rules() {
            var checkData = (rule, value, callback) => {
                if (value) {
                    if (/[\u4E00-\u9FA5]/g.test(value)) {
                        callback(new Error('密码不能是汉字'));
                    } else {
                        callback();
                    }
                }
                callback();
            }
            let data = {
                fileName: [
                    {
                        required: true,
                        message: '请输入私钥用户名',
                        trigger: "blur"
                    },
                    {
                        min: 1,
                        max: 12,
                        message: '长度在 1 到 12 位',
                        trigger: "blur"
                    }
                ],
                fileType: [
                    {
                        required: true,
                        message: '请选择文件类型',
                        trigger: "blur"
                    }
                ],
                password: [
                    { validator: checkData, trigger: 'blur' }
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
        changeFileType() {
            this.$refs.upload.clearFiles()
            this.keyForm.fileName = ''
            this.keyForm.description = '';
        },
        modelClose() {

        },
        submitUploadList() {
            this.$refs.upload.submit()
        },
        uploadFile(param) {
            this.$refs['keyForm'].validate(valid => {
                if (valid) {
                    var reader = new FileReader(), self = this;
                    reader.readAsText(param.file, "UTF-8");
                    reader.onload = function (evt) {
                        var fileContent = evt.target.result;
                        switch (self.keyForm.fileType) {
                            case '.txt':
                                try {
                                    var fileString = JSON.parse(fileContent).privateKey;
                                    self.textRivateKey(fileString)

                                } catch (error) {
                                    console.log(error)
                                }
                                break;
                            case '.pem':

                                self.pemRivateKey(fileContent)
                                break;
                            case '.p12':
                                self.p12RivateKey(param.file)
                                break;
                        }
                    }
                    this.$refs.upload.clearFiles()
                } else {
                    return false;
                }
            });
        },
        textRivateKey(fileString) {
            let reqQuery = {
                privateKey: Base64.encode(fileString),
                userName: this.keyForm.fileName,
                groupId: localStorage.getItem("groupId"),
                description: this.keyForm.description,
                account: localStorage.getItem("user")
            };
            queryImportPrivateKey(reqQuery)
                .then(res => {
                    const { data, status } = res;
                    if (status === 200) {
                        this.$emit('importRivateKeySuccess')
                        this.modelClose()
                        this.$message({
                            type: 'success',
                            message: '导入成功'
                        })
                    } else {
                        message(errorcode[res.data.code].cn, 'error')
                    }
                })
                .catch(err => {
                    this.$message({
                        type: "error",
                        message: '系统错误'
                    });
                });
        },
        pemRivateKey(fileContent) {
            let reqQuery = {
                pemContent: fileContent,
                userName: this.keyForm.fileName,
                groupId: localStorage.getItem("groupId"),
                description: this.keyForm.description,
                account: localStorage.getItem("user")
            };
            ImportPemPrivateKey(reqQuery)
                .then(res => {
                    const { data, status } = res;
                    if (status === 200) {
                        this.$emit('importRivateKeySuccess')
                        this.$message({
                            type: 'success',
                            message: '导入成功'
                        });
                        this.modelClose()
                    } else {
                        message(errorcode[res.data.code].cn, 'error')
                    }
                })
                .catch(err => {
                    this.$message({
                        type: "error",
                        message: '系统错误'
                    });
                });


        },
        p12RivateKey(param) {
            var form = new FormData()
            form.append('userName', this.keyForm.fileName)
            form.append('p12File', param)
            form.append('p12Password', this.keyForm.password)
            form.append('groupId', localStorage.getItem("groupId"))
            form.append('description', this.keyForm.description)
            form.append('account', localStorage.getItem("user"))
            ImportP12PrivateKey(form)
                .then(res => {
                    const { data, status } = res;
                    if (status === 200) {
                        this.$emit('importRivateKeySuccess')
                        this.$message({
                            type: 'success',
                            message: '导入成功'
                        });
                        this.modelClose()
                    } else {
                        message(errorcode[res.data.code].cn, 'error')
                    }
                })
                .catch(err => {
                    this.$message({
                        type: "error",
                        message: '系统错误'
                    });
                });
        },
    }
}
</script>

<style scoped>
.demo-ruleForm >>> .el-form-item__label {
    color: #ffffff;
}
</style>
