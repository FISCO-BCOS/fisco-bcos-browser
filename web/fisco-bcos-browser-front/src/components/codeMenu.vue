<template>
    <div>
        <ul>
            <li v-for='(item,index) in contractArry' :key='item.contractId' class="contract-menu-item" :class="{'colorActive': item.contractActive}">
                <div style="cursor: pointer" v-if='!item.contractType' class="contract-menu-file">
                    <el-tooltip class="item" effect="dark" content="未编译" placement="top-start">
                        <img src="../assets/images/file.png" v-show='item.contractStatus == 0 || item.contractStatus == -1'  @click='checkCode(item,index)'>
                    </el-tooltip>
                    <el-tooltip class="item" effect="dark" content="已编译" placement="top-start">
                        <img src="../assets/images/file-success.png" v-show='item.contractStatus == 1'  @click='checkCode(item,index)'>
                    </el-tooltip>
                    <el-tooltip class="item" effect="dark" content="编译失败" placement="top-start">
                        <img src="../assets/images/file-error.png" v-show='item.contractStatus == 2'  @click='checkCode(item,index)'>
                    </el-tooltip>
                    <span @click='checkCode(item,index)'>{{item.contractName + '.sol'}}</span>
                    <el-tooltip class="item" effect="dark" content="编译" placement="top-start" v-if='item.contractStatus == 0 || item.contractStatus ==2'>
                        <i class="wbs-icon-zhihang" style="font-size: 14px;padding-left: 10px;" @click='buttonShow && complite(item,index)'></i>
                    </el-tooltip>
                    <el-tooltip class="item" effect="dark" content="删除" placement="top-start">
                        <i class="wbs-icon-delete" style="font-size: 14px;padding-left: 10px;" @click='deleteFile(item)'></i>
                    </el-tooltip>
                </div>
                <div v-if='item.contractType' class="contract-menu-folder">
                    <i :class="item.fileIcon" @click='openItem(item)'></i>
                    <i class="wbs-icon-folder" style="color: #d19650" @click='openItem(item)'></i>
                    <span @click='openItem(item)'>{{item.contractName}}</span>
                    <el-tooltip class="item" effect="dark" content="编译" placement="top-start" v-show="!item.compileShow">
                        <i class="wbs-icon-zhihang" style="font-size: 14px;padding-left: 10px;" @click='complite(item)'></i>
                    </el-tooltip>
                    <el-tooltip class="item" effect="dark" content="删除" placement="top-start">
                        <i class="wbs-icon-delete" style="font-size: 14px;padding-left: 10px;" @click='deleteFolder(item)'></i>
                    </el-tooltip><br>
                    <ul v-if='item.fileIcon == "wbs-icon-jiantouarrow483"' style="padding-left: 42px;">
                        <li v-for='list in item.child' :key='list.contractId' class="contract-menu-item" :class="{'colorActive': list.contractActive}">
                            <div style="cursor: pointer" v-if='!list.contractType'  >
                                <el-tooltip class="item" effect="dark" content="未编译" placement="top-start">
                                   <img src="../assets/images/file.png" v-show='list.contractStatus == 0'  @click='checkCode(list,index)'>
                                </el-tooltip>
                                <el-tooltip class="item" effect="dark" content="已编译" placement="top-start">
                                    <img src="../assets/images/file-success.png" v-show='list.contractStatus == 1'  @click='checkCode(list,index)'>
                                </el-tooltip>
                                <el-tooltip class="item" effect="dark" content="编译失败" placement="top-start">
                                    <img src="../assets/images/file-error.png" v-show='list.contractStatus == 2'  @click='checkCode(list,index)'>
                                </el-tooltip>
                                <span @click='checkCode(list,index)'>{{list.contractName + '.sol'}}</span>
                            </div>
                        </li>
                    </ul>
                </div>
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
            activeIndex: -1,
            buttonShow: true,
        }
    },
    watch:{
        data: function(val){
            this.contractArry = val;
            this.contractArry.forEach(value => {
                if(value.contractType && value.child.length){
                    let num = 0;
                    for(let i = 0; i < value.child.length; i++){
                        if(value.child[i].contractStatus == 1){
                            num++
                        }
                    }
                    if(num == value.child.length && num !=0){
                        this.$set(value,'compileShow',true)
                    }
                }
            });
        },
        indexData: function(val){
            this.activeIndex = null;
        }
    },
    mounted: function(){
    },
    methods: {
        checkCode: function(val){
            // this.contractArry.forEach((value,index) => {
            //     if(val.contractId == value.contractId){
            //         if(val.contractActive){
            //             this.$set(value,'contractActive',false);
            //         }else{
            //             this.$set(value,'contractActive',true);
            //         }
            //     }else{
            //         this.$set(value,'contractActive',false);
            //     }
            // })
            Bus.$emit('check',val);
        },
        openItem: function(val){
            Bus.$emit("open",val)
        },
        deleteFile: function(val){
            if( this.buttonShow){
                 this.$confirm('确认删除？',{center:true}).then(_ => {
                    Bus.$emit("deleteFile",val)
                }).catch(_ =>{

                })
            }
           
        },
        deleteFolder: function(val){
            if( this.buttonShow){
                this.$confirm('确认删除？',{center:true}).then(_ => {
                    Bus.$emit("deleteFolder",val)
                }).catch( _ =>{

                })
            }
            
        },
        complite: function(val,indexs){
            this.contractArry.forEach((value,index) => {
                if(value.contarctId == val.contarctId && value.contractType){
                    this.$set(value,'compileShow',true)
                    this.$set(val,'compileShow',true)
                }else if(value.contractId == val.contractId && !value.contractType){
                    this.$set(value,'compileShow',true)
                    this.$set(value,'contractStatus',-1)
                    this.$set(val,'compileShow',true)
                    this.$set(val,'contractStatus',-1)
                }
            })
            let data = []
            if(val.contractType){
                data = val.child
            }else{
                data = [val]
            }
            if(this.buttonShow){
                console.log((new Date()).getTime())
                this.$emit("compile",data)
                Bus.$emit("complite",data)
                
            }
            this.buttonShow = false
            setTimeout(() => {
                this.buttonShow = true
            },2000)
        }
    }
}
</script>
<style scoped>
.contract-menu-item{
    width: 100%;
    /* height: 40px; */
    line-height: 32px;
    overflow-x: auto;
}
ul,li{
    list-style: none;
    padding: 0
}
.contract-menu-file{
    padding-left: 40px;
}
.contract-menu-folder{
    padding-left: 20px;
}
.colorActive{
    color: #409EFF;
}
</style>


