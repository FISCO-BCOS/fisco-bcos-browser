<template>
    <div>
        <div class="text-center">
            <!-- <el-radio-group v-model="timeGranularity" @change='changeKey'>
                <el-radio :label="'RIV'" style="color:#fff;">私钥用户</el-radio>
                <el-radio :label="'PUB'" style="color:#fff;">公钥用户</el-radio>
            </el-radio-group> -->
        </div>
        <div class="divide-line"></div>
        <import-public-key v-if="pubKey" @success="success" @modelClose="modelClose" :userInfo="userInfo" :btnType="btnType"></import-public-key>
        <!-- <import-private-key v-if="rivKey"></import-private-key> -->
    </div>
</template>

<script>
import importPublicKey from './importPublicKey.vue'
import importPrivateKey from '@/components/importPrivateKey.vue'
export default {
    name: 'importKey',

    components: {
        importPublicKey,
        importPrivateKey
    },

    props: ['userInfo','btnType'],

    data() {
        return {
            loading: false,
            pubKey: true,
            
            timeGranularity: "RIV",
        }
    },

    computed: {
    },

    watch: {
    },

    created() {
    },

    mounted() {
    },

    methods: {
        modelClose: function() {
            this.$emit('modelClose')
        },
        success: function () {
            this.$emit('success')
        }
    }
}
</script>

<style scoped>
.key-dialog {
    margin-top: 10px;
}
.dialog-footer {
    text-align: right;
    margin-right: -5px;
    padding-bottom: 20px;
}
.radio-key {
    cursor: context-menu;
    font-size: 14px;
}
.base-span-key {
    margin-left: 8px;
    color: #00122c;
}
.pub-key {
    margin-left: 30px;
}
.riv-key {
    margin-left: 50px;
}
.divide-line {
    border: 1px solid #e7ebf0;
    margin-left: 30px;
    width: 514px;
    margin-top: 15px;
    margin-bottom: 25px;
}
</style>
