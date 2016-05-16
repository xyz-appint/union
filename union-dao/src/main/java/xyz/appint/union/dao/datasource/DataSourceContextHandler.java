package xyz.appint.union.dao.datasource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by justin on 16/3/21.
 */
public class DataSourceContextHandler {
    private static Logger logger = LoggerFactory.getLogger(DataSourceContextHandler.class);
    private static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<>();

    public static void setDataSourceType(DataSourceType dataSourceType) {
        logger.error("setDataSourceType===============>" + dataSourceType);
        if (dataSourceType == null) {
            throw new NullPointerException();
        }
        contextHolder.set(dataSourceType);
    }

    public static DataSourceType getDataSourceType() {
        logger.error("getDataSourceType===============>" + contextHolder.get());
        return contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }

    public static void main(String args[]) {
        DataSourceContextHandler.setDataSourceType(DataSourceType.READ);
        System.err.println(DataSourceContextHandler.getDataSourceType());
    }
}
