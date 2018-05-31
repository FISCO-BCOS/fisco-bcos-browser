package org.bcos.browser.entity.req;

import java.io.Serializable;

/**
 * @Description:根据pkHash查询正在处理但未上联的交易的详细信息
 * @Author: v_wbsqwu
 * @Date: 2017/11/2 19:32
 */
public class ReqGetTbPendingTransactionByPkHashEntity implements Serializable{
    private static final long serialVersionUID = 5699037013097205111L;
    private String pkHash;

    public String getPkHash() {
        return pkHash;
    }

    public void setPkHash(String pkHash) {
        this.pkHash = pkHash;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"pkHash\":\"")
                .append(pkHash).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
