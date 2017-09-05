package com.lol.common.pojos;

import java.util.List;

/**
 *分页信息
 * @author yangli
 *
 */
public class Page {
    private int pageNum = 1; // 当前页数
    private int numPerPage = 0; // 每页显示的条数
    private int totalCount = 0; // 总共条数
    
    private String orderField; // 排序字段
    private String orderDirection; // 排序规则，desc or asc
    
    private List<?> list = null; // 返回记录列表
    
    public static final int PAGE_SHOW_COUNT = 9999;
    
    /**
     * 默认构造函数
     */
    public Page(){
        
    }
    
    /**
     * 构造函数
     * @param pageNum 当前页数
     * @param numPerPage 每页显示的条数
     * @param totalCount 总共条数
     * @param list 记录列表
     */
    public Page(int pageNum, int numPerPage, int totalCount, List<?> list){
        this.pageNum = pageNum;
        this.numPerPage = numPerPage;
        this.totalCount = totalCount;
        this.list = list;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getNumPerPage() {
        if(numPerPage > 0)
            return numPerPage;
        else{
            numPerPage = PAGE_SHOW_COUNT;
            return numPerPage;
        }
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

}
