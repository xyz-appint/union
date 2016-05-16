package xyz.appint.union.dao.dao;


import xyz.appint.union.dao.page.*;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 基础dao
 */
public interface BaseDao<T extends Serializable> {
    /**
     * 获取读操作数据源
     *
     * @return
     */
//    DataSource getReaderDataSource();
//
//    /**
//     * 注入读操作数据源
//     */
//    void setReaderDataSource(DataSource dataSource) throws DaoAccessException;

    /**
     * 获取写操作数据源
     *
     * @return
     */
    DataSource getWriterDataSource();

    /**
     * 注入写操作数据源
     */
    void setWriterDataSource(DataSource dataSource) throws DaoAccessException;

    /**
     * 根据多个条件查询实体
     *
     * @param statement
     * @param params
     * @return
     */
    T queryEntity(String statement, Object... params);


    /**
     * 查询单个int值
     *
     * @param statement
     * @param params
     * @return
     */
    Integer queryInt(String statement, Object... params);

    /**
     * 查詢long類型
     *
     * @param statement
     * @param params
     * @return
     */
    Long queryLong(String statement, Object... params);


    /**
     * 查询单个字符串值
     *
     * @param statement
     * @param params
     * @return String
     */
    String queryString(String statement, Object... params);


    /**
     * 根据0..N个条件查询并返回object值
     *
     * @param statement
     * @param params
     * @param <O>
     * @return
     */
    <O> O queryOne(String statement, Object... params);


    /**
     * 查询list
     *
     * @param statement
     * @param params
     * @return
     */
    List<T> queryList(String statement, Object... params);

    /**
     * 查询List集合
     *
     * @param entityClass
     * @param statement
     * @param params
     * @param <E>
     * @return
     * @throws DaoAccessException
     */
    <E> List<E> queryList(Class<E> entityClass, String statement, Object... params) throws DaoAccessException;

    /**
     * 查询map
     *
     * @param statement
     * @param params
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Map<K, V> queryMap(String statement, Object... params);

    /**
     * 根据条件查询范围内数据（分页查询）
     *
     * @param statement
     * @param firstResult
     * @param maxResults
     * @param params
     * @return
     * @throws DaoAccessException
     */
    List<T> queryRange(String statement, int firstResult, int maxResults, Object... params) throws DaoAccessException;

    /**
     * 分页查询
     *
     * @param statement
     * @param pageNumber
     * @param pageSize
     * @param params
     * @return
     * @throws DaoAccessException
     */
    Page<T> queryPage(String statement, int pageNumber, int pageSize, Object... params) throws DaoAccessException;

    /**
     * 分页查询
     *
     * @param entityClass
     * @param statement
     * @param pageRequest
     * @param params
     * @param <E>
     * @return
     * @throws DaoAccessException
     */
    <E> Page<E> queryPage(Class<E> entityClass, String statement, PageRequest pageRequest, Object... params) throws DaoAccessException;

    /**
     * 分页查询
     *
     * @param statement
     * @param pageRequest
     * @param params
     * @return
     * @throws DaoAccessException
     */
    Page<T> queryPage(String statement, PageRequest pageRequest, Object... params) throws DaoAccessException;

    /**
     * 数字分页条
     *
     * @param statement
     * @param pageRequest
     * @param params
     * @return
     * @throws DaoAccessException
     */
    NavPage<T> queryNavPage(String statement, PageRequest pageRequest, Object... params) throws DaoAccessException;


    /**
     * 游标分页
     * @param statement
     * @param pageRequest
     * @param params
     * @return
     * @throws DaoAccessException
     */
    CursorPage<T> queryCursorPage(String statement, CursorPageRequest pageRequest, Object... params) throws DaoAccessException;

    /**
     * 插入
     *
     * @param statement
     * @param entity
     * @return
     * @throws DaoAccessException
     */
    boolean insert(String statement, T entity) throws DaoAccessException;

    /**
     * 插入
     *
     * @param statement
     * @param parameter
     * @return
     * @throws DaoAccessException
     */
    boolean insert(String statement, Object... parameter) throws DaoAccessException;

    /**
     * 插入并返回key值
     *
     * @param statement
     * @param entity
     * @return
     * @throws DaoAccessException
     */
    int insertAndReturnKey(String statement, T entity) throws DaoAccessException;

    /**
     * 插入并返回key值
     *
     * @param statement
     * @param parameter
     * @return
     * @throws DaoAccessException
     */
    int insertAndReturnKey(String statement, Object... parameter) throws DaoAccessException;

    /**
     * 更新
     *
     * @param statement
     * @param entity
     * @return
     */
    boolean update(String statement, T entity);


    boolean update(String statement, Object... parameter);

    /**
     * 删除
     *
     * @param statement
     * @param parameter
     * @return
     */
    boolean delete(String statement, Object... parameter) throws DaoAccessException;

    /**
     * 批量插入
     *
     * @param statement
     * @param entities
     * @throws DaoAccessException
     */
    boolean batchInsert(String statement, Collection<T> entities) throws DaoAccessException;

    /**
     * 批量更新
     *
     * @param statement
     * @param entities
     * @return
     * @throws DaoAccessException
     */
    boolean batchUpdate(String statement, Collection<T> entities) throws DaoAccessException;

//    public Page<T> queryPageBySpringBatch(String statement, PageRequest pageRequest, Object... params) throws DaoAccessException;
}
