package cn.webank.bcos.browser.base;

/**
 * @Description: Table prefix
 * @Author: v_wbsqwu
 * @Date: 2017/11/22 21:53
 */
public enum TablePrefixEnum {
    TB_STAT_TRANSACTION("tb_stat_transaction_"),//"tb_stat_transaction_" prefix of the series table
    TB_SINGLE_STAT("tb_single_stat_"),//"tb_single_stat_" prefix of the series table
    TB_STAT_BLOCK("tb_stat_block_");//"tb_stat_block_" prefix of the series table


    private String value;
    TablePrefixEnum(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
