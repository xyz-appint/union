package xyz.appint.union.dao.page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Justin
 */
public class PageRequest implements Serializable {
    /**
     * 查询条件
     */
    private Map<String, Object> filters = new HashMap<String, Object>();
    /**
     * like 查询条件
     */
    private Map<String, String> likeFilters = new HashMap<String, String>();

    /**
     * 当前页码
     */
    private int pageNumber = 1;

    /**
     * 每页纪录数
     */
    private int pageSize = 15;

    /**
     * 排序列
     */
    private String sortColumns;


    public PageRequest() {
        this(1, 15);
    }

    /**
     * 分页请求
     *
     * @param pageNumber 当前页码
     * @param pageSize   每页纪录数
     */
    public PageRequest(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    /**
     * 每页纪录数
     * @param pageSize
     */
    public PageRequest(int pageSize) {
        this(1 , pageSize);
    }


    /**
     * 当前页码
     *
     * @return
     */
    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }


    /**
     * 每页大小
     *
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 排序列
     *
     * @return
     */
    public String getSortColumns() {
        return sortColumns;
    }

    /**
     * 排序 例：username desc,age asc
     *
     * @param sortColumns
     */
    public void setSortColumns(String sortColumns) {
        this.sortColumns = sortColumns;
    }

    public Map<String, Object> getFilters() {
        return filters;
    }

    public Map<String, String> getLikeFilters() {
        return likeFilters;
    }
}
