<%--
  Created by IntelliJ IDEA.
  User: v_wbgjzhang
  Date: 2017/11/22
  Time: 16:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Transaction ${pkHash}</title>
    <%--公共的css和js文件--%>
    <%@ include file="../pages/comm/JSandCSS.jsp"%>
    <link rel="stylesheet" href="../assets/css/jquery.multiselect.css">
    <script type="text/javascript" src="../echarts/js/echarts.min.js"></script>
    <script type="text/javascript" src="../assets/js/dateUtils.js"></script>
    <link rel="stylesheet" href="../css/singleStatistics.css">

</head>
<body>

<%--echart--%>
<div class="wrapper">
    <%@ include file="../pages/comm/header.jsp"%>

    <div class="breadcrumbs">
        <div class="container">
            <h1 class="pull-left">统计指标 <span class="lead-modify" style="color: #999999">&nbsp;</span><br />
            </h1>
            <ul class="pull-right breadcrumb">
                <li><a href="../">首页</a></li>
                <li class="active">统计指标</li>
            </ul>
        </div>
    </div>

    <div class="profile container " style="margin-top: 5px">
        <div class="row">
            <form class="form-horizontal" role="form">
                <div class="form-group">
                    <label class="col-xs-1 control-label">日期</label>
                    <div class="col-xs-11">
                        <input type="text" id="selectDate"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-1 control-label">时间</label>
                    <div class="col-sm-11">
                        <input type="text" id="selectTime1"/><span style="font-size: 14px;"> 至：</span>
                        <input type="text" id="selectTime2"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-1 control-label">区块链节点</label>
                    <div class="col-sm-11">
                        <select id='objSelect' multiple="multiple">

                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-1 control-label">选择指标</label>
                    <div class="col-sm-11">
                        <input type="button" value="全选" onclick="checkAllAttr()">
                        <input type="button" value="取消" onclick="cancelAllCheck()">
                        <br/>
                        <div id="attrView" style="overflow-y: scroll"> </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-1 control-label">
                        <button type="button" class="btn btn-info btn-sm" onclick="queryStstList()">查询</button>
                    </label>
                </div>
            </form>
        </div>
    </div>




    <%--
        <div class="container left " id="divmobilesearch" >
            <div style="margin-bottom: 10px;">
                <ul style="margin-bottom: 10px;">
                <span style="font-size: 14px;">日期:</span> <input type="text" id="selectDate"/>
                </ul>
                <ul style="margin-bottom: 10px;">
                    <span style="font-size: 14px;">时间:</span> <input type="text" id="selectTime1"/>至</span> <input type="text" id="selectTime2"/>
                </ul>
            </div>
           &lt;%&ndash;对象下拉列表&ndash;%&gt;
            <div id="objDiv" style="margin-bottom: 10px;">
                <span style="font-size: 14px;height: 30px;">选择区块链节点:</span>
                <select id='objSelect' multiple="multiple">
                </select>
            </div>
             &lt;%&ndash;属性下拉列表&ndash;%&gt;
               <span style="font-size: 14px;">选择指标:</span>
               <input type="button" value="全选" onclick="checkAllAttr()">
               <input type="button" value="取消" onclick="cancelAllCheck()">
               <br/>
            <div id="attrView" style="overflow-y: scroll">
            </div>
           <input type="button" onclick="queryStstList()" value="查询"/>
        </div>
    --%>



    <div class="profile container " style="margin-top: 0px">
        <div class="profile-body" id="allEchartContainer">
        </div>
    </div>
</div>
</body>
<script type="text/javascript" charset="utf-8" src="../js/singleStatistics.js"></script>
</html>
