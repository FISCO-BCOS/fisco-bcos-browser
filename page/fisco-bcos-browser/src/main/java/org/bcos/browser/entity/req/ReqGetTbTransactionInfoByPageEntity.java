package org.bcos.browser.entity.req;

import org.bcos.browser.entity.base.BasePageReqEntity;

import java.io.Serializable;

/**
 * @Description:分页查询交易信息表,页面请求入参
 * @Author: v_wbsqwu
 * @Date: 2017/10/19 15:03
 */
public class ReqGetTbTransactionInfoByPageEntity extends BasePageReqEntity implements Serializable{
    private static final long serialVersionUID = 303139353670046800L;
    private Integer blockHeight;//块高

    public Integer getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(Integer blockHeight) {
        this.blockHeight = blockHeight;
    }

}
