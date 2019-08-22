<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Blocks Information</title>
    <%--公共的css和js文件--%>
    <%@ include file="../pages/comm/JSandCSS.jsp"%>
    <script type="text/javascript" src="../assets/js/dateUtils.js"></script>

</head>
<body>
<input id="blockHeight" type="hidden" value="${blockHeight}">
<div class="wrapper">
    <%@ include file="../pages/comm/header.jsp"%>

<%--    <div class="container left hidden-lg hidden-md" id="divmobilesearch" style="margin-top: 5px; margin-bottom: -18px; padding-right: 20px; padding-left: 20px;">
        <form action="/search" method="GET">
            <input id="txtSearchInputMobile" type="text" placeholder="Search for Account, Tx Hash or Data" class="form-control" style="text-align: center;" name="q" maxlength="100" title="Address, Contract, Txn Hash or Data" />
        </form>
        <br /><br />
    </div>--%>

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
        <div class="row">
                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="col-sm-1 control-label">哈希值</label>
                        <div class="col-sm-11">
                            <input type="text" id="hashVal"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-1 control-label">生成时间</label>
                        <div class="col-sm-11">
                            <input type="text" id="blockDateTime1"/><span style="font-size: 14px;"> 至：</span>
                            <input type="text" id="blockDateTime2"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-1 control-label">
                            <button type="button" class="btn btn-info btn-sm" onclick="queryBlockList()">查询</button>
                        </label>
                    </div>
                </form>
        </div>


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
                        <th>哈希</th>
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

</html>
