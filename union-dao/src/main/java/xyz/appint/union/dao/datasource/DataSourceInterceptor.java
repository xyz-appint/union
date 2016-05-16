package xyz.appint.union.dao.datasource;

import org.springframework.core.Ordered;

/**
 * Created by justin on 16/3/21.
 */
public class DataSourceInterceptor implements Ordered {
    public void setReadDataSource() {
        DataSourceContextHandler.setDataSourceType(DataSourceType.READ);
    }

    public void setWriteDataSource() {
        DataSourceContextHandler.setDataSourceType(DataSourceType.WRITE);
    }

    @Override
    public int getOrder() {
        return 20;
    }
}
