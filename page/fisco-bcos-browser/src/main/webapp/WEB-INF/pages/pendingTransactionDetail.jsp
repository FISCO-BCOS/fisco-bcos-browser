<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Transaction ${pkHash}</title>
    <%--公共的css和js文件--%>
    <%@ include file="../pages/comm/JSandCSS.jsp"%>
<body>
<div class="wrapper">
    <%@ include file="../pages/comm/header.jsp"%>

    <div class="breadcrumbs">
        <div class="container">
            <h1 class="pull-left">交易 &nbsp;<span class="lead-modify" id="pkHash" style="color: #999999">${pkHash}</span><br />
                <a id="Top"></a>
            </h1>
            <ul class="pull-right breadcrumb">
                <li><a href="../">首页</a></li>
                <li><a href="../pendingTransaction/pendingTransaction.page">正在执行交易</a></li>
                <li class="active">正在执行交易信息</li>
            </ul>
        </div>
    </div>


    <div class="profile container">
        <span id="ContentPlaceHolder1_lblResult"><div style='margin-top:20px; margin-bottom:8px'></div></span><br />
        <div class="tab-v1">
            <ul class="nav nav-tabs" id='nav_tabs'>
                <li class="active"><a href="#overview" data-toggle="tab">交易概览</a></li>
                <%--<li id="ContentPlaceHolder1_li_disqus"><a href="#comments" data-toggle="tab">Comments</a></li>--%>
            </ul>

            <div class="tab-content" style="padding: 1px 0;">
                <div class="tab-pane fade in active" id="overview">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title">交易信息</h3>
                        </div>
                        <div id="ContentPlaceHolder1_maintable" class="container row" style="margin-top:30px">
                            <div class="col-sm-12">
                                    <pre id="result"></pre>
                            </div>
                        </div>
                        <div>
                        </div>
                        <br />
                    </div>
                </div>

                <div class="tab-pane fade in" id="comments">
                    <div class="panel panel-info">
                        <div class="panel-body">
                            <div>
                                Make sure to use the "downvote" button for any spammy posts, and the "upvote" for interesting conversations.<br />
                                <div id="disqus_thread"></div>
                                <noscript>Please enable JavaScript to view the <a href="https://disqus.com/?ref_noscript" rel="nofollow">comments powered by Disqus.</a></noscript>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br />
    </div>
</div>
</body>
<script type="text/javascript">
    //提示工具
    $("[rel='tooltip']").tooltip({ html: true });
    //显示json
    //var tbPendingTransactionDto = <%--${tbPendingTransactionDto};--%>
    //格式化
   // var jsonStr = JSON.stringify(tbPendingTransactionDto, null, 2);
   // $('#result').html(jsonStr);


    //查询参数
    var paramData = {
        pkHash: $("#pkHash").html(),//交易hash
    }

    //获取交易详细信息
    $.ajax({
        url:'../pendingTransaction/getTbPendingTransactionByPkHash.json',//URI
        contentType:"application/json;charset=UTF-8",//设置头信息
        type:'post',
        cache:false,
        dataType:'json',
        data:JSON.stringify(paramData),
        success:function(DATA) {
            if(DATA.status==0){
                if(DATA.data != null){
                    //显示json
                    var jsonStr = JSON.stringify(DATA.data, null, 2);
                    $('#result').html(jsonStr);
                }
            }else {
                console.log("query fail:"+DATA);
                alert("query fail:"+DATA.msg);
            }
        },
        error : function(DATA) {
            console.log("query fail:"+DATA);
        }
    });
</script>
</html>
