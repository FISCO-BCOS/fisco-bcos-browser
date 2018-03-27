package cn.webank.bcos.browser.entity.base;

import cn.webank.bcos.browser.base.Constants;
import cn.webank.bcos.browser.base.RetCode;

import java.util.List;

public class BasePageRespEntity<T>{
    private int status;
    private String msg;
    private Integer total;
    private Integer pageTotal;
    private Integer pageSize;
    private Integer pageNumber;
    private Integer start;
    private List<T> list;

    public Integer getTotal() {
        return total;
    }

    public Integer getPageTotal() {
        if (total > 0 && total <= pageSize) {
            pageTotal = 1;
        } else {
            pageTotal = total / pageSize;
            if((total % pageSize)>0){
                pageTotal +=1;
            }
        }

        return pageTotal;
    }

    public Integer getPageSize() {
        return (pageSize==null || pageSize==0) ? Constants.DB_QUERY_DEFAULT_PAGE_SIZE : pageSize;
    }

    public Integer getPageNumber() {
        return (pageNumber==null || pageNumber==0) ? Constants.DB_QUERY_DEFAULT_PAGE_NO : pageNumber;
    }

    public Integer getStart() {
        return (getPageNumber() - 1) * getPageSize();
    }

    public List<T> getList() {
        return list;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }



    //设置返回码
    public void setRetCode(RetCode retCode){
        this.status = retCode.getCode();
        this.msg = retCode.getMsg();
    }
}
