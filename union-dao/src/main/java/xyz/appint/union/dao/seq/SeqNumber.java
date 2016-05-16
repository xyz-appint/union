package xyz.appint.union.dao.seq;

import java.io.Serializable;

/**
 * Created by justin on 16/4/19.
 */
public class SeqNumber implements Serializable {
    private String tableKey;
    private int seqNumber;

    public String getTableKey() {
        return tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public int getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(int seqNumber) {
        this.seqNumber = seqNumber;
    }
}
