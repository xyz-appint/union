package xyz.appint.union.dao.page;

import java.io.Serializable;

/**
 * Created by Justin
 */
public class CursorPageRequest implements Serializable {
    /**
     * 每页纪录数
     */
    private int limit = 15;

    /**
     * 每页纪录数
     * @param limit
     */
    public CursorPageRequest(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
