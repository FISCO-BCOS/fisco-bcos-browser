package org.bcos.browser.entity.base;


import org.bcos.browser.base.RetCode;

/**
 * 返回类的基类
 */
public class BaseRspEntity {
    private int status;//错误码
    private String msg;//错误信息
    private Object data;

    public BaseRspEntity(){}
    public BaseRspEntity(int status){
        this.status = status;
    }
    public BaseRspEntity(RetCode rsc){
        this.status = rsc.getCode();
        this.msg = rsc.getMsg();
    }
    public BaseRspEntity(RetCode rsc, Object obj){
        this.status = rsc.getCode();
        this.msg = rsc.getMsg();
        this.data = obj;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
