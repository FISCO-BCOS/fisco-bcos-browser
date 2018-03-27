package cn.webank.bcos.browser.dto;

import java.sql.Timestamp;
import java.util.Comparator;

/**
 * @Description:Single point statistics table, entity class
 * @Author: v_wbsqwu
 * @Date: 2017/11/22 10:45
 */
public class TbStatDto implements Comparable<TbStatDto>{
    private Integer pkId;
    private String object;
    private String attr;
    private String attrName;
    private Timestamp collectTimestamp;
    private Double metricValue;
    private String hostIp;
    private String detailInfo;

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public Timestamp getCollectTimestamp() {
        return collectTimestamp;
    }

    public void setCollectTimestamp(Timestamp collectTimestamp) {
        this.collectTimestamp = collectTimestamp;
    }

    public Double getMetricValue() {
        return metricValue;
    }

    public void setMetricValue(Double metricValue) {
        this.metricValue = metricValue;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"pkId\":")
                .append(pkId);
        sb.append(",\"object\":\"")
                .append(object).append('\"');
        sb.append(",\"attr\":\"")
                .append(attr).append('\"');
        sb.append(",\"attrName\":\"")
                .append(attrName).append('\"');
        sb.append(",\"collectTimestamp\":\"")
                .append(collectTimestamp).append('\"');
        sb.append(",\"metricValue\":")
                .append(metricValue);
        sb.append(",\"hostIp\":\"")
                .append(hostIp).append('\"');
        sb.append(",\"detailInfo\":\"")
                .append(detailInfo).append('\"');
        sb.append('}');
        return sb.toString();
    }

    /**
     *@Description: 对象比较（按照collectTimestamp从小到达排序）
     */
    @Override
    public int compareTo(TbStatDto obj) {
        if(obj==null){
            return 1;
        }
        if(this.getCollectTimestamp().after(obj.getCollectTimestamp())){
            return 1;
        }
        if(this.getCollectTimestamp().before(obj.getCollectTimestamp())){
            return -1;
        }
        return 0;
    }
}
