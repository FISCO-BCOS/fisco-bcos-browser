# 二次开发   （需要下载nodejs，此处是该项目搭建开发环境与打包，不需要进行二次开发同学无需关注）
nodejs下载地址：https://nodejs.org/en/download/

### 1、开发环境搭建


#### （1）安装依赖包
从代码库拉下代码后，将代码放入新建的project文件夹下，可以在命令行工具（Windows是cmd，苹果是终端控制）切到项目下面。

> 切换到项目目录：

    cd D:\project\fisco-bcos-browser-front

> 使用命令：

    npm install
    
下载依赖包

> 执行update修改依赖和下载相关文件：

    ```
    npm run update
    ```
    *注意：必须执行update，否则编译打包都是失败，此脚本会修改部分依赖文件并且从cdn下载solc的编译文件，执行成功后，后面无需再执行。*

> 修改依赖：

   在node_modules中找到依赖包`require-from-string`，修改`index.js`文件第2行
   
   	var Module = require("module");
   改为
   
   	var Module = module.constructor;


#### （2）启动项目

> 在项目根目录使用命令：

    npm run dev

> 控制台出现：

    Listening at http ://localhost:8080

> 在浏览器输入"http ://localhost:8080"。

> 默认端口是8080，可在config文件夹index.js中修改。

    dev: {
          env: require('./dev.env'),
          port: process.env.PORT || 8080,   //项目启动端口
          autoOpenBrowser: true,
          assetsSubDirectory: 'static',
          assetsPublicPath: '/',
          proxyTable: {
              '/api': {
                  target: "http://xx.xx.xx.xx:xxxx/",   //跨域地址配置
                  changeOrigin: true,
                  pathRewrite: {
                      '^/api': '/'
                  }
              }
          },

#### （3）跨域配置

在config文件夹index.js中，在dev中的proxyTable修改，修改地址即可,请求的url路径必须加上前缀。

> 例如：请求路径：

    http ://xx.xx.xx.xx:xxxx/home/login

> 代理后请求路径修改为：

    /api/home/login


#### （4）模拟数据

模拟数据在mock.js中，在开发联调前使用，使用中注意mock.js的url和axios请求的url要保持一致。

注意：开发时将mian.js中加上mock.js引用，打包时需要注释mock.js的引用。

> axios请求地址：

    GET_TBBLOCK_CHAIN_INFO: `/fisco-cc-browser-page/home/getTbBlockChainInfo.json`,  //url和下面mock.js相同

    GET_TXNBY_LASTFOURTEENDAY: `${api}/home/getTxnByLastFourteenDay.json`, //使用mock.js这种写法无效

    GET_TBBLOCK_INFO: `${api}/block/getTbBlockInfoByPage.json`,  //使用mock.js这种写法无效

> mock.js地址：

    Mock.mock('/fisco-cc-browser-page/home/getTbBlockChainInfo.json',function (req,res) {     //url和上面axios相同
        return {
            "status":0,
            "msg":"success",
            "data":{"pkId":1,
                "lastBlock":17,
                "transactionNumber":17,
                "pendingTxn":0,
                "pbftView":9768,
                "avgTime":100.00
            }
        }
    });

> main.js引用mock.js：

    Vue.use(ElementUI);
    // require('./mock')    //直接require引入，开发时放开注释，打包时注释
    /* eslint-disable no-new */
    new Vue({
      el: '#app',
      router,
      template: '<App/>',
      components: { App }
    })


### 2、项目打包部署

#### （1）项目打包

> 切换到项目根目录，执行命令：

    npm run build

> 进行打包，生成打包文件dist，在项目根目录下。

#### （2）部署

部署和上面一致。


## 3、目录说明


    │  .babelrc   // ES6语法编译配置
    │  .editorconfig   // 定义代码格式
    │  .postcssrc.js
    │  dist.zip         //已打包好的项目
    │  index.html               //项目入口
    │  package-lock.json
    │  package.json     // 项目基本信息
    │  README.md      // 项目说明
    │
    ├─build       //项目构建(webpack)相关代码
    │      build.js   // 生产环境构建代码
    │      check-versions.js   // 检查node&npm等版本
    │      dev-client.js       // 热加载相关
    │      dev-server.js       // 构建本地服务器
    │      utils.js          // 构建配置公用工具
    │      vue-loader.conf.js      // vue加载器
    │      webpack.base.conf.js    // webpack基础环境配置
    │      webpack.dev.conf.js      //  webpack开发环境配置
    │      webpack.prod.conf.js     // webpack生产环境配置
    │
    ├─config       // 项目开发环境配置相关代码
    │      dev.env.js    // 开发环境变量
    │      index.js      //项目一些配置变量
    │      prod.env.js     // 生产环境变量
    │
    │
    ├─src   // 源码目录
    │  │  App.vue   // 页面入口文件（根组件）
    │  │  main.js   // 程序入口文件（入口js文件）
    │  │  mock.js   //模拟数据
    │  │
    │  ├─api  //请求相关文件
    │  │      api.js   //相关请求函数，`备注：由于需要兼容ie9，此文件未被使用`，
    │  │      http.js   //axios配置，`备注：由于需要兼容ie9，此文件未被使用`，
    │  │      url.js   //url路径
    │  │
    │  ├─assets  //静态文件
    │  │  │
    │  │  ├─css
    │  │  │      layout.css
    │  │  │      public.css
    │  │  │
    │  │  └─images
    │  │          head-bg.jpg
    │  │          header-ng.png
    │  │          logo.png
    │  │          s-right.png
    │  │
    │  ├─components    // vue公共组件
    │  │      chart.vue
    │  │      config-model.vue
    │  │      content-nav.vue
    │  │
    │  ├─router  //路由配置文件
    │  │      index.js
    │  │
    │  ├─util //公共方法和常量
    │  │      constant.js  //常量
    │  │      util.js   //公共方
    │  │
    │  └─views  //视图组件
    │      ├─components   //页面组件
    │      │      block.vue
    │      │      blockDetail.vue
    │      │      home.vue
    │      │      nodeConfig.vue
    │      │      pendingTransaction.vue
    │      │      pendingTransactionDetail.vue
    │      │      transaction.vue
    │      │      transactionDetail.vue
    │      │
    │      └─layout //布局组件
    │              header.vue
    │              main.vue
    │
    └─static