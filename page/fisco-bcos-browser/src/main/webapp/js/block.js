// /**
//  *@Description: block页面的js
//  *@Author: v_wbsqwu
//  *@Date: 2017/10/17 10:19
//  */
//
// //请求参数
// var paramData = {
//     pageNumber: 1,//默认第一页
//     pageSize:20//默认每页查询20条数据
// }
//
//
// $( document ).ready(function() {
//     /*提示工具*/
//     $("[rel='tooltip']").tooltip({ html: true });
//
//     //分页获取区块表信息列表
//     getBlocksListByPage();
// });
//
// /**
//  *@Description: 分页获取区块表信息列表
//  */
// function getBlocksListByPage() {
//     //清空table body
//     $("#tableBody").html("");
//
//     $.ajax({
//         url:'../block/getTbBlockInfoByPage.json',//URI
//         contentType:"application/json;charset=UTF-8",//设置头信息
//         type:'post',
//         cache:false,
//         dataType:'json',
//         data:JSON.stringify(paramData),
//         success:function(DATA) {
//             if(DATA.status==0){
//                 var blockList = DATA.list;
//                 if(blockList.length>0){
//                     var htmlStr = "";
//                     for(var index in blockList){
//                         var rowData = blockList[index];
//                         var GasUsedPercent = rowData.gasUsed/rowData.gasLimit;//取商
//                         var GasUsedPercentStr = Math.round(GasUsedPercent*10000)/100+"%";//转换成百分比
//
//                         htmlStr +='<tr>'
//                                 +'<td><a href="../block/getTbBlockDetailPage.page?blockHash='+rowData.pkHash+'"">'+rowData.number+'</a></td>'
//                                 +' <td>'+rowData.dateTimeStr+'</td>'
//                                 +'<td><a href="../transaction/transaction.page?blockHeight='+rowData.number+'">'+rowData.transactionNumber+'</a></td>'
//                                 +'<td>'+rowData.miner+'</td>'
//                                 +'<td>'+rowData.gasUsed+' <span style="font-family: Monospace;">('+GasUsedPercentStr+')</span></td>'
//                                 +' <td>'+rowData.gasLimit+'</td>'
//                                 +' <td>'+rowData.avgGasPrice+'</td>'
//                                 +'</tr>'
//                     }
//                     $("#tableBody").html(htmlStr);
//
//                     //添加分页
//                     addPaginator(DATA.pageNumber,DATA.pageSize,DATA.pageTotal);
//                 }
//
//             }else {
//                 console.log("query fail:"+DATA);
//             }
//
//         },
//         error : function(DATA) {
//             console.log("query fail:"+DATA);
//         }
//     });
// }
//
//
//
// /**
//  *@Description: 添加分页
//  */
// function addPaginator(pageNumber,pageSize,totalPages) {
//     var $paginator = $('.blockPaginator');
//     //清空分页
//     $paginator.html("");
//
//     //前一页和首页
//     var preAndFirstHtml = "";
//     if(pageNumber<=1){
//         preAndFirstHtml ='<a Class="btn btn-primary btn-xs " disabled="disabled">First</a>'
//                         +'<a Class="btn btn-primary btn-xs" disabled="disabled">Prev</a>'
//     }else {
//         paramData.pageNumber =
//         preAndFirstHtml = '<a Class="btn btn-default btn-xs logout"  onclick="onPaginatorClick(1,'+pageSize+')">First</a>'
//                         +'<a Class="btn btn-default btn-xs logout" onclick="onPaginatorClick('+(pageNumber-1)+','+pageSize+')">Prev</a>'
//     }
//
//     //下一页和最后一页
//     var nextAndLastHtml = "";
//     if(pageNumber>=totalPages){
//         nextAndLastHtml ='<a Class="btn btn-primary btn-xs " disabled="disabled">Next</a>'
//                         +'<a Class="btn btn-primary btn-xs" disabled="disabled">Last</a>'
//     }else {
//         nextAndLastHtml ='<a Class="btn btn-default btn-xs logout" onclick="onPaginatorClick('+(pageNumber+1)+','+pageSize+')">Next</a>'
//                         +'<a Class="btn btn-default btn-xs logout" onclick="onPaginatorClick('+totalPages+','+pageSize+')" >Last</a>'
//     }
//
//     //中间部分
//     var pageShowHtml = '<span style="padding: 2px 4px 4px 3px; border: 1px solid #D4D4D4; line-height: 30px; background-color: #EAEAEA; margin-top: 2px; height: 2px;">Page <b>'+pageNumber+'</b> of <b>'+totalPages+'</b></span>';
//
//
//     //分页
//     var htmlStr = preAndFirstHtml + pageShowHtml + nextAndLastHtml;
//     $paginator.html(htmlStr);
// }
//
// //点击分页按钮之后
// function onPaginatorClick(pageNumber,pageSize) {
//     paramData.pageNumber = pageNumber;//页码
//     paramData.pageSize = pageSize;//页面大小
//     getBlocksListByPage();
// }



/**
 *@Description: block页面的js
 *@Author: v_wbsqwu
 *@Date: 2017/10/17 10:19
 */

//请求参数
var paramData = {
    pageNumber: 1,//默认第一页
    pageSize:20//默认每页查询20条数据
}


$( document ).ready(function() {
    /*提示工具*/
    $("[rel='tooltip']").tooltip({ html: true });

    //分页获取区块表信息列表
    getBlocksListByPage();
});

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
                if(blockList.length>0){
                    var htmlStr = "";
                    for(var index in blockList){
                        var rowData = blockList[index];
                        var GasUsedPercent = rowData.gasUsed/rowData.gasLimit;//取商
                        var GasUsedPercentStr = Math.round(GasUsedPercent*10000)/100+"%";//转换成百分比

                        htmlStr +='<tr>'
                            +'<td><a href="../block/getTbBlockDetailPage.page?blockHash='+rowData.pkHash+'"">'+rowData.number+'</a></td>'
                            +' <td>'+rowData.dateTimeStr+'</td>'
                            +'<td><a href="../transaction/transaction.page?blockHeight='+rowData.number+'">'+rowData.transactionNumber+'</a></td>'
                            +'<td>'+rowData.genIndex+'</td>'
                            +'<td>'+rowData.gasUsed+' <span style="font-family: Monospace;">('+GasUsedPercentStr+')</span></td>'
                            +' <td>'+rowData.gasLimit+'</td>'
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
