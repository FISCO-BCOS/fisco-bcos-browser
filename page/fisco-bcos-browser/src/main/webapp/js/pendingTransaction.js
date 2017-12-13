/**
 *@Description:
 *@Author: dwusiq
 *@Date: 2017/10/17 21:22
 */

var pageNumberDefault = 1;//默认第一页
var pageSizeDefault = 20;//默认每页查询20条数据

$( document ).ready(function() {
    /*提示工具*/
    $("[rel='tooltip']").tooltip({ html: true });

    //分页获取正在处理但还未上链的交易信息列表
    getPendingTransactionListByPage(pageNumberDefault,pageSizeDefault);
});

/**
 *@Description: 分页获取交易信息列表
 */
function getPendingTransactionListByPage(pageNumber,pageSize) {
    //清空table body
    $("#tableBody").html("");

    //请求参数
    var data = {
        pageNumber: pageNumber,
        pageSize:pageSize,
        start:0
    }

    $.ajax({
        url:'../pendingTransaction/getTbPendingTransactionInfoByPage.json',//URI
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
                        htmlStr +='<tr>'
                            +'<td><span class="address-tag" title="'+rowData.pkHash+'"><a href="../pendingTransaction/getPendingTransactionDetailPage.page?pkHash='+rowData.pkHash+'">'+rowData.pkHash+'</a></span></td>'
                            +'<td><span class="address-tag" title="'+rowData.blockHash+'"><a href="../block/getTbBlockDetailPage.page?blockHash='+rowData.blockHash+'">'+rowData.blockHash+'</a></span></td>'
                            +'<td>'+rowData.transactionIndex+'</td>'
                            +'<td><span class="address-tag" title="'+rowData.transactionFrom+'">'+rowData.transactionFrom+'</span></td>'
                            +'<td> <img src="../images/green-arrow-right.png"></td>'
                            +'<td><span style="white-space: nowrap;"><span class="address-tag" title="'+rowData.transactionTo+'">'+rowData.transactionTo+'</span></span></td>'
                            +'<td>'+rowData.gas+'</td>'
                            +'<td>'+rowData.gasPrice+'</td>'
                            +'<td>'+rowData.cumulativeGas+'</td>'
                            +'<td>'+rowData.randomId+'</td>'
                            +'<td>'+rowData.queueType+'</td>'
                            +'</tr>'
                    }
                    $("#tableBody").html(htmlStr);

                    //添加分页
                    addPaginator(DATA.pageNumber,DATA.pageSize,DATA.pageTotal);
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
            getPendingTransactionListByPage(currentPage,pageSize);
        }
    });
}