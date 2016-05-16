package xyz.appint.union.dao.dao;

import com.google.common.base.Defaults;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.appint.union.dao.dao.annotation.Col;
import xyz.appint.union.dao.page.*;
import xyz.appint.union.utils.ObjectUtils;
import xyz.appint.union.utils.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Justin on 2014/8/11.
 */
public abstract class BaseDaoSupport<T extends Serializable> {
    private BaseDao<T> baseDao;


    @Autowired
    public void setBaseDao(BaseDao<T> baseDao) {
        this.baseDao = baseDao;
    }

    private static final Cache<String, Method> METHOD_CACHE = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(30, TimeUnit.MINUTES).build();


    private final static String NAMESPACE = "%s.%s";

    private String getCallerMethodName() {
        return Thread.currentThread().getStackTrace()[4].getMethodName();
    }

    protected String getStatement() {
        return String.format(NAMESPACE, this.getClass().getName(), getCallerMethodName());
    }

    protected int queryInt(Object... params) {
        return baseDao.queryInt(getStatement(), transformParameters(params));
    }


    protected Long queryLong(Object... params) {
        return baseDao.queryLong(getStatement(), transformParameters(params));
    }

    protected String queryString(Object... params) {
        return baseDao.queryString(getStatement(), transformParameters(params));
    }

    protected <O> O queryOne(Object... params) {
        return baseDao.queryOne(getStatement(), transformParameters(params));
    }

    protected T queryEntity(Object... params) {
        return baseDao.queryEntity(getStatement(), transformParameters(params));
    }


    protected <K, V> Map<K, V> queryMap(Object... params) {
        return baseDao.queryMap(getStatement(), transformParameters(params));
    }


    @SuppressWarnings("unchecked")
    protected List<T> queryList(Object... params) {
        return baseDao.queryList(getStatement(), transformParameters(params));
    }


    protected <E> List<E> queryList(Class<E> entityClass, Object... params) throws DaoAccessException {
        return baseDao.queryList(entityClass, getStatement(), transformParameters(params));
    }


    protected List<T> queryRange(int firstResult, int maxResults, Object... params) throws DaoAccessException {
        return baseDao.queryRange(getStatement(), firstResult, maxResults, transformParameters(params));
    }


    protected <E> Page<E> queryPage(Class<E> entityClass, PageRequest pageRequest, Object... params) throws DaoAccessException {
        return baseDao.queryPage(entityClass, getStatement(), pageRequest, transformParameters(params));
    }


    protected Page<T> queryPage(int pageNumber, int pageSize, Object... params) throws DaoAccessException {
        return baseDao.queryPage(getStatement(), pageNumber, pageSize, transformParameters(params));
    }


    protected Page<T> queryPage(PageRequest pageRequest, Object... params) throws DaoAccessException {
        return baseDao.queryPage(getStatement(), pageRequest, transformParameters(params));
    }

    protected NavPage<T> queryNavPage(PageRequest pageRequest, Object... params) throws DaoAccessException {
        return baseDao.queryNavPage(getStatement(), pageRequest, transformParameters(params));
    }

    public CursorPage<T> queryCursorPage(CursorPageRequest pageRequest, Object... params) throws DaoAccessException {
        return baseDao.queryCursorPage(getStatement() , pageRequest , transformParameters(params));
    }

    protected boolean insert(T entity) throws DaoAccessException {
        return baseDao.insert(getStatement(), entity);
    }


    protected boolean insert(Object... parameters) throws DaoAccessException {
        return baseDao.insert(getStatement(), transformParameters(parameters));
    }


    protected int insertAndReturnKey(T entity) throws DaoAccessException {
        return baseDao.insertAndReturnKey(getStatement(), entity);
    }


    protected int insertAndReturnKey(Object... parameters) throws DaoAccessException {
        return baseDao.insertAndReturnKey(getStatement(), transformParameters(parameters));
    }


    protected boolean update(T entity) {
        return baseDao.update(getStatement(), entity);
    }


    protected boolean update(Object... parameters) {
        return baseDao.update(getStatement(), transformParameters(parameters));
    }


    protected boolean delete(Object... parameters) {
        return baseDao.delete(getStatement(), transformParameters(parameters));
    }


    protected boolean batchInsert(List<T> list) throws DaoAccessException {
        return baseDao.batchInsert(getStatement(), list);
    }


    protected boolean batchUpdate(List<T> list) throws DaoAccessException {
        return baseDao.batchUpdate(getStatement(), list);
    }


    public static Method getMethodByName(final Class theClass, final String name) {
        try {
            return METHOD_CACHE.get(theClass.getName() + ":" + name, new Callable<Method>() {
                @Override
                public Method call() throws Exception {
                    Method[] methods = theClass.getMethods();
                    for (int n = 0; n < methods.length; ++n) {
                        if (name.equals(methods[n].getName()))
                            return methods[n];
                    }
                    return null;
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object transformParameters(Object... paramVal) {
        if (paramVal == null || paramVal.length == 0) {
            return null;
        }
        if (paramVal.length == 1) {
            return paramVal[0];
        }
        StackTraceElement currentStackElement = Thread.currentThread().getStackTrace()[3];
        try {
            Class currentClass = Class.forName(currentStackElement.getClassName());
            Method method = getMethodByName(currentClass, currentStackElement.getMethodName());
            Parameter[] params = method.getParameters();
            Map<String, Object> args = new HashMap<>();
            boolean isAnnotationPresent = false;
            int k = 0;
            for (int i = 0; i < params.length; i++) {
                Parameter p = params[i];
                if (p.isAnnotationPresent(Col.class)) {
                    isAnnotationPresent = true;
                    Col col = p.getAnnotation(Col.class);
                    if (col.include() == Col.Include.NON_NULL) {
                        if (paramVal[k] == null) {
                            continue;
                        }
                    } else if (col.include() == Col.Include.NON_DEFAULT) {
                        if (paramVal[k] == Defaults.defaultValue(p.getType())) {
                            continue;
                        }
                    } else if (col.include() == Col.Include.NON_EMPTY) {
                        if (ObjectUtils.isEmptyArray(paramVal[k])) {
                            continue;
                        }
                    }
                    String argsName = col.name();
                    if(StringUtils.hasText(col.value())) {
                        argsName = col.value();
                    }
                    args.put(argsName, paramVal[k]);
                    k++;
                }
            }
            if (isAnnotationPresent == false) {
                Map<String, Object> m = new HashMap<>();
                int i = 0;
                for (Object val : paramVal) {
                    m.put("" + i, val);
                    i++;
                }
                return m;
            }
            if (args.isEmpty() == false) {
                return args;
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}
