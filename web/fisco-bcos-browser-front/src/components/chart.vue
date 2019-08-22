<template>
    <div :id="chartId" style="min-width: 255px; height: 230px; margin: 0 auto;color: #fff!important;"></div>
</template>
<script>
    let echarts = require('echarts/lib/echarts');
    require('echarts/lib/chart/line');
    require('echarts/lib/component/title');
    require('echarts/lib/component/tooltip');
    require("echarts/lib/component/grid");
    require("echarts/lib/component/legend");
    require("echarts/lib/component/dataZoom");

    export default {
        name: 'charts',
        props: ['type','id','data','transactionDataArr','size','title','titleData','name'],
        data: function () {
            return {
                chartType: this.type,
                chartId: this.id,
                chartData: this.data,
                chartTransactionDataArr: this.transactionDataArr,
                chartSize: this.size,
                titleType: this.title,
                titleContent: this.titleData,
                titleName: this.name,
                chart: null,
            }
        },
        watch: {
            'data': function () {
                this.chartType = this.type;
                this.chartId =  this.id;
                this.chartData = this.data;
                this.chartTransactionDataArr = this.transactionDataArr;
                this.chartSize= this.size;
                this.titleType= this.title;
                this.titleContent = this.titleData;
                this.titleName = this.name,
                this.chartShow();
            }
        },
        mounted: function () {
            this.$nextTick(function () {
                this.chartShow()
            })
        },
        beforeDestroy: function () {
            if(this.chart){
                this.chart.dispose()
            }
        },
        methods: {
            chartShow: function () {
               this.chart = echarts.init(document.getElementById(this.chartId));
                let dayNum = this.chartData.length;
                let months = 0

                let option = {
                    title: {
                        text: '最近15天的交易量',
                        textStyle: {
                            fontSize: 13,
                            fontWeight:'bold',
                            color: '#fff'
                        },
                        left:'center'
                    },
                    legend: {
                        height: this.chartSize.height,
                        width: this.chartSize.width
                    },
                    tooltip:{
                        show:true,
                        trigger: 'axis',
                        formatter: function(data){
                            return '<span style="font-size:10px">' + data[0].name + '</span><br><table ><tr><td style="padding:0">' +
                                '<span style="font-size:10px;color:white">交易量：' + data[0].value + '</a></span><br></td></tr></table>';
                        }
                    },
                    grid:{
                        left:65,
                        right:0,
                        top:30,
                        bottom:30
                    },
                    series: [{
                        //  name: '销量',
                        type: this.chartType,
                        symbolSize:10,
                        itemStyle : {
                            normal : {
                                color:'#fff',
                                lineStyle:{
                                    color:'#da99db'
                                }
                            }
                        },
                        data: this.chartTransactionDataArr
                    }],
                    xAxis: {
                        data: this.chartData,
                        axisLine: {
                            lineStyle: {
                                color: '#fff',//left line color
                            }
                        },
                        axisLabel: {
                            interval: 3,
                            textStyle: {
                                color: '#fff',

                            }
                        }
                    },
                    yAxis: {
                        axisLine: {
                            show: false, 
                            lineStyle: {
                                color: '#fff',
                            }
                        },
                        splitLine: {show: true},
                        axisTick: {show: false},
                        axisLabel: {
                            formatter: function (value, index) {
                                if(value > 1000 && value < 1000000){
                                    return value/1000 + "K"
                                }else if(value >= 1000000){
                                    return value/1000000 + 'M'
                                }else {
                                    return value
                                }
                            },
                            textStyle: {
                                color: '#fff',

                            }
                        }
                    }
                };
                let self = this;
                if(this.titleType === 'home'){
                    // option.title.text =  '最近有交易的'+ dayNum+' 天交易量';
                    option.xAxis.axisLabel.interval = 3;
                }else if(this.titleType === 'single'){
                    option.title.text =  this.titleContent;
                    option.xAxis.axisLabel.interval = 'auto';
                    option.tooltip.formatter = function (data) {
                        return '<span style="font-size:10px">' + data[0].name + '</span><br><table ><tr><td style="padding:0">' +
                            '<span style="font-size:10px;color:white">'+self.titleName+'：' + data[0].value + '</a></span><br></td></tr></table>';
                    }
                }
                this.chart.clear();
                this.chart.setOption(option,true);
                window.onresize = () => {
                    this.chart.resize();
                };
            }
        }
    }
</script>
