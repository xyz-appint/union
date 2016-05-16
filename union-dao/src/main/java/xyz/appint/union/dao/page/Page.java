package xyz.appint.union.dao.page;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Justin
 */
public interface Page<E> extends Serializable, Iterable<E> {
    /**
     * 上一页
     *
     * @return
     */
    int getPrevPage();

    /**
     * 下一页
     *
     * @return
     */
    int getNextPage();

    /**
     * 获取总页数
     *
     * @return
     */
    int getPages();

    /**
     * 获取分页
     *
     * @return
     */
    int getPageSize();

    /**
     * 获取页码
     *
     * @return
     */
    int getPageNumber();


    /**
     * 获取结果集
     *
     * @return
     */
    List<E> getResult();

    /**
     * 设置结果集
     *
     * @param elements
     */
    void setResult(List<E> elements);

    /**
     * 获取首行
     *
     * @return
     */
    int getFirstResult();

    /**
     * 获取总纪录数
     *
     * @return
     */
    int getTotalCount();

    /**
     * 设置总纪录数
     *
     * @return
     */
    void setTotalCount(int totalCount);

    @Override
    Iterator<E> iterator();

    /**
     * 获取分页条
     * @return
     */
    //String getPageBar();
}
