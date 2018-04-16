/**
 *@Description: block页面的js
 *@Author: v_wbsqwu
 *@Date: 2017/10/17 10:19
 */

//请求参数
var paramData = {
    pageNumber: 1,//默认第一页
    pageSize:20,//默认每页查询20条数据
    hash:null,
    dateTime1:null,
    dateTime2:null,
}


$( document ).ready(function() {
    /*提示工具*/
    $("[rel='tooltip']").tooltip({ html: true });

    //设置时间选择框
    laydate.render({elem: '#blockDateTime1',type: 'datetime'});
    laydate.render({elem: '#blockDateTime2',type: 'datetime',value:new Date().Format("yyyy-MM-dd hh:mm:ss")});

    $("#blockDateTime1").val("2017-12-15 00:00:00");

    //获取区块表信息列表
    queryBlockList();
});

//定时十秒刷新一次
setInterval("getBlocksListByPage()",30000);

/**
 *@Description: 分页获取区块表信息列表
 */
function getBlocksListByPage() {
    //清空table body
    $("#tableBody").html("");

    $.ajax({
        url:'../block/getTbBlockInfoByPage.json',//URI
        contentType:"application/json;charset=UTF-8",//设置头信息
        type:'post',
        cache:false,
        dataType:'json',
        data:JSON.stringify(paramData),
        success:function(DATA) {
            if(DATA.status==0){
                var blockList = DATA.list;
                if(blockList!=null&&blockList.length>0){
                    var htmlStr = "";
                    for(var index in blockList){
                        var rowData = blockList[index];
                        var GasUsedPercent = rowData.gasUsed/rowData.gasLimit;//取商
                        var GasUsedPercentStr = Math.round(GasUsedPercent*10000)/100+"%";//转换成百分比
                        htmlStr +='<tr>'
                            +'<td><a href="../block/getTbBlockDetailPage.page?blockHash='+rowData.pkHash+'">'+rowData.number+'</a></td>'
                            +' <td>'+rowData.dateTimeStr+'</td>'
                            +'<td><a href="../transaction/transaction.page?blockHeight='+rowData.number+'">'+rowData.transactionNumber+'</a></td>'
                            +'<td><span class="address-tag" style="cursor: pointer" title="'+rowData.miner+'">'+rowData.miner+'</span></td>'
                            +'<td><span class="address-tag" style="cursor: pointer" title="'+rowData.pkHash+'"><a href="../block/getTbBlockDetailPage.page?blockHash='+rowData.pkHash+'">'+rowData.pkHash+'</a></span></td>'
                            +'<td>'+rowData.gasUsed+' <span style="font-family: Monospace;">('+GasUsedPercentStr+')</span></td>'
                            +' <td>'+rowData.gasLimit+'</td>'
                            +'</tr>'
                    }
                    $("#tableBody").html(htmlStr);

                }
                //添加分页
                addPaginator(DATA.pageNumber,DATA.pageSize,DATA.pageTotal);

            }else {
                alert(DATA.msg);
            }

        },
        error : function(DATA) {
            console.log("query fail:"+DATA);
        }
    });
}



/**
 *@Description: 添加分页
 */
function addPaginator(pageNumber,pageSize,totalPages) {

    $("#pagination3").pagination({
        currentPage: pageNumber,
        totalPage: totalPages,
        isShow: true,
        count: 7,
        homePageText: "首页",
        endPageText: "尾页",
        prevPageText: "上一页",
        nextPageText: "下一页",
        callback: function (currentPage) {
            onPaginatorClick(currentPage,pageSize);
        }
    });
}

//点击分页按钮之后
function onPaginatorClick(pageNumber,pageSize) {
    paramData.pageNumber = pageNumber;//页码
    paramData.pageSize = pageSize;//页面大小
    getBlocksListByPage();
}


/**
 *@Description: 根据hash查询
 */
function queryBlockList() {
    paramData.hash = $("#hashVal").val();
    paramData.dateTime1 = $("#blockDateTime1").val();
    paramData.dateTime2 = $("#blockDateTime2").val();
    paramData.pageNumber=1;
    paramData.pageSize=20;

    getBlocksListByPage();
}