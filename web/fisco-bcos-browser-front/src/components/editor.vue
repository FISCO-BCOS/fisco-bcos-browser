<template>
    <div>
        <el-dialog  :title="'查看合约'" :visible.sync="editorDialog" @close="modelClose" width="900px">
            <div ref='codeContent'>
                <div  class="ace-editor" ref="ace" v-show='editorShow' id='aceEditor'></div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
import ace from 'ace-builds'
// import 'ace-builds/webpack-resolver' 
import 'ace-builds/src-noconflict/theme-monokai'
import 'ace-builds/src-noconflict/mode-javascript'
import 'ace-builds/src-noconflict/ext-language_tools';  
require('ace-mode-solidity/build/remix-ide/mode-solidity')
let Base64 = require("js-base64").Base64;
export default {
    name: 'editor',
    props: ['data','show'],
    data: function(){
        return {
            editorShow: true,
            aceEditor: null,
            themePath: 'ace/theme/monokai', 
            modePath: 'ace/mode/solidity',
            editorDialog: this.show || false,
        }
    },
    mounted: function(){ 
        this.$nextTick(function() {
            this.initEditor();
            this.setContent();
        })   
    },
    methods: {
        initEditor: function(){
            this.aceEditor = ace.edit('aceEditor', {
                maxLines: 30,
                minLines: 5,
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
            if(localStorage.getItem("openContractList")){
                this.openContractList = JSON.parse(localStorage.getItem("openContractList"))
            };
            this.aceEditor.resize();  
        },
        setContent: function(){
            let data = Base64.decode(this.data)
            this.aceEditor.setValue(data)
        },
        modelClose: function(){
            this.$emit('close')
        }
    }
}
</script>

