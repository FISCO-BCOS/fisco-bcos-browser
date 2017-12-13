/**
 *@Description: transaction页面的js
 *@Author: v_wbsqwu
 *@Date: 2017/10/17 15:20
 */

//请求参数
var paramData = {
    pageNumber: 1,//默认第一页
    pageSize:20,//默认每页查询20条数据
    blockHeight:null//块高
}

$( document ).ready(function() {
    /*提示工具*/
    $("[rel='tooltip']").tooltip({ html: true });

    //获取块高
    paramData.blockHeight=$("#blockHeight").val();

    //分页获取交易信息列表
    getTransactionListByPage();
});


/**
 *@Description: 分页获取交易信息列表
 */
function getTransactionListByPage(pageNumber,pageSize) {
    //清空table body
    $("#tableBody").html("");

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
                var blockList = DATA.list;
                if(blockList.length>0){
                    var htmlStr = "";
                    for(var index in blockList){
                        var rowData = blockList[index];
                        htmlStr +='<tr>'
                                +'<td><span class="address-tag" title="'+rowData.pkHash+'"><a href="../transaction/getTbTransactionDetailPage.page?pkHash='+rowData.pkHash+'">'+rowData.pkHash+'</a></span></td>'
                                +'<td class="hidden-sm"><a href="../block/getTbBlockDetailPage.page?blockHash='+rowData.blockHash+'">'+rowData.blockNumber+'</a></td>'
                                +'<td>'+rowData.transactionIndex+'</td>'
                                +'<td>'+rowData.blockTimesStr+'</td>'
                                +'<td><span class="address-tag" title="'+rowData.transactionFrom+'">'+rowData.transactionFrom+'</span></td>'
                                +'<td> <img src="../images/green-arrow-right.png" ></td>'
                                +'<td><span style="white-space: nowrap;"><span class="address-tag" title="'+rowData.transactionTo+'">'+rowData.transactionTo+'</span></span></td>'
                                +'</tr>'
                    }
                    $("#tableBody").html(htmlStr);

                    //添加分页
                    addPaginator(DATA.pageNumber,DATA.pageSize,DATA.pageTotal);
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
            getTransactionListByPage(currentPage,pageSize);
        }
    });
}

