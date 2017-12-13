<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>FISCO BCOS Browser</title>
    <link rel="stylesheet" href="../assets/css/profile.css">
    <%--公共的css和js文件--%>
    <%@ include file="../pages/comm/JSandCSS.jsp"%>
    <script src="../cdn-cgi/apps/head/XCcIJUja65t7SLXcpK7gLzF5XFY.js"></script>
    <script type="text/javascript" src="../echarts/js/echarts.min.js"></script>
</head>
<body>
<div class="wrapper">
    <%@ include file="../pages/comm/header.jsp"%>
    <div class="container-wrap">
        <div class="profile container " style="margin-top: 0px">
            <div class="row">
                <div>
                    <div class="profile-body">
                        <div align="left" id="divadvert" style="margin-bottom: 11px; margin-top: -10px;">
                            <span id="ContentPlaceHolder1_litIBannerAdd"></span>
                        </div>
                        <div id="ContentPlaceHolder1_mainboxes" class="row margin-bottom-10">
                            <div class="col-sm-6 sm-margin-bottom-20" >
                                <div class="service-block-v3 service-block-blue">
                                    <div class="clearfix margin-bottom-5 "></div>
                                    <div class="row margin-bottom-20 " id="bWrap">
                                        <%--1--%>
                                        <div class="col-xs-6 text-left service-in">
                                            <small>
                                                <a href="../block/block.page" rel="tooltip" data-placement="right">
                                                    <font color="white">当前块高</font>
                                                </a>
                                            </small>
                                            <h4 style="color: #000">
                                                <span id="ContentPlaceHolder1_Label1" class="counter" style="font-size:16px;">
                                                    <a href="../block/block.page" rel="tooltip" data-placement="bottom">
                                                        <font color="white" id="LastBlockNumber"></font>
                                                    </a>
                                                </span>
                                            </h4>
                                        </div>
                                        <%--2--%>
                                        <div class="col-xs-6 text-right service-in">
                                            <small>
                                                <a href="../transaction/transaction.page" rel="tooltip" data-placement="left">
                                                    <font color="white">交易总量</font>
                                                </a>
                                            </small>
                                            <h4 class="counter">
                                                <a href="../transaction/transaction.page" rel="tooltip" data-placement="left">
                                                    <font color="white" id="TransactionsNumber"></font>
                                                </a>
                                            </h4>
                                        </div>
                                        <%--3--%>
                                        <div class="col-xs-6 text-left service-in">
                                        <small>
                                            <a href="../pendingTransaction/pendingTransaction.page" rel="tooltip" data-placement="left">
                                                <font color="white">正在处理交易数</font>
                                            </a>
                                        </small>
                                        <h4 class="counter">
                                            <a href="../pendingTransaction/pendingTransaction.page" rel="tooltip" data-placement="bottom">
                                                <font color="white" id="pendingTxnNumber"></font>
                                            </a>
                                        </h4>
                                    </div>
                                    <%--4--%>
                                    <div class="col-xs-6 text-right service-in">
                                        <small>
                                            <p rel="tooltip" data-placement="left">
                                                <font color="white">PBFT 当前视图</font>
                                            </p>
                                        </small>
                                        <h4 class="counter">
                                            <p rel="tooltip" data-placement="bottom">
                                                <font color="white" id="pbftViewNumber"></font>
                                            </p>
                                        </h4>
                                    </div>

                                    </div>

                                    <%--<div class="statistics">--%>
                                        <%--<h3 class="heading-xs">--%>
                                            <%--<a href="../pendingTransaction/pendingTransaction.page" rel="tooltip" data-placement="right" title="View pending transactions">--%>
                                                <%--<font color="black">正在处理交易数</font>--%>
                                            <%--</a>--%>
                                            <%--<a href="#" class="pull-right" rel="tooltip" data-placement="left" title="pbftViewNumber">--%>
                                                <%--<font color="black">PBFT 当前视图</font>--%>
                                            <%--</a>--%>
                                        <%--</h3>--%>
                                        <%--<a href="../pendingTransaction/pendingTransaction.page" rel="tooltip" data-placement="bottom" title="Total No Of pendingTransactions"><font color="black" id="pendingTxnNumber"></font></a>&nbsp;--%>
                                        <%--<span class="pull-right" rel="tooltip" data-placement="bottom" title="pbftViewNumber"><font color="black" id="pbftViewNumber"></font></span>--%>
                                    <%--</div>--%>


                                </div>
                            </div>

                            <div class="col-sm-6">
                                <div class="tag-box tag-box-v2 box-shadow shadow-effect-1" style="border-right-color: #3498db; border-right-style: solid; border-right-width: 2px; margin-bottom: 0px; background-color: #FFFFFF;">
                                    <div id="containerchart" style="min-width: 255px; height: 183px; margin: 0 auto">
                                    </div>
                                </div>
                            </div>


                        </div>

                        <div class="table-responsive" style="padding: 0 15px">
                            <table class="table table-hover" style="margin-top: 20px">
                                <thead>
                                <tr style="border-color: #E1E1E1; border-width: 1px; background-color: #F9F9F9; border-top-style: solid;">
                                    <th>节点ID</th>
                                    <th>节点端口</th>
                                    <th>当前块高</th>
                                </tr>
                                </thead>
                                <tbody id="nodesInfoTableBody">
                                </tbody>
                            </table>
                        </div>

                        <div class="row margin-bottom-20">
                            <div class="col-sm-6 sm-margin-bottom-20">
                                <div class="panel panel-profile no-bg">
                                    <div class="panel-heading overflow-h">
                                        <h2 class="panel-title heading-sm pull-left"><img src="../images/fisco/qukuai.png">区块</h2>
                                        <a href="../block/block.page" class="btn-brd-hover btn-u-dark btn-u-xs pull-right">更多&nbsp;&nbsp;></a>
                                    </div>
                                    <div id="scrollbar2" class="panel-body no-padding mCustomScrollbar pre-scrollable" data-mcs-theme="minimal-dark" style="height: 580px">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <div class="panel panel-profile no-bg">
                                    <div class="panel-heading overflow-h">
                                        <h2 class="panel-title heading-sm pull-left"><img src="../images/fisco/jiaoyi.png" alt="">交易</h2>
                                        <span style="color: #999999; font-size: 11px; margin-bottom: -20px; padding-bottom: 0px;"></span>
                                        <a href="../transaction/transaction.page" class="btn-brd-hover btn-u-dark btn-u-xs pull-right">更多&nbsp;&nbsp;></a>
                                    </div>
                                    <div id="scrollbar" class="panel-body no-padding mCustomScrollbar pre-scrollable" data-mcs-theme="minimal-dark" style="height: 580px">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <br>

                        <%--<div class="table-responsive">--%>
                            <%--<table class="table table-hover">--%>
                                <%--<thead>--%>
                                <%--<tr style="border-color: #E1E1E1; border-width: 1px; background-color: #F9F9F9; border-top-style: solid;">--%>
                                    <%--<th>节点ID</th>--%>
                                    <%--<th>节点端口</th>--%>
                                    <%--<th>当前块高</th>--%>
                                <%--</tr>--%>
                                <%--</thead>--%>
                                <%--<tbody id="nodesInfoTableBody">--%>
                                <%--</tbody>--%>
                            <%--</table>--%>
                        <%--</div>--%>

                    </div>


                        <%--<div class="row">--%>
                        <%--<div class="col-md-12">--%>
                        <%--<p class="nodesInfoPaginator" align="right">--%>
                        <%--</p>--%>
                        <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="row">--%>
                        <%--<div class="box">--%>
                        <%--<div id="pagination3" class="page fl"></div>--%>
                        <%--&lt;%&ndash;<div class="info fl">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<p>当前页数：<span id="current3">4</span></p>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                        <%--</div>--%>
                        <%--</div>--%>

                        <br /><br />
                    </div>

                </div>
            </div>
        </div>
    </div>

    <%--<div class="profile-wrap">--%>
    <%--<div class="profile container " >--%>
        <%--<br />--%>
        <%--<div class="row">--%>
            <%--<div>--%>
                <%--<div>--%>
                    <%--<div class="table-responsive">--%>
                        <%--<table class="table table-hover">--%>
                            <%--<thead>--%>
                            <%--<tr style="border-color: #E1E1E1; border-width: 1px; background-color: #F9F9F9; border-top-style: solid;">--%>
                                <%--<th>节点ID</th>--%>
                                <%--<th>节点端口</th>--%>
                                <%--<th>当前块高</th>--%>
                            <%--</tr>--%>
                            <%--</thead>--%>
                            <%--<tbody id="nodesInfoTableBody">--%>
                            <%--</tbody>--%>
                        <%--</table>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>

        <%--&lt;%&ndash;<div class="row">&ndash;%&gt;--%>
            <%--&lt;%&ndash;<div class="col-md-12">&ndash;%&gt;--%>
                <%--&lt;%&ndash;<p class="nodesInfoPaginator" align="right">&ndash;%&gt;--%>
                <%--&lt;%&ndash;</p>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<div class="row">&ndash;%&gt;--%>
        <%--&lt;%&ndash;<div class="box">&ndash;%&gt;--%>
            <%--&lt;%&ndash;<div id="pagination3" class="page fl"></div>&ndash;%&gt;--%>
            <%--&lt;%&ndash;&lt;%&ndash;<div class="info fl">&ndash;%&gt;&ndash;%&gt;--%>
                <%--&lt;%&ndash;&lt;%&ndash;<p>当前页数：<span id="current3">4</span></p>&ndash;%&gt;&ndash;%&gt;--%>
            <%--&lt;%&ndash;&lt;%&ndash;</div>&ndash;%&gt;&ndash;%&gt;--%>
        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>

        <%--<br /><br />--%>
    <%--</div>--%>
<%--</div>--%>

<%--    <div id="push"></div>--%>

</div>
</body>
<script type="text/javascript" charset="utf-8" src="../js/home.js"></script>

</html>
