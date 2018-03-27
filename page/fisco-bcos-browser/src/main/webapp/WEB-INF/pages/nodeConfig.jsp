<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Transactions Information</title>
    <%--公共的css和js文件--%>
    <%@ include file="../pages/comm/JSandCSS.jsp"%>
    <script type="text/javascript" src="../assets/js/dateUtils.js"></script>
</head>
<body>
<div class="wrapper">
    <%@ include file="../pages/comm/header.jsp"%>

    <div class="breadcrumbs">
        <div class="container">
            <h1 class="pull-left">节点配置<span class="lead-modify" style="color: #999999">&nbsp;</span><br />
            </h1>
            <ul class="pull-right breadcrumb">
                <li><a href="../">首页</a></li>
                <li class="active">节点配置</li>
            </ul>
        </div>
    </div>



    <div class="profile container" style="margin-top: 5px">

        <div class="row">
            <div class="col-md-6 hidden-xs checkWrap" style="padding: 5px 0">
                <ul style="margin-bottom: 10px;margin-top: 10px;">
                    <span style="font-size: 15px;">IP：</span><input type="text" id="ipVal"/>
                    <span style="font-size: 15px;margin-left: 20px;">RPC接口：</span><input type="text" id="rpcPortVal"/>
                    <input  style="margin-left: 20px;" type="button" onclick="queryNodeConnectionList()" value="search"/>
                </ul>
            </div>
        </div>

        <hr/>

        <div class="row">
            <div>
                <div >
                    <div class="table-responsive block">
                        <button class="btn btn-primary btn-sm" onclick="openAddModal()">新增节点</button>
                        <table class="table table-hover ">
                            <thead>
                            <tr style="border-color: #E1E1E1; border-width: 1px; background-color: #F9F9F9; border-top-style: solid;">
                                <th>序号</th>
                                <th>IP</th>
                                <th>RPC接口</th>
                                <th>操作</th>
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
            <div class="col-md-12">
                <div class="row">
                    <div class="box">
                        <div id="pagination3" class="page fl"></div>
                    </div>
                </div>
                <br /><br />
            </div>
        </div>

        <!-- 新增节点模态框（Modal） -->
        <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="addModalLabel">新增节点</h4>
                    </div>
                    <div class="modal-body">
                        <div>
                          <ul><span>IP：</span><input style="margin-left: 53px;" type="text" id="addIp"/></ul>
                          <ul><span>RPC接口：</span><input style="margin-left: 13px;" type="text" id="addRPC"/></ul>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary" onclick="addIpConfig()">提交</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal -->
        </div>

        <!-- 修改节点模态框（Modal） -->
        <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="editModalLabel">修改节点</h4>
                    </div>
                    <div class="modal-body">
                       <div>
                            <ul><span>序号：</span><input style="margin-left: 40px;" type="text" id="editId" readonly="true"/></ul>
                            <ul><span>IP：</span><input style="margin-left: 53px;" type="text" id="editIp"/></ul>
                            <ul><span>RPC接口：</span><input style="margin-left: 13px;" type="text" id="editRPC"/></ul>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary" onclick="editIpConfig()">提交</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal -->
        </div>
</body>

<script language="JavaScript" type="text/javascript" src="../js/nodeConfig.js"></script>
</html>

