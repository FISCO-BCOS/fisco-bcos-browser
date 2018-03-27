/**
 *Created by v_wbgjzhang on 20171122.
 */


//查询对象和属性的入参
var params={
    dateStr:new Date().Format("yyyy-MM-dd")
}


$( document ).ready(function() {
    //设置日期选择框
    laydate.render({
        elem: '#selectDate', //指定元素
        value:new Date().Format("yyyy-MM-dd"),
        done: function(value, date){
            params.dateStr=value;
            //获取对象列表
            listObject();
            //获取属性列表
            getAttrList();
        }
    });

    //设置时间选择框
    laydate.render({elem: '#selectTime1',type: 'time'});
    laydate.render({elem: '#selectTime2',type: 'time',value:new Date().Format("23:59:59")});

    $("#selectTime1").val("00:00:00");

    //默认查询
    defaultQuqry();
});


/**
 *@Description: 获取属性列表
 */
function getAttrList() {
    $("#attrView").html("");
    var attrCheckBok = "";

    $.ajax({
        url:'../singleStatistics/listAttr.json',//URI
        contentType:"application/json;charset=UTF-8",//设置头信息
        type:'post',
        cache:false,
        dataType:'json',
        data:JSON.stringify(params),
        success:function(DATA) {
            if(DATA.status==0){
                var attrArr = DATA.data;
                for(var i=0;i<attrArr.length;i++){
                    var data = attrArr[i];
                    if(data.attrName=="区块高度"){
                        attrCheckBok += "<input type='checkbox' name='attrs' checked='checked' value='" + data.attr + "'/>"+ data.attrName +"&nbsp&nbsp&nbsp";
                    }else {
                        attrCheckBok += "<input type='checkbox' name='attrs' value='" + data.attr + "'/>"+ data.attrName +"&nbsp&nbsp&nbsp";
                    }

                }
                $("#attrView").append(attrCheckBok);

            }else {
                alert(DATA.msg);
            }

        },
        error : function(DATA) {
            console.log("query fail:"+DATA);
        }
    });
}

/**
 *@Description: 获取对象列表
 */

function listObject() {
    //获取下拉列表对象
    var $ObjSelect = $("#objSelect");
    $ObjSelect.html("");

    $.ajax({
        url:'../singleStatistics/listTbStatObject.json',//URI
        contentType:"application/json;charset=UTF-8",//设置头信息
        type:'post',
        cache:false,
        dataType:'json',
        data:JSON.stringify(params),
        success:function(DATA) {
            if(DATA.status==0){
                var objArr = DATA.data;
                if(objArr != null && objArr != undefined && objArr.length>0){
                    var htmlStr = "";
                    for(var i=0;i<objArr.length;i++){
                        var objStr = objArr[i];
                        htmlStr += "<option  value='"+objStr+"'>"+objStr+"</option>";
                    }
                    //初始化下拉列表
                    $ObjSelect.append(htmlStr);
                    setSelectOption();
                }else{
                    $ObjSelect.multiselect('destroy')
                }
            }else {
                alert(DATA.msg);
            }
        },
        error : function(DATA) {
            console.log("query fail:"+DATA);
        }
    });

}

/**
 *@Description: 初始化下拉列表的配置
 */
function setSelectOption() {
    //以下为初始配置参数，用户可自行配置，同时，可配置事件参数
    $('#objSelect').multiselect({
        header: true,
        height: 175,
        minWidth: 225,
        classes: '',
        checkAllText: '选中全部',
        uncheckAllText: '取消全选',
        noneSelectedText: '请勾选',
        selectedText: '# 选中',
        selectedList: 5,
        show: null,
        hide: null,
        autoOpen: false,
        multiple: true,
        position: {},
        appendTo: "body",
        menuWidth:null
    });
    $('#objSelect').multiselect('checkAll');//默认全部选中
}



/**
 *@Description: 查询单点统计记录
 */
