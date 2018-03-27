package cn.webank.bcos.browser.entity.req;

import cn.webank.bcos.browser.base.ConstantCode;

import javax.validation.constraints.NotNull;


public class ReqGetTbNodeConnectionByPkIdVO {
    @NotNull(message = ConstantCode.QUERY_FAIL_PK_ID_PARAM_EMPTY)
    private Integer pkId;

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }
}
