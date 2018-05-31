/**
 * This file is part of FISCO BCOS Browser.
 *
 * FISCO BCOS Browser is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FISCO BCOS Browser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FISCO BCOS Browser.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * @file: home.js
 * @author: v_wbsqwu
 * @date: 2018
 */
var pageNumber = 1;//列表查询的起始页
var pageSize = 10;//列表查询的页面大小

$( document ).ready(function() {
    /*提示工具*/
    $("[rel='tooltip']").tooltip({ html: true });

    //获取区块链全局信息
    getTbBlockChainInfo();

    //显示最近14天数据的折线图
    getTxnByLastFourteenDay();

    //获取区块表信息列表
    getBlocksList();

    //获取交易信息列表
    getTransactionList();

    //查询节点的信息表的数据
    getTbNodesInfoList();
});
//指定10秒刷新一次
setInterval("getTbBlockChainInfo()",10000);
setInterval("getTxnByLastFourteenDay()",10000);
setInterval("getBlocksList()",10000);
setInterval("getTransactionList()",10000);
setInterval("getTbNodesInfoList()",10000);



/**
 *@Description: 获取最近14天的交易数据
 */
function getTxnByLastFourteenDay() {
    $.ajax({
        url:'../home/getTxnByLastFourteenDay.json',//URI
        contentType:"application/json;charset=UTF-8",//设置头信息
        type:'post',
        cache:false,
        dataType:'json',
        data:"",
        success:function(DATA) {
            if(DATA.status==0){
                var data = DATA.data;
                if(data!=null&&data.length>0){
                    var transactionDataArr = new Array();//交易数据的数组
                    var dataArr = new Array();//日期的数组
                    for(var index in data){
                        dataArr[index] = data[index].pkDateStr;
                        transactionDataArr[index] = data[index].transactionNumber;
                    }

                    //显示折线图
                    showEchart(transactionDataArr,dataArr);
                }
            }else {
                console.log("query fail:"+DATA);
            }

        },
        error : function(DATA) {
            console.log("query fail:"+DATA);
        }
    });
}


/**
 *@Description: 显示echart
 */
function showEchart(transactionDataArr,dataArr) {
    var myChart = echarts.init(document.getElementById('containerchart'));

    var dayNum = dataArr.length;
    var option = {
        title: {
            text:'最近有交易的'+ dayNum+' 天交易量',//题目
            textStyle: {
                fontSize: 13,//题目文字大小
                fontWeight:'bold'//题目文字粗细
            },
            left:'center'//题目文字居中
        },
        legend: {
            height: $("#containerchart").height(),
            width: $("#containerchart").width()
        },
        tooltip:{
            show:true,
            trigger: 'axis',//坐标轴触发，主要在柱状图，折线图等会使用类目轴的图表中使用。
            formatter: function(data){//自定义显示格式
                return '<span style="font-size:10px">' + data[0].name + '</span><br><table ><tr><td style="padding:0">' +
                    '<span style="font-size:10px;color:white">交易量：' + data[0].value + '</a></span><br></td></tr></table>';
            }
        },
        grid:{
            left:35,//结果图相对于容器的左距
            right:0,//结果图相对于容器的右距
            top:30,//结果图相对于容器的上距
            bottom:30//结果图相对于容器的底距
        },
        series: [{
            //  name: '销量',
            type: 'line',//折线
            symbolSize:5,//实心点的大小
            itemStyle : {
                normal : {
                    color:'#7cb5ec',
                    lineStyle:{
                        color:'#FFA500'
                    }
                }
            },
            data: transactionDataArr
        }],
        xAxis: {
            data: dataArr,
            axisLabel:{
                interval: 0//显示所有的横坐标
            },
        },
        yAxis: {
            axisLine: {show: false}, //控制y轴线是否显示
            splitLine: {show: true},// 控制网格线是否显示
            axisTick: {show: false},//去除y轴上的刻度线
            axisLabel: {
                formatter: function (value, index) {//格式化Y轴
                    return value/1000+"k";
                }
            }
        }
    };

    myChart.setOption(option);
}



/**
 *@Description: 获取区块链的全局信息
 */
function getTbBlockChainInfo() {
    $.ajax({
        url:'../home/getTbBlockChainInfo.json',//URI
        contentType:"application/json;charset=UTF-8",//设置头信息
        type:'post',
        cache:false,
        dataType:'json',
        data:"",
        success:function(DATA) {
            if(DATA.status==0){
                var data = DATA.data;
                if(data!=null) {
                    $("#LastBlockNumber").html(data.lastBlock);//最新块高
                    $("#TransactionsNumber").html(data.transactionNumber);//交易数
                    $("#pendingTxnNumber").html(data.pendingTxn);//当前正在处理但还未上链的交易的个数
                    $("#pbftViewNumber").html(data.pbftView);
                }
            }else {
                console.log("query fail:"+DATA);
            }

        },
        error : function(DATA) {
            console.log("query fail:"+DATA);
        }
    });
}

/**
 *@Description: 获取区块表信息列表
 */