function queryStstList() {
    //存放所有echart视图的容器
    var $allEchartContainer = $("#allEchartContainer");
    $allEchartContainer.html("");//清空

    var objs=[];
    var attrs=[];
    //获取选中的对象
    $("#objSelect :checked").each(function(i,item){
        objs.push($(item).attr("value"));
    });

    //遍历每一个名字为attrs的复选框，其中选中的执行函数
    $('input[name="attrs"]:checked').each(function(){
        attrs.push($(this).val());
    });

    //参数
    var data = {
        objArr: objs,
        attrArr: attrs,
        dataStr:$("#selectDate").val(),
        timeStartStr:$("#selectTime1").val(),//开始时间
        timeEndtStr:$("#selectTime2").val()//结束时间
    }

    //获取单点统计记录
    $.ajax({
        url:'../singleStatistics/ListTbStat.json',//URI
        contentType:"application/json;charset=UTF-8",//设置头信息
        type:'post',
        cache:false,
        dataType:'json',
        data:JSON.stringify(data),
        success:function(DATA) {
            if(DATA.status==0){
                var statArr = DATA.data;
                for(var i=0;i<statArr.length;i++){
                    var dataArr=[];
                    var timeArr=[];
                    var objArr = statArr[i];
                    for(var j=0;j<objArr.length;j++){
                        var obj = objArr[j];
                        var date = new Date(obj.collectTimestamp);
                        var hour = date.getHours();
                        var minute = date.getMinutes();
                        if (minute >= 0 && minute <= 9) {
                            minute = "0" + minute;
                        }
                        var timeStr = hour+":"+minute;
                        timeArr.push(timeStr);//时间
                        dataArr.push(obj.metricValue);//属性值
                    }

                    //存放echart表格的容器
                    var $containerStr = "<div class='col-sm-6 tag-box tag-box-v2 box-shadow shadow-effect-1 container"+i+"' style='border-right-color: #3498db; border-right-style: solid; border-right-width: 2px; margin-bottom: 0px; background-color: #FFFFFF;'>"
                        + "<div id='echartView"+i+"' style='min-width: 300px; height: 200px; margin: 0 auto'></div>"
                        + "</div>";

                    //echart视图
                    $allEchartContainer.append($containerStr);


                    var titleStr = objArr[0].object + "|" + objArr[0].attrName;



                    //设置echart视图
                    (function (i) {
                        var echartViewId = "echartView"+i;
                        var $echartView = $("#allEchartContainer").find('#'+echartViewId)[0]
                        //console.log($echartView.attr('id'))
                        setEchartView(titleStr,timeArr,dataArr,$echartView);
                    })(i)

                }

            }else {
                alert(DATA.msg);
            }
        },
        error : function(DATA) {
            console.log("query fail:"+DATA);
        }
    });
}





/**
 *@Description: 设置echart视图
 */
