import { Message } from 'element-ui'
import router from '@/router'
import constant from '@/util/constant'

   export function message(data,type) {
        Message({
            showClose: true,
            message: data,
            type: type,
            duration: 2500
        })
   }
   export function goPage(name,label,data) {
       let path = {};
       path.name = name || "";
       path.query = {};
       if(label){
           path.query[label] = data
       }
       router.push(path)
   }
//   String Cutting Array
    let strSplit = function (value) {
        let Arr = value.split(" ");
    };
//Conversion time format  2018-06-06
export function timeState(times) {
    if (times != null){
        let date = new Date(times);
        let Y = date.getFullYear()+'-';
        let M = (date.getMonth()+1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
        let D = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
        return Y+M+D;
    }else{
        return times
    }
}
//Conversion time format  month 2018-08
export function MonthState(times) {
    if (times != null){
        let date = new Date(times);
        let Y = date.getFullYear()+'-';
        let M = (date.getMonth()+1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1);
        return Y+M;
    }else{
        return times
    }
}
//Conversion time format  month 8
export function MonthNumber(times) {
    if (times != null){
        let date = new Date(times);
        let M = (date.getMonth()+1);
        return M;
    }else{
        return times
    }
}

//Conversion time format 06/06
let dateState = function(times) {
    if (times != null){
        let date = new Date(times);
        let M = (date.getMonth()+1 < 10 ? (date.getMonth() + 1) : date.getMonth() + 1) + '/';
        let D = date.getDate() < 10 ? date.getDate() : date.getDate();
        return M+D;
    }else{
        return times
    }
};

let setDate = function (tt) {

    if (tt != null){
        let date = new Date(tt);
        let Y = date.getFullYear()+'-';

        let M = (date.getMonth()+1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
        let D = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
        let data = Y+M+D

        return data;
    }else{
        return tt
    }
}

//Judge the number of days per month
let monthsData = function (m,y) {
    m = parseInt(m)

    let number = 30
    if(m == 1 || m == 3 || m == 5 || m == 7 || m ==8 || m == 10 || m == 12){
        number = 31
    }else if (m == 4 || m == 6 || m == 9 || m == 11){
        number = 30
    }else if(y % 4 == 0 && m ==2){
        number =29
    }else if(y % 4 != 0 && m ==2){
        number =28
    }
    return number
};

let changeDate = function(val) {
    let result = [];
    let newDate = new Date()-14*24*3600*1000;

    for (let i = 0; i < 15; i++){
        result[i] = {}
        result[i].dateStr = newDate + 24*3600*1000*i;
        // console.log(result[i].pkDateStr)
        result[i].dateStr = dateState(result[i].dateStr);
        result[i].txn = 0;
    }
    if(val.length){
        for(let i = 0; i < result.length; i++){
            for(let j = 0; j < val.length; j++){
                if(result[i].dateStr == val[j].dateStr){
                    result[i].txn = val[j].txn
                }
            }
        }
    }
    return result
};

export function intiDate(val) {
    let data = changeDate(val);
    return data
}

//Conversion time format  00:00:00
export function timesChang(time){
    if(time){
        let date = new Date(time);
        let H = (date.getHours() < 10 ? '0' +  date.getHours() : date.getHours()) + ":";
        let M = (date.getMinutes() < 10 ? '0' +  date.getMinutes() : date.getMinutes()) + ":";
        let S = (date.getSeconds() < 10 ? '0' +  date.getSeconds() : date.getSeconds());
        return H+M+S;
    }
}

//Stitching Chart Data
//{times}  Date of the day  2019-01-01
//{now} Current month  1
//{val}  Graph Median Selection  2019-01
//{data} Data to be processed
export function chartData(times,now,val,data){
    let newVal = val.substring(5,7)
    let result = [];
    let timesDate =  parseInt(times.substring(8,10))
    let year = val.substring(0,4);

    // console.log(newVal,timesDate,year)
    if(newVal == now){
        result =changeDate(times,timesDate,data,'now')
    }else if(newVal < now){
        let num = monthsData(newVal,year);
        let newTimes = new Date(val).getTime() + num*24*3600*1000;
        let newDate = setDate(newTimes);
        result = changeDate(newTimes,num,data,'old')
    }
    return result
}


//Splicing default data
export function spliceData(time,value) {
        let dateArr = [];
        let time1 = (new Date(time[0])).getTime();
        let time2 = (new Date(time[1])).getTime();
        let dateNumber = (time2-time1) / (24*3600*1000) + 1;
        for(let i = 0; i < dateNumber; i++){
            let newTime = time1;
            newTime = newTime + 24*3600*1000*i;
            dateArr[i] = {};
            dateArr[i].dateStr = dateState(newTime);
            dateArr[i].txn = 0;
        }
        if(value) {
            for (let j = 0; j < dateArr.length; j++) {
                for (let item = 0; item < value.length; item++) {
                    if (dateArr[j].dateStr === value[item].dateStr) {
                        dateArr[j].txn = value[item].txn
                    }
                }
            }
        }
        return dateArr
}


export function checkData(val,data) {
    if(val && val.length && data && data.length){
        let item = [];
        for (let i = 0; i< data.length; i++){
            for(let a = 0; a < val.length; a++){
                if(val[a] ===  data[i].attrName){
                    item[a] = data[i].attr;
                }
            }
        }
        return item;
    }
}

export function reviseParam(necessary, query) {
    let params = arguments[0],
        querys = arguments[1],
        arr = [],
        str = '';
    for (var i in params) {
        arr.push(params[i])
    }
    str = arr.join('/');
    return { str, querys }
}


