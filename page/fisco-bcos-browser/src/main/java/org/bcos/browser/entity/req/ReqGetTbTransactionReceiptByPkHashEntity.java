package org.bcos.browser.entity.req;

import org.bcos.browser.base.ConstantCode;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @Description:根据pkHash查询交易回执信息，页面请求参数实体
 * @Author: v_wbsqwu
 * @Date: 2017/11/2 15:49
 */
public class ReqGetTbTransactionReceiptByPkHashEntity implements Serializable{
    private static final long serialVersionUID = -8153557934594957988L;
    @NotEmpty(message = ConstantCode.QUERY_FAIL_PK_HASH_PARAM_EMPTY)
    private String PkHash;//交易信息hash

    public String getPkHash() {
        return PkHash;
    }

    public void setPkHash(String pkHash) {
        PkHash = pkHash;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"PkHash\":\"")
                .append(PkHash).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
