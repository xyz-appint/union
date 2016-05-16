package xyz.appint.union.dao.datasource;

//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by justin on 16/3/21.
 */
public class RoutingDataSource  {
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHandler.getDataSourceType();
    }

}
