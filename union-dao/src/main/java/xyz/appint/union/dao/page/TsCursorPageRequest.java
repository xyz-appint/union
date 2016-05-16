package xyz.appint.union.dao.page;

import java.io.Serializable;

/**
 * Created by Justin
 */
public class TsCursorPageRequest extends CursorPageRequest implements Serializable {
    /**
     * 时间戳
     */
    private long ts;

    /**
     * 每页纪录数
     *
     * @param limit
     */
    public TsCursorPageRequest(int limit) {
        super(limit);
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }
}
