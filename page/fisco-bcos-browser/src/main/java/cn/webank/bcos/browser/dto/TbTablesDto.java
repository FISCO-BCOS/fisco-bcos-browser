package cn.webank.bcos.browser.dto;

/**
 * @Description:Database table, entity
 * @Author: v_wbsqwu
 * @Date: 2017/12/8 17:38
 */
public class TbTablesDto {
    private String tableName;
    private String tableSchema;
    private String tableComment;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }
}
