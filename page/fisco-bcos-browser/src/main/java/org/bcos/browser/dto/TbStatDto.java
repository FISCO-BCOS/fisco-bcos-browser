/*
 * This file is part of FISCO BCOS Browser.
 *
 * FISCO BCOS Browser is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FISCO BCOS Browser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FISCO BCOS Browser.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * @file: TbStatDto.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.dto;

import java.sql.Timestamp;

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
