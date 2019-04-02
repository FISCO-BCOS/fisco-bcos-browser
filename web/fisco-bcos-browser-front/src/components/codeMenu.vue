<template>
    <div>
        <ul>
            <li v-for='(item,index) in contractArry' :key='item.contractId' class="contract-menu-item" :class="{'colorActive': item.contractActive}">
                <div style="cursor: pointer" v-if='!item.contractType'  >
                    <i class="wbs-icon-file" @click='checkCode(item,index)'></i>
                    <span @click='checkCode(item,index)'>{{item.contractName + '.sol'}}</span>
                    <i class="wbs-icon-delete" style="font-size: 14px;padding-left: 10px;" @click='deleteFile(item)'></i>
                </div>
                <div v-if='item.contractType' >
                    <i class="wbs-icon-folder" style="color: #d19650" @click='openItem(item)'></i>
                    <span @click='openItem(item)'>{{item.contractName}}</span>
                    <i :class="item.fileIcon" @click='openItem(item)'></i>
                    <i class="wbs-icon-delete" style="font-size: 14px;padding-left: 10px;" @click='deleteFolder(item)'></i>
                </div>
                <codeMenu v-if='item.contractType && item.fileActive' :data='item.child'></codeMenu>
            </li>
        </ul>
    </div>
</template>
<script>
import Bus from "@/bus"
export default {
    name: "codeMenu",
    props: ['data','indexs'],
    data: function(){
        return {
            contractArry: this.data,
            contractData: [],
            activeIndex: this.indexs,
            indexData: null,
        }
    },
    watch:{
        data: function(val){
            this.contractArry = val
        },
        indexData: function(val){
            this.activeIndex = null;
        }
    },
    mounted: function(){
    },
    methods: {
        checkCode: function(val){
            this.contractArry.forEach((value,index) => {
                if(val.contractId == value.contractId){
                    if(val.contractActive){
                        this.$set(value,'contractActive',false);
                    }else{
                        this.$set(value,'contractActive',true);
                    }
                }else{
                    this.$set(value,'contractActive',false);
                }
            })
            let data = {
                data: val,
                list: this.contractArry
            }
            Bus.$emit('check',data);
        },
        openItem: function(val){
            this.activeIndex = null
            if(val.fileIcon == 'el-icon-caret-right'){
                this.contractArry.forEach(value => {
                    if(value.contractId == val.contractId){
                        value.fileActive = true;
                        val.fileActive = true;
                        val.fileIcon = 'el-icon-caret-bottom';
                    }else{
                        value.fileActive = false;
                        val.fileIcon = 'el-icon-caret-right';
                    }
                });
            }else if(val.fileIcon == 'el-icon-caret-bottom'){
                val.fileActive = false;
                val.fileIcon = 'el-icon-caret-right';
            } 
        },
        deleteFile: function(val){
            Bus.$emit("deleteFile",val)
        },
        deleteFolder: function(val){
            Bus.$emit("deleteFolder",val)
        }
    }
}
</script>
<style scoped>
.contract-menu-item{
    width: 100%;
    height: 40px;
    line-height: 40px;
}
ul,li{
    list-style: none;
}
.colorActive{
    color: #409EFF;
}
</style>