function getBlocksList() {
    //请求参数
    var data = {
        pageNumber: pageNumber,
        pageSize:pageSize,
        start:0
    }

    $.ajax({
        url:'../block/getTbBlockInfoByPage.json',//URI
        contentType:"application/json;charset=UTF-8",//设置头信息
        type:'post',
        cache:false,
        dataType:'json',
        data:JSON.stringify(data),
        success:function(DATA) {
            if(DATA.status==0){
                var blockList = DATA.list;
                if(blockList!=null&&blockList.length>0){
                    //清空block
                    $("#scrollbar2").empty();
                    var htmlStr = "";
                    for(var index in blockList){
                        var rowData = blockList[index];
                        htmlStr +='<div class="profile-event">'
                                +'   <div class="date-formats" style="width: 145px; height: 53px; margin-top:2px">'
                                +'       <span><a href="../block/getTbBlockDetailPage.page?blockHash='+rowData.pkHash+'"><font size="2" color="white">区块 '+rowData.number+'</font></a></span>'
                                +'       <small>'+rowData.dateTimeStr+'</small>'
                                +'   </div>'
                                +'   <div class="overflow-h"><p>出块者 <span class="address-tag" style="cursor: pointer" title="'+rowData.miner+'">'+rowData.miner+'</span></p>'
                                +'       <p><a href="../transaction/transaction.page?blockHeight='+rowData.number+'" title="块内交易"><b>'+rowData.transactionNumber+' txns</b></a></p>'
                                +'   </div>'
                                +'</div>'
                    }
                    $("#scrollbar2").html(htmlStr);
                }

            }else {
                console.log("query fail:"+DATA);
                alert("query fail:"+DATA.msg);
            }

        },
        error : function(DATA) {
            console.log("query fail:"+DATA);
        }
    });
}



/**
 *@Description: 获取交易信息列表
 */
function getTransactionList(){
    //请求参数
    var data = {
        pageNumber: pageNumber,
        pageSize:pageSize,
        start:0
    }

    $.ajax({
        url:'../transaction/getTbTransactionInfoByPage.json',//URI
        contentType:"application/json;charset=UTF-8",//设置头信息
        type:'post',
        cache:false,
        dataType:'json',
        data:JSON.stringify(data),
        success:function(DATA) {
            if(DATA.status==0){
                var transactionList = DATA.list;
                if(transactionList!=null&&transactionList.length>0){
                    //清空div
                    $("#scrollbar").empty();
                    var htmlStr = "";
                    for(var index in transactionList){
                        var rowData = transactionList[index];
                        htmlStr +='<div class="profile-post"><span class="profile-post-numb"><i class="fa fa-hdd-o"></i></span>'
                                +'<div class="profile-post-in"><h3 class="heading-xs">交易 <a href="../transaction/getTbTransactionDetailPage.page?pkHash='+rowData.pkHash+'" class="hash-tag-frontpage" title="交易 ：'+rowData.pkHash+'"><font color="#3498db">'+rowData.pkHash+'</font></a><span class="pull-right" style="font-size: small">'+rowData.blockTimesStr+' &nbsp;</span></h3><p> <span class="address-tag" title="发送者：'+rowData.transactionFrom+'">'+rowData.transactionFrom+'</span> <img style="width: 15;height: 15;" src="../images/green-arrow-right.png" > <span class="address-tag" title="接收者：'+rowData.transactionTo+'">'+rowData.transactionTo+'</span></p>'
                                +'</div>'
                                +'</div>'
                    }
                    $("#scrollbar").html(htmlStr);
                }

            }else {
                console.log("query fail:"+DATA);
                alert("query fail:"+DATA.msg);
            }

        },
        error : function(DATA) {
            console.log("query fail:"+DATA);
        }
    });
}



/**
 *@Description:查询节点的信息表的数据
 */
function getTbNodesInfoList() {
    //请求参数
    var data = {
        pageNumber: pageNumber,
        pageSize:pageSize,
        start:0
    }

    $.ajax({
        url:'../home/getTbNodesInfoByPage.json',//URI
        contentType:"application/json;charset=UTF-8",//设置头信息
        type:'post',
        cache:false,
        dataType:'json',
        data:JSON.stringify(data),
        success:function(DATA) {
            if(DATA.status==0){
                var blockList = DATA.list;
                if(blockList != null && blockList.length>0){
                    var htmlStr = "";
                    for(var index in blockList){
                        var rowData = blockList[index];

                        var activeStr = "是";
                        if(rowData.active != undefined && rowData.active=="false"){
                            activeStr = "<font style='color: #ac2925'>否</font>"
                        }

                        htmlStr +='<tr>'
                            +'<td><span class="address-tag" style="cursor: pointer" title="'+rowData.pkId+'">'+rowData.pkId+'</span></td>'
                            +'<td>'+rowData.addr+'</td>'
                            +'<td>'+rowData.blockNumber+'</td>'
                            +'<td>'+activeStr+'</td>'
                            +'</tr>'
                    }
                    $("#nodesInfoTableBody").html(htmlStr);
                }

            }else {
                console.log("query fail:"+DATA);
                alert("query fail:"+DATA.msg);
            }

        },
        error : function(DATA) {
            console.log("query fail:"+DATA);
        }
    });
}