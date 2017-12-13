<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Blocks Information</title>
    <%--公共的css和js文件--%>
    <%@ include file="../pages/comm/JSandCSS.jsp"%>
</head>
<body>
<input id="blockHeight" type="hidden" value="${blockHeight}">
<div class="wrapper">
    <%@ include file="../pages/comm/header.jsp"%>

    <div class="container left hidden-lg hidden-md" id="divmobilesearch" style="margin-top: 5px; margin-bottom: -18px; padding-right: 20px; padding-left: 20px;">
        <form action="/search" method="GET">
            <input id="txtSearchInputMobile" type="text" placeholder="Search for Account, Tx Hash or Data" class="form-control" style="text-align: center;" name="q" maxlength="100" title="Address, Contract, Txn Hash or Data" />
        </form>
        <br /><br />
    </div>

    <div class="breadcrumbs">
        <div class="container">
            <h1 class="pull-left">区块 <span class="lead-modify" style="color: #999999">&nbsp;</span><br />
            </h1>
            <ul class="pull-right breadcrumb">
                <li><a href="../">首页</a></li>
                <li class="active">区块</li>
            </ul>
        </div>
    </div>


    <div class="profile container " style="margin-top: 5px">
        <br />
        <div class="row">
            <div class="col-md-6 hidden-xs">
            </div>
            <%--<div class="col-md-6">--%>
                <%--<p class="blockPaginator" align="right">--%>
                <%--</p>--%>
            <%--</div>--%>
        </div>
        <div class="row">
            <div>
                <div>
                    <div class="table-responsive block">
                        <table class="table table-hover">
                            <thead>
                            <tr style="border-color: #E1E1E1; border-width: 1px; background-color: #F9F9F9; border-top-style: solid;">
                                <th>块高</th>
                                <th>生成时间</th>
                                <th>交易数量</th>
                                <th>出块者</th>
                                <th>Gas消耗量</th>
                                <th>Gas上限</th>
                            </tr>
                            </thead>
                            <tbody id="tableBody">
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <%--<p class="blockPaginator" align="right">--%>
                <%--</p>--%>
                    <div class="row">
                        <div class="box">
                            <div id="pagination3" class="page fl"></div>
                        </div>
                    </div>
        </div>

        <br /><br />
    </div>

</div>
</body>
<script type="text/javascript" src="../js/block.js"></script>
<%--<script>--%>
    <%--/**--%>
     <%--*@Description: block页面的js--%>
     <%--*@Author: v_wbsqwu--%>
     <%--*@Date: 2017/10/17 10:19--%>
     <%--*/--%>

<%--//请求参数--%>
    <%--var paramData = {--%>
        <%--pageNumber: 1,//默认第一页--%>
        <%--pageSize:20//默认每页查询20条数据--%>
    <%--}--%>


    <%--$( document ).ready(function() {--%>
        <%--/*提示工具*/--%>
        <%--$("[rel='tooltip']").tooltip({ html: true });--%>

        <%--//分页获取区块表信息列表--%>
        <%--getBlocksListByPage();--%>
    <%--});--%>

    <%--/**--%>
     <%--*@Description: 分页获取区块表信息列表--%>
     <%--*/--%>
    <%--function getBlocksListByPage() {--%>
        <%--//清空table body--%>
        <%--$("#tableBody").html("");--%>

        <%--$.ajax({--%>
            <%--url:'../block/getTbBlockInfoByPage.json',//URI--%>
            <%--contentType:"application/json;charset=UTF-8",//设置头信息--%>
            <%--type:'post',--%>
            <%--cache:false,--%>
            <%--dataType:'json',--%>
            <%--data:JSON.stringify(paramData),--%>
            <%--success:function(DATA) {--%>
                <%--if(DATA.status==0){--%>
                    <%--var blockList = DATA.list;--%>
                    <%--if(blockList.length>0){--%>
                        <%--var htmlStr = "";--%>
                        <%--for(var index in blockList){--%>
                            <%--var rowData = blockList[index];--%>
                            <%--var GasUsedPercent = rowData.gasUsed/rowData.gasLimit;//取商--%>
                            <%--var GasUsedPercentStr = Math.round(GasUsedPercent*10000)/100+"%";//转换成百分比--%>

                            <%--htmlStr +='<tr>'--%>
                                <%--+'<td><a href="../block/getTbBlockDetailPage.page?blockHash='+rowData.pkHash+'"">'+rowData.number+'</a></td>'--%>
                                <%--+' <td>'+rowData.dateTimeStr+'</td>'--%>
                                <%--+'<td><a href="../transaction/transaction.page?blockHeight='+rowData.number+'">'+rowData.transactionNumber+'</a></td>'--%>
                                <%--+'<td>'+rowData.miner+'</td>'--%>
                                <%--+'<td>'+rowData.gasUsed+' <span style="font-family: Monospace;">('+GasUsedPercentStr+')</span></td>'--%>
                                <%--+' <td>'+rowData.gasLimit+'</td>'--%>
                                <%--+' <td>'+rowData.avgGasPrice+'</td>'--%>
                                <%--+'</tr>'--%>
                        <%--}--%>
                        <%--$("#tableBody").html(htmlStr);--%>

                        <%--//添加分页--%>
                        <%--addPaginator(DATA.pageNumber,DATA.pageSize,DATA.pageTotal);--%>
                    <%--}--%>

                <%--}else {--%>
                    <%--console.log("query fail:"+DATA);--%>
                <%--}--%>

            <%--},--%>
            <%--error : function(DATA) {--%>
                <%--console.log("query fail:"+DATA);--%>
            <%--}--%>
        <%--});--%>
    <%--}--%>



    <%--/**--%>
     <%--*@Description: 添加分页--%>
     <%--*/--%>
    <%--function addPaginator(pageNumber,pageSize,totalPages) {--%>

        <%--$("#pagination3").pagination({--%>
            <%--currentPage: pageNumber,--%>
            <%--totalPage: totalPages,--%>
            <%--isShow: true,--%>
            <%--count: 7,--%>
            <%--homePageText: "首页",--%>
            <%--endPageText: "尾页",--%>
            <%--prevPageText: "上一页",--%>
            <%--nextPageText: "下一页",--%>
            <%--callback: function (currentPage) {--%>
                <%--onPaginatorClick(currentPage,pageSize);--%>
            <%--}--%>
        <%--});--%>
    <%--}--%>

    <%--//点击分页按钮之后--%>
    <%--function onPaginatorClick(pageNumber,pageSize) {--%>
        <%--paramData.pageNumber = pageNumber;//页码--%>
        <%--paramData.pageSize = pageSize;//页面大小--%>
        <%--getBlocksListByPage();--%>
    <%--}--%>

<%--</script>--%>
</html>

