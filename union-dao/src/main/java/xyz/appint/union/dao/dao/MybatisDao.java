package xyz.appint.union.dao.dao;

import org.apache.commons.lang.ArrayUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.appint.union.dao.page.*;
import xyz.appint.union.utils.Assert;
import xyz.appint.union.utils.ConvertUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Justin on 2014/8/11.
 */
public class MybatisDao<T extends Serializable> implements BaseDao<T> {
    static final Logger logger = LoggerFactory.getLogger(MybatisDao.class);
    @Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate writerSession;

//    private SqlSessionTemplate writerSession;

//        @Resource(name = "writerSqlSessionFactory")
//    private SqlSessionFactory sqlSessionFactory;

//    @Resource(name = "myBatisPagingItemReader")
//    private MyBatisPagingItemReader myBatisPagingItemReader;

    private DataSource writerDataSource;

    public SqlSessionTemplate getReaderSession() {
        return writerSession;
    }


    public SqlSessionTemplate getWriterSession() {
        return getReaderSession();
    }

    public void setWriterSession(SqlSessionTemplate writerSession) {
        this.writerSession = writerSession;
    }


    public DataSource getWriterDataSource() {
        return writerDataSource;
    }

    public void setWriterDataSource(DataSource writerDataSource) {
        Assert.notNull(writerDataSource);
        this.writerDataSource = writerDataSource;
    }

    private HashMap<String, Object> paramsToMap(Object[] args) {
        HashMap<String, Object> m = new HashMap<>();
        int i = 0;
        for (Object val : args) {
            m.put("" + i, val);
            i++;
        }
        return m;
    }

