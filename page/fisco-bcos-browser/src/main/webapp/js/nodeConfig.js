/**
 *@Description: nodeConfig页面的js
 *@Author: v_wbsqwu
 *@Date: 2017/10/17 15:20
 */

//请求参数
var paramData = {
    pageNumber: 1,//默认第一页
    pageSize:20,//默认每页查询20条数据
    ipVal:null,
    rpcPortVal:null
}

$( document ).ready(function() {
    /*提示工具*/
    $("[rel='tooltip']").tooltip({ html: true });

    //获取交易信息列表
    getTransactionListByPage();
});

/**
 *@Description: 分页获取交易信息列表
 */
function getTransactionListByPage() {
    //清空table body
    $("#tableBody").html("");

    $.ajax({
        url:'../nodeConfig/getTbNodeConnectionByPage.json',//URI
        contentType:"application/json;charset=UTF-8",//设置头信息
        type:'post',
        cache:false,
        dataType:'json',
        data:JSON.stringify(paramData),
        success:function(DATA) {
            if(DATA.status==0){
                var blockList = DATA.list;
                var htmlStr = "";
                if(blockList!=null&&blockList.length>0){
                    for(var index in blockList){
                        var rowData = blockList[index];
                        htmlStr +='<tr>'
                            +'<td>'+rowData.pkId+'</td>'
                            +'<td>'+rowData.ip+'</td>'
                            +'<td>'+rowData.rpcPort+'</td>'
                            +'<td><span class="glyphicon glyphicon-edit" onclick="openEditModal('+rowData.pkId+')"/><span class="glyphicon glyphicon-trash" style="margin-left: 15px;" onclick="confirmDelete('+rowData.pkId+')"/></td>'
                            +'</tr>'
                    }
                }
                $("#tableBody").html(htmlStr);

                //添加分页
                addPaginator(DATA.pageNumber,DATA.pageSize,DATA.pageTotal);

            }else {
                console.log("query fail:"+DATA);
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
 *@Description: 条件查询
 */
function queryNodeConnectionList() {
    paramData.ipVal = $("#ipVal").val();
    paramData.rpcPortVal = $("#rpcPortVal").val();
    paramData.pageNumber=1;
    paramData.pageSize=20;

    getTransactionListByPage();
}




/**
 *@Description: 打开新增节点配置的模态框
 */
function openAddModal() {
    //清空输入框
    $("#addIp").val("");
    $("#addRPC").val("")
    $("#addModal").modal("show");
}


/**
 *@Description: 新增节点配置
 */
function addIpConfig() {
    var ipVal = $("#addIp").val();
    var portVal = $("#addRPC").val();
    if(ipVal.length>16){
        alert("ip不应超过16位字符");
        return;
    }
    if(portVal.length>11){
        alert("RPC端口不应超过11位字符");
        return;
    }

    if(!check_IP(ipVal)){
        alert("请输入正确的ip");
        return;
    }

    if(!check_Number(portVal)){
        alert("请输入正确的端口");
        return;
    }

    if(portVal.length>5) {
        alert("端口请不要超过5位数");
        return;
    }

    //请求参数
    var addRowParam = {
        ip:ipVal,
        rpcPort:portVal
    }
    $.ajax({
        url:'../nodeConfig/addNodeConfigRow.json',//URI
        contentType:"application/json;charset=UTF-8",//设置头信息
        type:'post',
        cache:false,
        dataType:'json',
        data:JSON.stringify(addRowParam),
        success:function(DATA) {
            if(DATA.status==0){
                alert("保存成功");
                $("#addModal").modal("hide");
                getTransactionListByPage();
            }else {
                alert(DATA.msg);
            }

        },
        error : function(DATA) {
            var jsonStr = JSON.stringify(DATA);
            alert("addIpConfig fail:"+jsonStr);
        }
    });
}


/**
 *@Description: 打开修改节点配置的模态框
 */
function openEditModal(pkId) {
    //请求参数
    var rowDataParam = {
        pkId:pkId
    }
    $.ajax({
        url:'../nodeConfig/getTbNodeConnectionByPkId.json',//URI
        contentType:"application/json;charset=UTF-8",//设置头信息
        type:'post',
        cache:false,
        dataType:'json',
        data:JSON.stringify(rowDataParam),
        success:function(DATA) {
            if(DATA.status==0){
                var rowData = DATA.data;
                $("#editId").val(rowData.pkId);
                $("#editIp").val(rowData.ip);
                $("#editRPC").val(rowData.rpcPort);
            }else {
                alert(DATA.msg);
            }

        },
        error : function(DATA) {
            alert("query fail:"+DATA);
        }
    });

    //打开模态框
    $("#editModal").modal("show");
};


/**
 *@Description: 保存节点配置
 */
function editIpConfig() {
    var ipVal = $("#editIp").val();
    var portVal = $("#editRPC").val();
    if(ipVal.length>16){
        alert("ip不应超过16位字符");
        return;
    }
    if(portVal.length>11){
        alert("RPC端口不应超过11位字符");
        return;
    }

    if(!check_IP(ipVal)){
        alert("请输入正确的ip");
        return;
    }

    if(!check_Number(portVal)){
        alert("请输入正确的端口");
        return;
    }

    if(portVal.length>5) {
        alert("端口请不要超过5位数");
        return;
    }

    //请求参数
    var editRowParam = {
        pkId:$("#editId").val(),
        ip:ipVal,
        rpcPort:portVal
    }
    $.ajax({
        url:'../nodeConfig/editNodeConfigRow.json',//URI
        contentType:"application/json;charset=UTF-8",//设置头信息
        type:'post',
        cache:false,
        dataType:'json',
        data:JSON.stringify(editRowParam),
        success:function(DATA) {
            if(DATA.status==0){
                alert("保存成功");
                $("#editModal").modal("hide");
                getTransactionListByPage();
            }else {
                alert(DATA.msg);
            }

        },
        error : function(DATA) {
            alert("save fail:"+DATA);
        }
    });
}


/**
 *@Description: 确认是否删除
 */
function confirmDelete(pkId) {
    if(confirm("是否删除该记录？")){
        deleteIpConfig(pkId);
    }
}

/**
 *@Description: 删除节点配置
 */
function deleteIpConfig(pkId) {
    //请求参数
    var deleteRowParam = {
        pkId:pkId
    }
    $.ajax({
        url:'../nodeConfig/deleteNodeConfigRow.json',//URI
        contentType:"application/json;charset=UTF-8",//设置头信息
        type:'post',
        cache:false,
        dataType:'json',
        data:JSON.stringify(deleteRowParam),
        success:function(DATA) {
            if(DATA.status==0){
                alert("删除成功！");
                getTransactionListByPage();
            }else {
                alert("delete fail:"+DATA.msg);
            }

        },
        error : function(DATA) {
            alert("delete fail:"+DATA);
        }
    });
}

/**
 *@Description: 是否是ip
 */
function check_IP(ip){
    var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;//正则表达式
    if(re.test(ip))
    {
        if( RegExp.$1<256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256)
            return true;
    }
    return false;
}

/**
 *@Description: 是否是正整数
 */
function check_Number(str) {
    var r = /^[0-9]*[1-9][0-9]*$/　　//正整数
    return r.test(str); //str为你要判断的字符 执行返回结果 true 或 fals
}