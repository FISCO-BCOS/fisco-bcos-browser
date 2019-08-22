<template>
    <div class="search-main" style="color: #fff;height: auto;">
        <div class="container" style="overflow: auto;background-color: #2a2c3b;">
            <h1 style="width:100%;text-align:center">FISCO-BCOS 2.0区块链浏览器帮助文档</h1>
            <h2>一、描述</h2>
            <h3>1.1、基本描述</h3>
            <p><strong>全新适配FISCO-BCOS 2.0.0版本，如果使用FISCO-BCOS 1.2或1.3版本请用<a href="https://github.com/FISCO-BCOS/fisco-bcos-browser/releases/tag/v1.2.1">v1.2.1版本</a>。</strong></p>
            <p>区块链浏览器将区块链中的数据可视化，并进行实时展示。方便用户以Web页面的方式，获取当前区块链中的信息。本浏览器版本适配<strong><a href='https://github.com/FISCO-BCOS/FISCO-BCOS/tree/release-2.0.1'>FISCO-BCOS 2.0</a></strong>，关于2.0版本的特性可以参考此<a href='https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.0/docs/introduction.html'>链接</a>。在使用本浏览器之前需要先理解2.0版本的群组特性，详情可以参考此<a href='https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.0/docs/what_is_new.html#id2'>链接</a>。</p>
            <p><img :src='overview' alt='' referrerPolicy='no-referrer'/></p>
            <p>&nbsp;</p>
            <h4>1.2、主要功能模块</h4>
            <p>本小节概要介绍浏览器的各个模块，方便大家对浏览器有一个整体的认识。区块链浏览器主要的功能模块有：群组切换模块，配置模块，区块链信息展示模块。</p>
            <h5>1.2.1、群组切换模块</h5>
            <p>群组切换主要用于在多群组场景中切换到不同群组，进行区块链信息浏览。</p>
            <p><img :src='switchGroup' alt='switch_group' referrerPolicy='no-referrer' /></p>
            <h5>1.2.2、配置模块</h5>
            <p>主要包括群组配置，节点配置，合约配置。</p>
            <p><img :src='groupConfig' alt='group_config' referrerPolicy='no-referrer'/></p>
            <h5>1.2.3、区块链信息展示模块</h5>
            <p>区块链浏览器主要展示了链上群组的具体信息，这些信息包括：概览信息，区块信息，交易信息。</p>
            <p><img :src='show' alt='show.jpg' referrerPolicy='no-referrer' /></p>
            <h2>二、使用前提</h2>
            <h3>2.1、群组搭建</h3>
            <p>区块链浏览器展示的数据是从区块链上同步下来的。为了同步数据需要初始化配置（添加群组信息和节点信息），故在同步数据展示前需要用户先搭建好区块链群组。<strong><a href='https://github.com/FISCO-BCOS/FISCO-BCOS/tree/release-2.0.1'>FISCO-BCOS 2.0</a></strong>提供了多种便捷的群组搭建方式。</p>
            <ol>
            <li>如果是开发者进行开发调试，建议使用<a href='https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.0/docs/installation.html'>build_chain</a>。</li>
            <li>如果是开发企业级应用，建议使用企业部署工具<a href='https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.0/docs/tutorial/enterprise_quick_start.html'>FISCO-BCOS generator</a>。</li>

            </ol>
            <p>两者的主要区别在于build_chain为了使体验更好，搭建速度更快，辅助生成了群组内各个节点的私钥；但企业部署工具出于安全的考虑不辅助生成私钥，需要用户自己生成并设置。</p>
            <h2>三、初始化环境</h2>
            <h3>3.1、添加群组</h3>
            <p><img :src='createGroup' alt='create_group' referrerPolicy='no-referrer' /></p>
            <p>服务搭建成功后，可使用网页浏览器访问nginx配置的前端IP和前端端口，进入到浏览器页面。未初始化群组的浏览器页面会引导大家到新建群组配置页面，新建群组需要配置群组ID，群组名称，描述。</p>
            <p><strong>群组ID需要和区块链群组ID保持一致。</strong>群组ID有多种查看方式，1、<a href="https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.0/docs/api.html#getgrouplist">rpc接口获取</a>。2、<a href="https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.0/docs/manual/console.html">控制台命令</a>。</p>
            <p>群组名称是为群组ID取的一个有意义，便于理解的名字。</p>
            <p>描述字段是对名称的进一步说明。</p>
            <h3>3.2、添加节点</h3>
            <p><img :src='addNode' alt='add_node' referrerPolicy='no-referrer' /></p>
            <p>添加群组所在的节点信息，用于区块链浏览器连接拉取相关展示信息。节点的rpc端口信息和p2p端口信息可以从节点的 <strong>config.ini</strong>配置文件中获取。</p>
            <p>为了使用方便，新添加的群组会自动同步添加其他群组已经配置的共用节点信息。</p>
            <h3>3.3、添加合约</h3>
            <p>本浏览器版本提供合约解析的功能。此功能需要用户把本群组使用的所有合约进行导入。本版本支持用zip包上传一级目录，用于解决同名合约的问题。</p>
            <p>导入步骤：</p>
            <h4>3.3.1 上传合约</h4>
            <ol>
            <li>合约上传支持sol文件上传和将sol文件打包成zip包上传。</li><li>zip包最多支持一级目录，如果没有目录默认上传到根目录。zip包中只能有sol文件。</li></ol>
            <h4>3.3.2 编译合约</h4>
            <p><img :src="contract" alt="contract"></p>
            <h2>四、功能介绍</h2>
            <h3>4.1、概览</h3>
            <h4>4.1.1 概览信息</h4>
            <p>主要包括当前群组的块高，交易总量，正在处理的交易数，PBFT视图。</p>
            <h4>4.1.2 最近15天的交易量</h4>
            <p>用折线图的形式展示了当前群组15内的交易情况。</p>
            <h4>4.1.3 节点概览</h4>
            <p>节点概览展示了当前群组内各个节点的ID，当前快高，pbftView，和节点状态。</p>
            <h4>4.1.4  区块概览</h4>
            <p>区块概览展示了最近4个区块的信息，包括每个区块的块高，出块者，块产生的时间及块上的交易总量。</p>
            <h4>4.1.5  交易概览</h4>
            <p>交易概览展示了最近四个交易，包括交易hash，交易时间，交易的发送者、交易的接收者，如果是正确导入了交易相关的合约还能展出交易调用的接口信息。</p>
            <p><img :src='overview' alt='overview' referrerPolicy='no-referrer' /></p>
            <h3>4.2、区块信息浏览</h3>
            <p>区块信息浏览主要包括区块列表页面和区块详情页面。</p>
            <h3>4.3、交易浏览</h3>
            <p>交易信息浏览主要包括交易列表页面和交易详情页面。</p>
            <h4>4.3.1、交易解析</h4>
            <p>合约成功上传并编译后，区块链浏览器能够解析出此合约相关交易的方法名和参数。浏览器的解析建立在合约的准确导入的基础上，故提醒用户在使用java和js等语言调用合约时，请<strong>注意保存合约的正确版本。</strong></p>
            <p><img :src='transaction' alt='transaction' referrerPolicy='no-referrer' /></p>
            <h4>4.3.2、事件解析</h4>
            <p>合约成功上传并编译后，区块链浏览器能够解析出此合约相关交易回执中的事件方法名和参数。</p>
            <p><img :src='receipt' alt='receipt' referrerPolicy='no-referrer' /></p>
        </div>
    </div>
</template>
<script>
import '@/assets/css/layout.css'
import '@/assets/css/public.css'
import overview from '@/../static/image/overview.png'
import switch_group from '@/../static/image/switch_group.jpg'
import group_config from '@/../static/image/group_config.png'
import show from '@/../static/image/show.jpg'
import create_group from '@/../static/image/create_group.png'
import add_node from '@/../static/image/add_node.png'
import contract from '@/../static/image/contract.png'
import transaction from '@/../static/image/transaction.png'
import receipt from '@/../static/image/receipt.png'
export default {
    name: 'help',
    data: function(){
        return {
            overview: overview,
            switchGroup: switch_group,
            groupConfig: group_config,
            show: show,
            createGroup: create_group,
            addNode: add_node,
            contract: contract,
            transaction: transaction,
            receipt: receipt
        }
    }
}
</script>
<style scoped>
img{
    width: 100%;
}
a{
    color: #3498db;
    text-decoration: none
}
a:hover{
    color: #3498db;
}
a:link{
    color: #3498db;
}
a:active{
    color: #3498db;
}
a:visited{
    color: #3498db;
}
</style>


