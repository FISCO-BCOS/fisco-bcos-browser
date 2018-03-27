package cn.webank.bcos.browser.entity.req;

import cn.webank.bcos.browser.base.ConstantCode;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;


public class ReqGetTbTransactionReceiptByPkHashVO implements Serializable{
    private static final long serialVersionUID = -8153557934594957988L;
    @NotEmpty(message = ConstantCode.QUERY_FAIL_PK_HASH_PARAM_EMPTY)
    private String PkHash;

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
