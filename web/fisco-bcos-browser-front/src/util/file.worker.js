import solc from 'solc/wrapper';
let Base64 = require("js-base64").Base64;
var compileJSON;
var missingInputs = [];
function findImports (path,list,contractPath) {
    let contractList = list;
    let arry = path.split("/");
    let newpath = arry[arry.length - 1];
    let num = 0;
    if (arry.length > 1) {
        let newPath = arry[0]
        let oldPath = arry[arry.length - 1]
        let importArry = []
        contractList.forEach(value => {
            if (value.contractPath == newPath) {
                importArry.push(value)
            }
        })
        if (importArry.length) {
            for (let i = 0; i < importArry.length; i++) {
                if (oldPath == importArry[i].contractName + ".sol") {
                    console.log(importArry[i].contractSource,11111)
                    return {
                        contents: Base64.decode(
                            importArry[i].contractSource
                        )
                    };
                }
            }
        } else {
            return { error: "File not found" };
        }
    } else {
        let newpath = arry[arry.length - 1];
        let newArry = []
        contractList.forEach(value => {
            if (value.contractPath == contractPath) {
                newArry.push(value)
            }
        })
        if (newArry.length > 1) {
            for (let i = 0; i < newArry.length; i++) {
                if (newpath == newArry[i].contractName + ".sol") {
                    return {
                        contents: Base64.decode(
                            newArry[i].contractSource
                        )
                    };
                }
            }
            for (let i = 0; i < contractList.length; i++) {
                if (newpath == contractList[i].contractName + ".sol") {
                    console.log(contractList[i].contractSource,33333)
                    return {
                        contents: Base64.decode(contractList[i].contractSource)
                    };
                } else {
                    num++;
                }
            }
            if (num) {
                return { error: "File not found" };
            }
        } else {
            let num1 = 0
            for (let i = 0; i < contractList.length; i++) {
                if (newpath == contractList[i].contractName + ".sol") {
                    console.log(contractList[i].contractSource,4444444)
                    return {
                        contents: Base64.decode(contractList[i].contractSource)
                    };
                }else{
                    num1 ++
                }
            }
            if (num1) {
                return { error: "File not found" };
            }
        }
    }
};
module.exports = function worker(self) {
    
    self.addEventListener('message', (e) => {
    const data = e.data;
    console.log(data)
    // return
    switch (data.cmd) {
        case 'loadVersion':
            delete self.Module
            // NOTE: workaround some browsers?
            self.Module = undefined
            compileJSON = null;
            try {
                self.importScripts(data.data)
            } catch (error) {
                console.log(error)
            }
           
            let compiler = solc(self.Module);
            compileJSON = (input,list,dataPath) => {
                try {
                    let missingInputsCallback = (path) => {
                        return findImports(path,list,dataPath)
                    }
                    return compiler.compile(input, { import: missingInputsCallback })
                } catch (exception) {
                    return JSON.stringify({ error: 'Uncaught JavaScript exception:\n' + exception })
                }
            }
            self.postMessage({
                cmd: 'versionLoaded',
                data: compiler.version()
              })
            break
        case 'compile':
            missingInputs.length = 0
            if(data.input && compileJSON) {
            self.postMessage({
                cmd: 'compiled', 
                data: compileJSON(data.input,data.list,data.path),
                input: data.input,
                missingInputs: missingInputs
            })
            }
            break
        }
    },false)
}