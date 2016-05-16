package xyz.appint.union.dao.page;


import xyz.appint.union.utils.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Justin
 */
public class DefaultPage<E> implements Page<E> {
    /**
     * 结果集
     */
    private List<E> result;

    /**
     * 每页纪录数
     */
    private int pageSize;
    /**
     * 当前页码
     */
    private int pageNumber;
    /**
     * 总纪录数
     */
    private int totalCount = 0;

    /**
     * 总页数
     */
    private int pages;

    /**
     * 上一页 页码
     */
    private int prevPage;

    /**
     * 下一页 页码
     */
    private int nextPage;

    /**
     * 纪录首行
     */
    private int firstResult;

    /**
     * 上一页
     *
     * @return
     */
    public int getPrevPage() {
        return prevPage;
    }

    /**
     * 下一页
     *
     * @return
     */
    public int getNextPage() {
        return nextPage;
    }

    /**
     * 分页请求
     *
     * @param pageRequest
     * @param totalCount
     */
    public DefaultPage(PageRequest pageRequest, int totalCount) {
        this(pageRequest.getPageNumber(), pageRequest.getPageSize(), totalCount);
    }

    /**
     * 构造函数
     *
     * @param pageNumber
     * @param pageSize
     */
    public DefaultPage(int pageNumber, int pageSize) {
        this(pageNumber, pageSize, 0, new ArrayList<E>(0));
    }

    /**
     * 构造函数
     *
     * @param pageNumber
     * @param pageSize
     * @param totalCount
     */
    public DefaultPage(int pageNumber, int pageSize, int totalCount) {
        this(pageNumber, pageSize, totalCount, new ArrayList<E>(0));
    }

    /**
     * 构造函数
     *
     * @param pageNumber
     * @param pageSize
     * @param totalCount
     * @param result
     */
    public DefaultPage(int pageNumber, int pageSize, int totalCount, List<E> result) {
        Assert.isTrue(pageSize > 0, "[pageSize] must great than zero");
        //if (pageSize <= 0) throw new IllegalArgumentException("[pageSize] must great than zero");
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.totalCount = totalCount;
        setResult(result);

        execute();
    }

    /**
     * 计算分页值
     */
    private void execute() {
//        // 计算总的页数，如果刚好是整页的情况，如allRows=10,20,30
//        this.pages = totalCount / pageSize;
//        // 如果不足一页要看成一页 ,如allRows=11,22,34 看成2 3 4页
//        if (pages % pageSize != 0)
//            this.pages++;


        pages = (int) ((float) totalCount / pageSize + 0.99);

        // 处理当前页不在范围的情况
//        this.pageNumber = pageNumber < 1 ? 1 : pageNumber;
//        this.pageNumber = pageNumber > pages ? pages : pageNumber;


        // 没有下一页时 页号显示为0
        nextPage = (pageNumber >= pages ? 0 : pageNumber + 1);
        // 没有上一页时 页号显示为0
        prevPage = (pageNumber <= 1 ? 0 : pageNumber - 1);

    }


    /**
     * 获取总页数
     *
     * @return
     */
    @Override
    public int getPages() {
//        // 计算总的页数，如果刚好是整页的情况，如allRows=10,20,30
//        this.pages = totalCount / pageSize;
//        // 如果不足一页要看成一页 ,如allRows=11,22,34 看成2 3 4页
//        if (pages % pageSize != 0)
//            this.pages++;

        pages = (int) ((float) totalCount / pageSize + 0.99);


        return pages;
    }


    /**
     * 获取分页
     *
     * @return
     */
    @Override
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 获取结果集
     *
     * @return
     */
    @Override
    public List<E> getResult() {
        return result;
    }

    /**
     * 设置结果集
     *
     * @param elements
     */
    @Override
    public void setResult(List<E> elements) {
        //Assert.notNull(elements, "'result' must be not null");
        this.result = elements;
        if(this.totalCount == 0) {
            setTotalCount(result.size());
        }
    }

    /**
     * 获取首行
     *
     * @return
     */
    @Override
    public int getFirstResult() {
        // 提取得数据库的开始行 数据库的行号从0开始
        firstResult = (pageNumber - 1) * pageSize;
        if (firstResult < 0) {
            firstResult = 0;
        }
        return firstResult;
    }

    /**
     * 获取总纪录数
     *
     * @return
     */
    @Override
    public int getTotalCount() {
        return totalCount;
    }


    @Override
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        execute();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterator<E> iterator() {
        return (Iterator<E>) (result == null ? Collections.emptyList().iterator() : result.iterator());
    }


    public int getPageNumber() {
        return pageNumber;
    }

}