    private Object assemblyParams(Object... params) {
        Object parameter = new Object();
        if (params != null && params.length > 0) {
            if (params.length > 1) {
                parameter = paramsToMap(params);
            } else {
                parameter = params[0];
            }
        }
        return parameter;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Integer queryInt(String statement, Object... params) {
        Object obj = getReaderSession().selectOne(statement, assemblyParams(params));
        if (obj == null) {
            return 0;
        }
        return (Integer) obj;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Long queryLong(String statement, Object... params) {
        Object obj = getReaderSession().selectOne(statement, assemblyParams(params));
        return obj == null ? 0 : ConvertUtils.toLong(obj);
    }

    @Override
    public String queryString(String statement, Object... params) {
        return getReaderSession().selectOne(statement, assemblyParams(params));
    }

    @Override
    public <O> O queryOne(String statement, Object... params) {
        return getReaderSession().selectOne(statement, assemblyParams(params));
    }

    @Override
    public T queryEntity(String statement, Object... params) {
        return getReaderSession().selectOne(statement, assemblyParams(params));
    }

    @Override
    public <K, V> Map<K, V> queryMap(String statement, Object... params) {
        return getReaderSession().selectOne(statement, assemblyParams(params));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> queryList(String statement, Object... params) {
        return getReaderSession().selectList(statement, assemblyParams(params));
    }

    @Override
    public <E> List<E> queryList(Class<E> entityClass, String statement, Object... params) throws DaoAccessException {
        return getReaderSession().selectList(statement, assemblyParams(params));
    }


//    @Override
//    public <K, V> Map<K, V> queryMap(String statement, String mapKey) {
//        return getReaderSession().selectMap(statement, mapKey);
//    }

    @Override
    public List<T> queryRange(String statement, int firstResult, int maxResults, Object... params) throws DaoAccessException {
        Object[] array = ArrayUtils.addAll(params, new Integer[]{firstResult, maxResults});
        RowBounds rowBounds = new RowBounds(firstResult, maxResults);
        PaginationInterceptor.setIsCount(false);
        return getReaderSession().selectList(statement, assemblyParams(array), rowBounds);
    }

    @Override
    public Page<T> queryPage(String statement, int pageNumber, int pageSize, Object... params) throws DaoAccessException {
        PageRequest pageRequest = new PageRequest(pageNumber, pageSize);
        return queryPage(statement, pageRequest, params);
    }

    @Override
    public <E> Page<E> queryPage(Class<E> entityClass, String statement, PageRequest pageRequest, Object... params) throws DaoAccessException {
        try {
            Page<E> page = new DefaultPage<>(pageRequest.getPageNumber(), pageRequest.getPageSize());

            PaginationInterceptor.setIsCount(true);
            RowBounds rowBounds = new RowBounds(page.getFirstResult(), pageRequest.getPageSize());
            getReaderSession().selectList(statement, assemblyParams(params), rowBounds);

            int recordTotal = PaginationInterceptor.getPaginationTotal();
            PaginationInterceptor.setIsCount(false);

            if (recordTotal > 0) {
                page.setTotalCount(recordTotal);

                rowBounds = new RowBounds(page.getFirstResult(), pageRequest.getPageSize());
                List<E> result = getReaderSession().selectList(statement, assemblyParams(params), rowBounds);
                page.setResult(result);
            }
            return page;
        } catch (Exception e) {
            logger.error("查询出错", e);
            throw new DaoAccessException(e);
        }
    }


    @Override
    public Page<T> queryPage(String statement, PageRequest pageRequest, Object... params) throws DaoAccessException {
        try {
            Page<T> page = new DefaultPage<>(pageRequest.getPageNumber(), pageRequest.getPageSize());

            PaginationInterceptor.setIsCount(true);

            RowBounds rowBounds = new RowBounds(page.getFirstResult(), pageRequest.getPageSize());
            getReaderSession().selectList(statement, assemblyParams(params), rowBounds);

            int recordTotal = PaginationInterceptor.getPaginationTotal();
            PaginationInterceptor.setIsCount(false);

            if (recordTotal > 0) {
                page.setTotalCount(recordTotal);
                rowBounds = new RowBounds(page.getFirstResult(), pageRequest.getPageSize());
                List<T> result = getReaderSession().selectList(statement, assemblyParams(params), rowBounds);
                page.setResult(result);
            }
            return page;
        } catch (Exception e) {
            logger.error("查询出错", e);
            throw new DaoAccessException(e);
        }
    }
//
//    public Page<T> queryPageBySpringBatch(String statement, PageRequest pageRequest, Object... params) throws DaoAccessException {
//        try {
//            Page<T> page = new DefaultPage<T>(pageRequest.getPageNumber(), pageRequest.getPageSize());
//            myBatisPagingItemReader.setQueryId(statement);
////            myBatisPagingItemReader.setParameterValues(paramsToMap(params));
//            myBatisPagingItemReader.setCurrentItemCount(pageRequest.getPageSize() * pageRequest.getPageNumber());
//
//            myBatisPagingItemReader.setPageSize(pageRequest.getPageSize());
//            List<T> result = new ArrayList<>();
//            T entity = (T) myBatisPagingItemReader.read();
//            while (entity != null) {
//                result.add(entity);
//                entity = (T) myBatisPagingItemReader.read();
//            }
//            page.setResult(result);
//            return page;
//        } catch (Exception e) {
//            logger.error("查询出错", e);
//            throw new DaoAccessException(e);
//        }
//    }

    @Override
    public NavPage<T> queryNavPage(String statement, PageRequest pageRequest, Object... params) throws DaoAccessException {
        try {
            NavPage<T> page = new NavPage<>(pageRequest.getPageNumber(), pageRequest.getPageSize());
            PaginationInterceptor.setIsCount(true);
            RowBounds rowBounds = new RowBounds(page.getFirstResult(), pageRequest.getPageSize());
            getReaderSession().selectList(statement, assemblyParams(params), rowBounds);

            int recordTotal = PaginationInterceptor.getPaginationTotal();
            PaginationInterceptor.setIsCount(false);

            if (recordTotal > 0) {
                page.setTotalCount(recordTotal);

                rowBounds = new RowBounds(page.getFirstResult(), pageRequest.getPageSize());
                List<T> result = getReaderSession().selectList(statement, assemblyParams(params), rowBounds);
                page.setResult(result);
            }
            return page;
        } catch (Exception e) {
            logger.error("查询出错", e);
            throw new DaoAccessException(e);
        }
    }

    @Override
    public CursorPage<T> queryCursorPage(String statement, CursorPageRequest pageRequest, Object... params) throws DaoAccessException {
        CursorPage<T> cursorPage = new DefaultCursorPage<>(pageRequest.getLimit());
        PaginationInterceptor.setIsCount(true);
        RowBounds rowBounds = new RowBounds(0, cursorPage.getLimit());
        getReaderSession().selectList(statement, assemblyParams(params), rowBounds);

        int recordTotal = PaginationInterceptor.getPaginationTotal();
        PaginationInterceptor.setIsCount(false);


        if (recordTotal > 0) {
            cursorPage.setTotal(recordTotal);
            rowBounds = new RowBounds(0, cursorPage.getLimit());
            List<T> result = getReaderSession().selectList(statement, assemblyParams(params), rowBounds);
            cursorPage.setItems(result);
        }
        return cursorPage;
    }


    @Override
    public boolean insert(String statement, T entity) throws DaoAccessException {
        return getWriterSession().insert(statement, entity) > 0;
    }

    @Override
    public boolean insert(String statement, Object... parameter) throws DaoAccessException {
        return getWriterSession().insert(statement, assemblyParams(parameter)) > 0;
    }

    @Override
    public int insertAndReturnKey(String statement, T entity) throws DaoAccessException {
        return getWriterSession().insert(statement, entity);

    }

    @Override
    public int insertAndReturnKey(String statement, Object... parameter) throws DaoAccessException {
        return getWriterSession().insert(statement, assemblyParams(parameter));
    }

    @Override
    public boolean update(String statement, T entity) {
        Assert.hasText(statement);
        return getWriterSession().update(statement, entity) > 0;
    }

    @Override
    public boolean update(String statement, Object... parameter) {
        Assert.hasText(statement);
        return getWriterSession().update(statement, assemblyParams(parameter)) > 0;
    }


    @Override
    public boolean delete(String statement, Object... parameter) {
        Assert.notNull(parameter);
        return getWriterSession().delete(statement, assemblyParams(parameter)) > 0;
    }

    @Override
    public boolean batchInsert(String statement, Collection<T> entities) throws DaoAccessException {
        Assert.hasText(statement);
        Assert.notNull(entities);
        SqlSession sqlSession = getWriterSession().getSqlSessionFactory().openSession(ExecutorType.BATCH);
        try {
            sqlSession.insert(statement, entities);
            sqlSession.commit();
            return true;
        } catch (Exception e) {
            logger.error("batchInsert error：", e);
            throw new DaoAccessException(e);
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public boolean batchUpdate(String statement, Collection<T> entities) throws DaoAccessException {
        Assert.hasText(statement);
        Assert.notNull(entities);
        SqlSession sqlSession = getWriterSession().getSqlSessionFactory().openSession(ExecutorType.BATCH);
        try {
            sqlSession.update(statement, entities);
            sqlSession.commit();
            return true;
        } catch (Exception e) {
            logger.error("batchInsert error：", e);
            throw new DaoAccessException(e);
        } finally {
            sqlSession.close();
        }
    }

}
