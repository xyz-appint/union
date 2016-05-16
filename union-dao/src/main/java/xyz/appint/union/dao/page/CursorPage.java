package xyz.appint.union.dao.page;

import java.io.Serializable;
import java.util.Collection;

/**
 * 游标分页
 * Created by justin on 16/3/18.
 */
public abstract class CursorPage<T> implements Serializable {
    private int total;//总纪录数
    private int limit;//每页纪录数

//    private Object nextCursorVal;

    private Collection<T> items;

    public CursorPage(int limit) {
        this.limit = limit;
    }

//    public Object getNextCursorVal() {
//        return nextCursorVal;
//    }
//
//    public void setNextCursorVal(Object nextCursorVal) {
//        this.nextCursorVal = nextCursorVal;
//    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }


    public Collection<T> getItems() {
        return items;
    }

    public void setItems(Collection<T> items) {
        this.items = items;
    }
}
