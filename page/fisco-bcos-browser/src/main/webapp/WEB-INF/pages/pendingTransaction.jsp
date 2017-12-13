<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Pending Transactions</title>
    <%--公共的css和js文件--%>
    <%@ include file="../pages/comm/JSandCSS.jsp"%>
</head>
<body>
<div class="wrapper">
    <%@ include file="../pages/comm/header.jsp"%>

    <div class="breadcrumbs">
        <div class="container">
            <h1 class="pull-left">正在处理的交易
                <span class="lead-modify" style="color: #999999">&nbsp;</span><br />
            </h1>
            <ul class="pull-right breadcrumb">
                <li><a href="../">首页</a></li>
                <li class="active">正在处理的交易</li>
            </ul>
        </div>
    </div>



    <div class="profile container" style="margin-top: 5px">
        <br />
        <div class="row">
            <div class="col-md-6 hidden-xs">
                <span><i id="spinwheel" class="fa fa-spin fa-spinner fa-2x fa-pulse" style="display: none; margin-top: 4px">&nbsp;</i></span>
               <%-- <span>Connected to 29 peers. A total of 618 Pending txns found<br><br></span>--%>
            </div>

        </div>
        <div class="row">
            <div>

                <div>
                    <div class="table-responsive block">
                        <table class="table table-hover ">
                            <thead>
                            <tr style="border-color: #E1E1E1; border-width: 1px; background-color: #F9F9F9; border-top-style: solid;">
                            <tr style="border-color: #E1E1E1; border-width: 1px; background-color: #F9F9F9; border-top-style: solid;">
                                <th>哈希</th>
                                <th class="hidden-sm">所属块</th>
                                <th>交易块内ID</th>
                                <th>发送者</th>
                                <th></th>
                                <th>接收者</th>
                                <th>消耗Gas</th>
                                <th>Gas价格</th>
                                <th>累积Gas</th>
                                <th>randomId</th>
                                <th>类型</th>
                            </tr>
                            </tr>
                            </thead>
                            <tbody id="tableBody">
                            </tbody>
                        </table>
                    </div>
                </div>

            </div>
        </div>

        <%--分页--%>
        <div class="row">
            <div class="box">
                <div id="pagination3" class="page fl"></div>
            </div>
        </div>
        <br /><br />
    </div>
<%--    <div id="push"></div>--%>

</div>
</body>
<script type="text/javascript" src="../js/pendingTransaction.js"></script>
</html>
