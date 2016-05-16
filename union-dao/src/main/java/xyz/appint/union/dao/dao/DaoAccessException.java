package xyz.appint.union.dao.dao;

/**
 * 数据层异常
 * unchecked
 */
public class DaoAccessException extends RuntimeException {

    public DaoAccessException(String msg) {
        super(msg);
    }

    public DaoAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public DaoAccessException(Throwable cause) {
        super(cause);
    }

}
