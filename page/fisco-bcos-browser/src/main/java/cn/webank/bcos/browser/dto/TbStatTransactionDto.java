package cn.webank.bcos.browser.dto;

import java.sql.Timestamp;

/**
 * @Description:Trading process statistics information table, entity class
 * @Author: v_wbsqwu
 * @Date: 2017/11/22 10:52
 */
public class TbStatTransactionDto {
    private Integer pkId;
    private String hash;
    private String startMsg;
    private String startTime;
    private String onChainMsg;
    private String onChainTime;
    private String detailInfo;
    private String object;
    private Timestamp collectTimestamp;

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getStartMsg() {
        return startMsg;
    }

    public void setStartMsg(String startMsg) {
        this.startMsg = startMsg;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getOnChainMsg() {
        return onChainMsg;
    }

    public void setOnChainMsg(String onChainMsg) {
        this.onChainMsg = onChainMsg;
    }

    public String getOnChainTime() {
        return onChainTime;
    }

    public void setOnChainTime(String onChainTime) {
        this.onChainTime = onChainTime;
    }

    public String getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Timestamp getCollectTimestamp() {
        return collectTimestamp;
    }

    public void setCollectTimestamp(Timestamp collectTimestamp) {
        this.collectTimestamp = collectTimestamp;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"pkId\":")
                .append(pkId);
        sb.append(",\"hash\":\"")
                .append(hash).append('\"');
        sb.append(",\"startMsg\":\"")
                .append(startMsg).append('\"');
        sb.append(",\"startTime\":\"")
                .append(startTime).append('\"');
        sb.append(",\"onChainMsg\":\"")
                .append(onChainMsg).append('\"');
        sb.append(",\"onChainTime\":\"")
                .append(onChainTime).append('\"');
        sb.append(",\"detailInfo\":\"")
                .append(detailInfo).append('\"');
        sb.append(",\"object\":\"")
                .append(object).append('\"');
        sb.append(",\"collectTimestamp\":\"")
                .append(collectTimestamp).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
