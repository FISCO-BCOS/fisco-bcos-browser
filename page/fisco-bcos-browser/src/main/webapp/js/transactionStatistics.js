/**
 *@Description: 交易流程统计页面的js
 */

//请求参数
var paramData = {
    pageNumber: 1,//默认第一页
    pageSize:20,//默认每页查询20条数据
    hash:null,
    dateStr:new Date().Format("yyyy-MM-dd"),
    timeStartStr:null,
    timeEndStr:null
}


$( document ).ready(function() {
    /*提示工具*/
    $("[rel='tooltip']").tooltip({ html: true });

    //分别设置日期和时间选择框
    laydate.render({elem: '#selectDate',value:new Date().Format("yyyy-MM-dd")});
    laydate.render({elem: '#selectTime1',type: 'time'});
    laydate.render({elem: '#selectTime2',type: 'time',value:new Date().Format("23:59:59")});

    $("#selectTime1").val("00:00:00");//设置默认时间

    //分页获取据交易流程统计信息列表
    getTbStatTransactionByPage();
});

//定时十秒刷新一次
setInterval("getTbStatTransactionByPage()",30000);


/**
 *@Description: 分页获取据交易流程统计信息列表
 */
function getTbStatTransactionByPage() {
    //清空table body
    $("#tableBody").html("");

    $.ajax({
        url:'../transactionStatistics/listTbStatTransactionByPage.json',//URI
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

                        var startMsg = rowData.startMsg==null?"":rowData.startMsg
                        var onChainMsg = rowData.onChainMsg==null?"":rowData.onChainMsg

                        htmlStr +='<tr>'
                            +'<td>'+rowData.object+'</a></td>'
                            +'<td><span class="address-tag" style="cursor: pointer" title="'+rowData.hash+'"><a href="../transaction/getTbTransactionDetailPage.page?pkHash='+rowData.hash+'">'+rowData.hash+'</a></span></td>'
                            +'<td>'+rowData.startTime+'</a></td>'
                            +'<td>'+rowData.onChainTime+'</a></td>'
                            +'<td>'+startMsg+'</a></td>'
                            +'<td>'+onChainMsg+'</a></td>'
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
            console.log("查询节点流失败:"+DATA);
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
    getTbStatTransactionByPage();
}


/**
 *@Description: 查询
 */
function queryTransactionStatisticsList() {
    paramData.hash = $("#hashVal").val();
    paramData.dateStr = $("#selectDate").val();
    paramData.timeStartStr = $("#selectTime1").val();
    paramData.timeEndStr = $("#selectTime2").val();
    paramData.pageNumber=1;
    getTbStatTransactionByPage();
}