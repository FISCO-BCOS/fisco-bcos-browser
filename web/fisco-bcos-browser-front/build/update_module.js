const fs = require('fs');
const path = require('path');
const request = require('request');

// solc编译文件列表
const solcBinList = [
    {
        name: 'v0.4.25.js',
        url: `https://osp-1257653870.cos.ap-guangzhou.myqcloud.com/WeBASE/download/solidity/wasm/v0.4.25.js`,
    },
    {
        name: 'v0.4.25-gm.js',
        url: `https://osp-1257653870.cos.ap-guangzhou.myqcloud.com/WeBASE/download/solidity/wasm/v0.4.25-gm.js`,
    },
    {
        name: 'v0.5.2.js',
        url: `https://osp-1257653870.cos.ap-guangzhou.myqcloud.com/WeBASE/download/solidity/wasm/v0.5.2.js`,
    },
    {
        name: 'v0.5.2-gm.js',
        url: `https://osp-1257653870.cos.ap-guangzhou.myqcloud.com/WeBASE/download/solidity/wasm/v0.5.2-gm.js`,
    },
    {
        name: 'v0.6.10.js',
        url: `https://osp-1257653870.cos.ap-guangzhou.myqcloud.com/WeBASE/download/solidity/wasm/v0.6.10.js`,
    },
    {
        name: 'v0.6.10-gm.js',
        url: `https://osp-1257653870.cos.ap-guangzhou.myqcloud.com/WeBASE/download/solidity/wasm/v0.6.10-gm.js`,
    }
];
const folderName = path.join('./static/js')
/*
* url 网络文件地址
* filename 文件名
* callback 回调函数
*/
const downloadFile = async (uri, filename, callback) => {
    var stream = fs.createWriteStream(path.resolve(folderName, filename), { flags: 'w' });
    await request(uri).pipe(stream).on('close', callback);
}

/**
 * list 文件列表
 */
const allDownload = async (list) => {
    await list.map(value => {
        downloadFile(value.url, value.name, () => {
            console.log(value.name + '下载完毕');
        })
    })
}

const aceWebpackResolve = './node_modules/ace-builds/webpack-resolver.js';
const requireFromString = './node_modules/require-from-string/index.js';
try {
    // 修改ace-builds webpack-resolver打包路径
    let fileContent = fs.readFileSync(aceWebpackResolve, 'utf-8');
    if (!fileContent) {
        console.log("ace-builds webpack-resolver not find")
        return
    }
    let oldStr = /esModule=false!/g
    let newStr = 'esModule=false&outputPath=ace/!'
    fileContent = fileContent.replace(oldStr, newStr);
    fs.writeFileSync(aceWebpackResolve, fileContent, 'utf8');
    console.log('ace-builds webpack-resolver 打包路径修改成功')
} catch (error) {
    console.log('error: ' + error)
}

try {
    // 修改require-from-string index的写法问题
    let fileContent2 = fs.readFileSync(requireFromString, 'utf-8');
    if (!fileContent2) {
        console.log("require-from-string not find")
        return
    }
    let oldStr = 'var Module = require("module")'
    let newStr = 'var Module = module.constructor'
    fileContent2 = fileContent2.replace(oldStr, newStr);
    fs.writeFileSync(requireFromString, fileContent2, 'utf8');
    console.log('require-from-string 依赖修改成功')
} catch (error) {
    console.log('error: ' + error)
}

try {
    console.log('solc下载开始')
    allDownload(solcBinList)
} catch (error) {
    console.log('solc下载失败:' + error)
}