package xyz.appint.union.utils;

import java.util.List;


/**
 * @author Administrator
 *         <p/>
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PageUtils<T> {
    private int prevPage = 1;
    private int nextPage = 1;
    private int pageCount = 1;
    private int pageNum = 1;
    private int recordCount = 1;
    private int pageSize = 20;
    private List<T> list;
    private int start;
    private String pageControl;
    private String filters;
    private String orderField;
    private String sord;
    private String searchField;
    private String searchString;
    private String searchOper;
    private String order;

    public PageUtils() {

    }

    /**
     * @param li:列表
     * @param count:记录集总数
     */
    public PageUtils(List<T> li, int count, int curPage, int start, int size) {
        this.recordCount = count;
        this.pageNum = curPage;
        if (curPage < 1) {
            this.pageNum = 1;
        }
        this.pageSize = size;
        adjust();
        this.list = li;
        this.start = start;
    }


    /**
     * @param li:列表
     * @param count:记录集总数
     */
    public void init(List<T> li, int count, int curPage, int start, int size) {
        this.recordCount = count;
        this.pageNum = curPage;
        if (curPage < 1) {
            this.pageNum = 1;
        }
        this.pageSize = size;
        adjust();
        this.list = li;
        this.start = start;
    }

    /**
     * 获得当前页开始行数
     *
     * @param recordNum
     * @param curPage
     * @param iPageSize
     * @return
     */
    public static int getStart(int recordNum, int curPage, int iPageSize) {
        if (curPage == 0) {
            curPage = 1;
        }
        if (recordNum == 0) return 0;
        int pageTotal = (recordNum + iPageSize - 1) / iPageSize;
        if (curPage > pageTotal)
            curPage = pageTotal;
        int s = (curPage - 1) * iPageSize;
        if (s < 0) {
            s = 0;
        } else if (s > recordNum) {
            s = recordNum;
        }
        if (s == 1 && curPage == 1) {
            s = 0;
        }
        return s;
    }


    private void adjust() {
        pageCount = (int) ((float) recordCount / pageSize + 0.99);
        pageNum = (pageNum > 0 ? pageNum : 1);
        pageNum = (pageNum > pageCount ? pageCount : pageNum);
        nextPage = (pageNum < pageCount ? (pageNum + 1) : pageCount);
        prevPage = (pageNum > 1 ? (pageNum - 1) : 1);
    }


    public String getPageControl() {
        return pageControl;
    }

    public int getNextPage() {
        return nextPage;
    }

    public int getFirstNo() {
        return (pageNum - 1) * pageSize + 1;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setPageSize(int i) {
        pageSize = i;
    }

    public List<T> getList() {
        return list;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setPrevPage(int prevPage) {
        this.prevPage = prevPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void setPageControl(String pageControl) {
        this.pageControl = pageControl;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public String getCondition() {
        return getCondition("");
    }

    public String getCondition(String prefix) {
        String str = "";
        SearchFilters sf = new SearchFilters();
        if (filters != null && filters.equals("") == false) {
            str = sf.getWhere(filters, prefix);
        }
        if (searchField != null && searchField.equals("") == false) {
            str = sf.getWhere(searchField, searchString, searchOper, prefix);
        }
        return str;
    }

    public String getOrder(String prefix) {

        if (this.orderField != null && this.sord != null
                && this.orderField.equals("") == false && this.sord.equals("") == false) {
            order = "ORDER BY " + orderField;
            order = order.trim();
            if (order.lastIndexOf(",") == order.length() - 1) {
                order = order.substring(0, order.length() - 1);
            }
            if (order.endsWith("asc") == false && order.endsWith("desc") == false) {
                order += " " + sord;
            }
        }
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrder() {
        return getOrder("");
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchOper() {
        return searchOper;
    }

    public void setSearchOper(String searchOper) {
        this.searchOper = searchOper;
    }


//	public int getStart() {
//		return start;
//	}

}
