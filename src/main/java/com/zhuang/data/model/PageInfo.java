package com.zhuang.data.model;

public class PageInfo {
	    
    private int pageIndex;
    private int rowCount;
    private String orderClause;
    private int totalRowCount;

	public PageInfo(int pageIndex, int rowCount,String orderClause) {
		this.pageIndex = pageIndex;
		this.rowCount = rowCount;
		this.orderClause = orderClause;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public String getOrderClause() {
		return orderClause;
	}

	public void setOrderClause(String orderClause) {
		this.orderClause = orderClause;
	}

	public int getTotalRowCount() {
		return totalRowCount;
	}

	public void setTotalRowCount(int totalRowCount) {
		this.totalRowCount = totalRowCount;
	}
}
