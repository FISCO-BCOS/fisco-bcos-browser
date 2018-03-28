package cn.webank.bcos.browser.entity.req;

import java.io.Serializable;


public class ReqGetTbPendingTransactionByPkHashVO implements Serializable{
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
