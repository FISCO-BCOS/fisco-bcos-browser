<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>Block ${blockHash} Info</title>
    <%--公共的css和js文件--%>
    <%@ include file="../pages/comm/JSandCSS.jsp"%>
</head>
<body>
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
            <h1 class="pull-left">区块 <span class="lead-modify" style="color: #999999">&nbsp;#${blockHash}</span><br />
            </h1>
            <ul class="pull-right breadcrumb">
                <li><a href="../">首页</a></li>
                <li><a href="../block/block.page">区块链</a></li>
                <li class="active">区块信息</li>
            </ul>
        </div>
    </div>


    <div class="profile container blockImfo" id="Top">
        <span id="ContentPlaceHolder1_lblAdResult"></span>
        <br />
        <div class="tab-v1">
            <ul class="nav nav-tabs" id='nav_tabs'>
                <li class="active"><a href="#overview" data-toggle="tab">交易概览</a></li>
                <%--<li id="ContentPlaceHolder1_li_disqus"><a href="#comments" data-toggle="tab" onclick="javascript:loaddisqus();">Comments</a></li>--%>
            </ul>

            <div class="tab-content" style="padding: 1px 0;">
                <div class="tab-pane fade in active" id="overview">
                    <div class="panel panel-info table-responsive">
                        <div class="panel-heading margin-bottom-15">
                            <h3 class="panel-title">区块信息
                            </h3>
                        </div>
                        <div id="blockDetail">
                            <pre id="result"></pre>
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
                                <script type="text/javascript">
                                    var disqus_shortname = 'Etherscan'; var disqus_identifier = 'Etherscan_BlockNo_4375932_Comments'; var disqus_title = 'BlockNo 4375932'; var disqus_url = 'https://etherscan.io/block/4375932#disqus';
                                </script>
                                <noscript>Please enable JavaScript to view the <a href="https://disqus.com/?ref_noscript" rel="nofollow">comments powered by Disqus.</a></noscript>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br />
        <br />
    </div>
</div>
</body>
<script type="text/javascript">
    //提示工具
    $("[rel='tooltip']").tooltip({ html: true });
    //显示json
    var blockDetailInfo = ${blockDetailInfo};
    //格式化
    var jsonStr = JSON.stringify(blockDetailInfo, null, 2);
    $('#result').html(jsonStr);
</script>
</html>
