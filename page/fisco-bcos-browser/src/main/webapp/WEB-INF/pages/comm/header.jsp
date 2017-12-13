<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="header">
    <div class="collapse navbar-collapse mega-menu navbar-responsive-collapse">
        <div class="container">
            <div class="nav-left"><span style="margin-left: 10px;" height="74px">FISCO BCOS</span><%--<img style="margin-left: 10px;" height="74px" src="../images/fisco/logo.png">--%></div>
            <ul class="nav navbar-nav">
                <li id="LI_default"><a href="../home/home.page">首页</a></li>
                <li id="LI_blockchain" class="dropdown">
                    <a href="#" class data-toggle="dropdown">区块链信息</a>
                    <ul class="dropdown-menu">
                        <li id="LI11"><a href="../block/block.page">查看区块</a></li>
                        <li id="LI12"><a href="../transaction/transaction.page">查看交易</a></li>
                        <li id="LI16"><a href="../pendingTransaction/pendingTransaction.page">正在处理的交易</a></li>
                    </ul>
                </li>
                <li id="LI_login" class="hidden-lg hidden-md ">
                    <a href="/login" title="Login Now">&nbsp;Login</a>
                </li>
            </ul>
        </div>
    </div>
</div>