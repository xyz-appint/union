package xyz.appint.union.dao.dao;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class PageCountHelper {
    private static Logger logger = LoggerFactory.getLogger(PageCountHelper.class);
    public static final String ORDER_BY_REGEX = "order\\s*by[\\w|\\W|\\s|\\S]*";

    private static final Cache<Integer, String> COUNT_CACHE = CacheBuilder.newBuilder().maximumSize(500).expireAfterWrite(30, TimeUnit.MINUTES).build();

    public static String removeOrders(String sql) {
        Preconditions.checkNotNull(sql);
        Pattern p = Pattern.compile(ORDER_BY_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(sql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public static void isSupportedSql(String sql) {
        if (sql.trim().toUpperCase().endsWith("FOR UPDATE")) {
            throw new RuntimeException("分页插件不支持包含for update的sql");
        }
    }

    public static String getSimpleCountSql(final String sql) {
        isSupportedSql(sql);
        StringBuilder stringBuilder = new StringBuilder(sql.length() + 40);
        stringBuilder.append("SELECT COUNT(*) FROM (");
        stringBuilder.append(sql);
        stringBuilder.append(") tmp_count");
        return stringBuilder.toString();
    }

    private static String getCountSql(String sql) throws ExecutionException {
        //校验是否支持该sql
        isSupportedSql(sql);
        final String newSql = removeOrders(sql);
        return COUNT_CACHE.get(sql.hashCode(), new Callable<String>() {
            @Override
            public String call() throws Exception {
                return getSimpleCountSql(newSql);
            }
        });

    }


    public static int getCount(final MappedStatement ms, final Connection connection, final Object parameterObject) throws SQLException {
        BoundSql boundSql = ms.getBoundSql(parameterObject);
        String countSql;
        try {
            countSql = getCountSql(boundSql.getSql());
        } catch (ExecutionException e) {
            throw new SQLException("get count sql", e);
        }

        logger.debug("Total count SQL [{}]", countSql);
        logger.debug("Parameters: {} ", parameterObject);

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement(countSql);
            DefaultParameterHandler handler = new DefaultParameterHandler(ms, parameterObject, boundSql);
            handler.setParameters(stmt);
            rs = stmt.executeQuery();

            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error("close rs", e);
                }
            }

            closeStatement(stmt);
//            if(connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    logger.error("close connection", e);
//                }
//            }
        }
    }

    private static void closeStatement(java.sql.Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error("closeStatement", e);
            }
        }
    }
}