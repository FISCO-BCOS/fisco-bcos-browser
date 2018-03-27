package cn.webank.bcos.browser.entity.req;

import cn.webank.bcos.browser.entity.base.BasePageReqEntity;


public class ReqGetTbBlockInfoByPageVO extends BasePageReqEntity {
    private String hash;//hash
    private String dateTime1;
    private String dateTime2;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getDateTime1() {
        return dateTime1;
    }

    public void setDateTime1(String dateTime1) {
        this.dateTime1 = dateTime1;
    }

    public String getDateTime2() {
        return dateTime2;
    }

    public void setDateTime2(String dateTime2) {
        this.dateTime2 = dateTime2;
    }
}
