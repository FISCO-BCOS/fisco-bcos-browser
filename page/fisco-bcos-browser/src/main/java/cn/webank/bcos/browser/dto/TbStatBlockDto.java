package cn.webank.bcos.browser.dto;

import java.sql.Timestamp;

/**
 * @Description:Block process statistics information table, entity class
 * @Author: v_wbsqwu
 * @Date: 2017/11/22 10:55
 */
public class TbStatBlockDto {
    private Integer pkId;
    private Integer height;
    private String hash;
    private String start;
    private String sealed;
    private String execed;
    private String signed;
    private String commited;
    private String onChain;
    private String viewChangeStart;
    private String viewChanged;
    private String detailInfo;
    private String object;
    private Timestamp collectTimestamp;


    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getSealed() {
        return sealed;
    }

    public void setSealed(String sealed) {
        this.sealed = sealed;
    }

    public String getExeced() {
        return execed;
    }

    public void setExeced(String execed) {
        this.execed = execed;
    }

    public String getSigned() {
        return signed;
    }

    public void setSigned(String signed) {
        this.signed = signed;
    }

    public String getCommited() {
        return commited;
    }

    public void setCommited(String commited) {
        this.commited = commited;
    }

    public String getOnChain() {
        return onChain;
    }

    public void setOnChain(String onChain) {
        this.onChain = onChain;
    }

    public String getViewChangeStart() {
        return viewChangeStart;
    }

    public void setViewChangeStart(String viewChangeStart) {
        this.viewChangeStart = viewChangeStart;
    }

    public String getViewChanged() {
        return viewChanged;
    }

    public void setViewChanged(String viewChanged) {
        this.viewChanged = viewChanged;
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
        sb.append(",\"height\":")
                .append(height);
        sb.append(",\"hash\":\"")
                .append(hash).append('\"');
        sb.append(",\"start\":\"")
                .append(start).append('\"');
        sb.append(",\"sealed\":\"")
                .append(sealed).append('\"');
        sb.append(",\"execed\":\"")
                .append(execed).append('\"');
        sb.append(",\"signed\":\"")
                .append(signed).append('\"');
        sb.append(",\"commited\":\"")
                .append(commited).append('\"');
        sb.append(",\"onChain\":\"")
                .append(onChain).append('\"');
        sb.append(",\"viewChangeStart\":\"")
                .append(viewChangeStart).append('\"');
        sb.append(",\"viewChanged\":\"")
                .append(viewChanged).append('\"');
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
