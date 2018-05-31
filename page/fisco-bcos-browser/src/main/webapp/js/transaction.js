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
 * @file: transaction.js
 * @author: v_wbsqwu
 * @date: 2018
 */

//请求参数
var paramData = {
    pageNumber: 1,//默认第一页
    pageSize:20,//默认每页查询20条数据
    blockHeight:null,//块高
    hash:null,
    dateTime1:null,
    dateTime2:null,
}

$( document ).ready(function() {
    /*提示工具*/
    $("[rel='tooltip']").tooltip({ html: true });

    //设置时间选择框
    laydate.render({elem: '#transDateTime1',type: 'datetime'});
    laydate.render({elem: '#transDateTime2',type: 'datetime',value:new Date().Format("yyyy-MM-dd hh:mm:ss")});

    $("#transDateTime1").val("2017-12-15 00:00:00");

    //获取块高
    paramData.blockHeight=$("#blockHeight").val();

    //获取交易信息列表
    queryTransactionList();
});

//定时十秒刷新一次
setInterval("getTransactionListByPage()",30000);


/**
 *@Description: 分页获取交易信息列表
 */
function getTransactionListByPage() {
    //清空table body
    $("#tableBody").html("");


    $.ajax({
        url:'../transaction/getTbTransactionInfoByPage.json',//URI
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
            paramData.pageNumber = currentPage;
            paramData.pageSize = pageSize;
            getTransactionListByPage();
        }
    });
}



/**
 *@Description: 根据hash查询
 */
function queryTransactionList() {
    paramData.hash = $("#hashVal").val();
    paramData.dateTime1 = $("#transDateTime1").val();
    paramData.dateTime2 = $("#transDateTime2").val();
    paramData.pageNumber=1;
    paramData.pageSize=20;

    getTransactionListByPage();
}