function setEchartView(titleStr,timeArr,dataArr,$echartView) {
    // 基于准备好的dom，初始化echarts实例
    // var $echartView = $("#allEchartContainer").find('#'+echartViewId)
    var myChart = echarts.init($echartView);

    // 指定图表的配置项和数据
    var option = {
        backgroundColor: '#fff',//背景颜色
        title: {
            text:titleStr,//题目
            textStyle: {
                fontSize: 13,//题目文字大小
                fontWeight:'bold'//题目文字粗细
            },
            left:'center'//题目文字居中
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                lineStyle: {
                    color: '#57617B'
                }
            }
        },
        legend: {
            icon: 'rect',
            itemWidth: 14,
            itemHeight: 5,
            itemGap: 13,
            data: ['22'],
            right: '4%',
            textStyle: {
                fontSize: 12,
                color: '#F1F1F3'
            }
        },
        grid:{
            left:55,//结果图相对于容器的左距
            right:20,//结果图相对于容器的右距
            top:40,//结果图相对于容器的上距
            bottom:30//结果图相对于容器的底距
        },
        xAxis: [{
            type: 'category',
            boundaryGap: false,
            axisLine: {
                lineStyle: {
                    color: '#57617B'
                }
            },
            data: timeArr
        }],
        yAxis: [{
            type: 'value',
            name: '',//单位
            axisTick: {
                show: false
            },
            axisLine: {
                lineStyle: {
                    color: '#57617B'
                }
            },
            axisLabel: {
                margin: 10,
                textStyle: {
                    fontSize: 14
                },
                formatter: function (value, index) {//格式化Y轴
                    if(value>=10000){
                        value = value.toExponential();
                    }
                    return value+"";
                }
            },
            splitLine: {
                lineStyle: {
                    color: '#57617B'
                }
            }
        }],
        series: [{
            name: '',//鼠标经过交点时显示的name
            type: 'line',//折现
            smooth: true,
            symbol: 'circle',
            showSymbol: false,
            symbolSize:5,//实心点的大小
            itemStyle : {
                normal : {
                    color:'#7cb5ec',
                    lineStyle:{
                        color:'#FFA500'
                    }
                }
            },
            data: dataArr
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}


/**
 *@Description: 全选属性
 */
function checkAllAttr() {
    $('input[name="attrs"]').each(function(){
        this.checked = true;
        this.checked = true;
    });
}


/**
 *@Description: 取消选择属性
 */
function cancelAllCheck() {
    $('input[name="attrs"]:checked').each(function(){
        this.checked = false;
    });
}


/**
 *@Description: 默认查询
 */
function defaultQuqry() {
    var $ObjSelect = $("#objSelect");
    $ObjSelect.html("");
    $("#attrView").html("");
    var attrCheckBok = "";

    //同时请求两个ajax
    var whenResult = $.when(
        //获取对象
        $.ajax({
            url:'../singleStatistics/listTbStatObject.json',//URI
            contentType:"application/json;charset=UTF-8",//设置头信息
            type:'post',
            cache:false,
            dataType:'json',
            data:JSON.stringify(params),
            success:function(DATA) {
                return DATA;
            }
        }),
        //获取属性
        $.ajax({
            url:'../singleStatistics/listAttr.json',//URI
            contentType:"application/json;charset=UTF-8",//设置头信息
            type:'post',
            cache:false,
            dataType:'json',
            data:JSON.stringify(params),
            success:function(DATA) {
                return DATA;
            }
        }));

    //分别处理上面两个ajax的结果
    whenResult.done(function(DATA1,DATA2){
        //设置对象的下拉列表
        if(DATA1[0].status==0){
            var objArr = DATA1[0].data;
            if(objArr != null && objArr != undefined && objArr.length>0){
                var htmlStr = "";
                for(var i=0;i<objArr.length;i++){
                    var objStr = objArr[i];
                    htmlStr += "<option  value='"+objStr+"'>"+objStr+"</option>";
                }
                //初始化下拉列表
                $ObjSelect.append(htmlStr);
                setSelectOption();
            }else{
                $ObjSelect.multiselect('destroy')
            }
        }else {
            alert("query fail");
        }


        //设置属性对选矿
        if(DATA2[0].status==0){
            var attrArr = DATA2[0].data;
            for(var i=0;i<attrArr.length;i++){
                var data = attrArr[i];
                if(data.attrName=="区块高度"){
                    attrCheckBok += "<input type='checkbox' name='attrs' checked='checked' value='" + data.attr + "'/>"+ data.attrName +"&nbsp&nbsp&nbsp";
                }else {
                    attrCheckBok += "<input type='checkbox' name='attrs' value='" + data.attr + "'/>"+ data.attrName +"&nbsp&nbsp&nbsp";
                }

            }
            $("#attrView").append(attrCheckBok);

        }else {
            console.log("query fail");
        }


        //如果对象和属性加载成功，则查询折线图
        if(DATA1[0].status==0&&DATA2[0].status==0){
            queryStstList();
        }

    });

    //失败时
    whenResult.fail(function(){
        alert("系统异常");
    })
}