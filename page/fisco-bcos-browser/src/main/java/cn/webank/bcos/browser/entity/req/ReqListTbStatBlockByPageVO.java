package cn.webank.bcos.browser.entity.req;

import cn.webank.bcos.browser.base.ConstantCode;
import cn.webank.bcos.browser.entity.base.BasePageReqEntity;
import org.hibernate.validator.constraints.NotBlank;


public class ReqListTbStatBlockByPageVO  extends BasePageReqEntity {
    private String hash;
    @NotBlank(message = ConstantCode.QUERY_FAIL_DATESTR_PARAM_EMPTY)
    private String dateStr;
    private String timeStartStr;
    private String timeEndStr;


    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getTimeStartStr() {
        return timeStartStr;
    }

    public void setTimeStartStr(String timeStartStr) {
        this.timeStartStr = timeStartStr;
    }

    public String getTimeEndStr() {
        return timeEndStr;
    }

    public void setTimeEndStr(String timeEndStr) {
        this.timeEndStr = timeEndStr;
    }
}
