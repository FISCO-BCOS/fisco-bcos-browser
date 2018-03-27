package cn.webank.bcos.browser.entity.req;

import cn.webank.bcos.browser.base.ConstantCode;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Arrays;


public class ReqListTbStatByObjectAttrVO {
    @NotBlank(message = ConstantCode.QUERY_FAIL_DATESTR_PARAM_EMPTY)
    private String dataStr;
    private String timeStartStr;
    private String timeEndtStr;
    @NotNull(message = ConstantCode.QUERY_FAIL_OBJECT_ARRAY_PARAM_EMPTY)
    private String[] objArr;
    @NotNull(message = ConstantCode.QUERY_FAIL_ATTR_ARRAY_PARAM_EMPTY)
    private String[] attrArr;

    public String getDataStr() {
        return dataStr;
    }

    public void setDataStr(String dataStr) {
        this.dataStr = dataStr;
    }

    public String getTimeStartStr() {
        return timeStartStr;
    }

    public void setTimeStartStr(String timeStartStr) {
        this.timeStartStr = timeStartStr;
    }

    public String getTimeEndtStr() {
        return timeEndtStr;
    }

    public void setTimeEndtStr(String timeEndtStr) {
        this.timeEndtStr = timeEndtStr;
    }

    public String[] getObjArr() {
        return objArr;
    }

    public void setObjArr(String[] objArr) {
        this.objArr = objArr;
    }

    public String[] getAttrArr() {
        return attrArr;
    }

    public void setAttrArr(String[] attrArr) {
        this.attrArr = attrArr;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"dataStr\":\"")
                .append(dataStr).append('\"');
        sb.append(",\"timeStartStr\":\"")
                .append(timeStartStr).append('\"');
        sb.append(",\"timeEndtStr\":\"")
                .append(timeEndtStr).append('\"');
        sb.append(",\"objArr\":")
                .append(Arrays.toString(objArr));
        sb.append(",\"attrArr\":")
                .append(Arrays.toString(attrArr));
        sb.append('}');
        return sb.toString();
    }
}
