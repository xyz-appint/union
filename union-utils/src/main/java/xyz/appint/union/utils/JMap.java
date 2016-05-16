package xyz.appint.union.utils;

import com.google.common.base.CaseFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.appint.union.utils.exception.ConversionException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class JMap extends HashMap<String, Object> {
    static Logger log = LoggerFactory.getLogger(JMap.class);
    private static final long serialVersionUID = 3234339170029857166L;

    private static final boolean DEFAULT_BOOLEAN_VAL = false;

    public Object get(String key) {
        return super.get(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends Object> m) {
        int numKeysToBeAdded = m.size();
        if (numKeysToBeAdded == 0)
            return;

        for (Iterator<? extends Map.Entry<? extends String, ? extends Object>> i = m.entrySet().iterator(); i.hasNext(); ) {
            Map.Entry<? extends String, ? extends Object> e = i.next();
            put(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, e.getKey()), e.getValue());
        }
    }

    public boolean getBoolean(String key) {
        Boolean b = getBoolean(key, DEFAULT_BOOLEAN_VAL);
        if (b != null) {
            return b.booleanValue();
        } else {
            return DEFAULT_BOOLEAN_VAL;
        }
    }

    public Boolean getBoolean(String key, Boolean defaultValue) {
        Object value = get(key);
        if (value == null) {
            return defaultValue;
        } else {
            try {
                return ConvertUtils.toBoolean((value));
            } catch (ConversionException e) {
                log.error("转换异常", e);
                return defaultValue;
            }
        }
    }

    public byte getByte(String key) {
        Byte b = getByte(key, null);
        if (b != null) {
            return b.byteValue();
        } else {
            return 0;
        }
    }

    public byte getByte(String key, byte defaultValue) {
        return getByte(key, new Byte(defaultValue)).byteValue();
    }

    public Byte getByte(String key, Byte defaultValue) {
        Object value = get(key);
        if (value == null) {
            return defaultValue;
        } else {
            try {
                return ConvertUtils.toByte(value);
            } catch (ConversionException e) {
                log.error("转换异常", e);
                return defaultValue;
            }
        }
    }

    public double getDouble(String key) {
        Double d = getDouble(key, null);
        if (d != null) {
            return d.doubleValue();
        } else {
            return 0.0;
        }
    }

    public double getDouble(String key, double defaultValue) {
        return getDouble(key, new Double(defaultValue)).doubleValue();
    }

    public Double getDouble(String key, Double defaultValue) {
        Object value = get(key);
        if (value == null) {
            return defaultValue;
        } else {
            try {
                return ConvertUtils.toDouble(value);
            } catch (ConversionException e) {
                log.error("转换异常", e);
                return defaultValue;
            }
        }
    }

    public float getFloat(String key) {
        Float f = getFloat(key, null);
        if (f != null) {
            return f.floatValue();
        } else {
            return 0.0f;
        }
    }

    public float getFloat(String key, float defaultValue) {
        return getFloat(key, new Float(defaultValue)).floatValue();
    }

    public Float getFloat(String key, Float defaultValue) {
        Object value = get(key);
        if (value == null) {
            return defaultValue;
        } else {
            try {
                return ConvertUtils.toFloat(value);
            } catch (ConversionException e) {
                log.error("转换异常", e);
                return defaultValue;
            }
        }
    }

    public int getInt(String key) {
        Integer i = getInteger(key, null);
        if (i != null) {
            return i.intValue();
        } else {
            return 0;
        }
    }

    public int getInt(String key, int defaultValue) {
        Integer i = getInteger(key, null);

        if (i == null) {
            return defaultValue;
        }
        return i.intValue();
    }

    public Integer getInteger(String key, Integer defaultValue) {
        Object value = get(key);

        if (value == null) {
            return defaultValue;
        } else {
            try {
                return ConvertUtils.toInteger(value);
            } catch (ConversionException e) {
                log.error("转换异常", e);
                return defaultValue;
            }
        }
    }

    public long getLong(String key) {
        Long l = getLong(key, null);
        if (l != null) {
            return l.longValue();
        } else {
            return 0;
        }
    }

    public long getLong(String key, long defaultValue) {
        return getLong(key, new Long(defaultValue)).longValue();
    }

    public Long getLong(String key, Long defaultValue) {
        Object value = get(key);
        if (value == null) {
            return defaultValue;
        } else {
            try {
                return ConvertUtils.toLong(value);
            } catch (ConversionException e) {
                log.error("转换异常", e);
                return defaultValue;
            }
        }
    }

    public short getShort(String key) {
        Short s = getShort(key, null);
        if (s != null) {
            return s.shortValue();
        } else {
            return 0;
        }
    }

    public short getShort(String key, short defaultValue) {
        return getShort(key, new Short(defaultValue)).shortValue();
    }

    public Short getShort(String key, Short defaultValue) {
        Object value = get(key);
        if (value == null) {
            return defaultValue;
        } else {
            try {
                return ConvertUtils.toShort(value);
            } catch (ConversionException e) {
                log.error("转换异常", e);
                return defaultValue;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public BigDecimal getBigDecimal(String key) {
        BigDecimal number = getBigDecimal(key, null);
        if (number != null) {
            return number;
        } else {
            return null;
        }
    }

    public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
        Object value = get(key);
        if (value == null) {
            return defaultValue;
        } else {
            try {
                return ConvertUtils.toBigDecimal(value);
            } catch (ConversionException e) {
                log.error("转换异常", e);
                return defaultValue;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public BigInteger getBigInteger(String key) {
        BigInteger number = getBigInteger(key, null);
        if (number != null) {
            return number;
        } else {
            return null;
        }
    }

    public BigInteger getBigInteger(String key, BigInteger defaultValue) {
        Object value = get(key);

        if (value == null) {
            return defaultValue;
        } else {
            try {
                return ConvertUtils.toBigInteger(value);
            } catch (ConversionException e) {
                log.error("转换异常", e);
                return defaultValue;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getString(String key) {
        String s = getString(key, null);
        if (s != null) {
            return s;
        } else {
            return "";
        }
    }

    public String getString(String key, String defaultValue) {
        Object value = get(key);

        if (value instanceof String) {
            return value.toString();
        } else if (value == null) {
            return defaultValue;
        } else {
            return defaultValue;
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<Object> getList(String key) {
        return getList(key, new ArrayList<Object>());
    }

    public List<Object> getList(String key, List<Object> defaultValue) {
        Object value = get(key);
        List<Object> list;
        if (value instanceof String) {
            list = new ArrayList<Object>(1);
            list.add((String) value);
        } else if (value instanceof List) {
            list = new ArrayList<Object>();
            List<?> l = (List<?>) value;
            for (Object elem : l) {
                list.add(elem);
            }
        } else if (value == null) {
            list = defaultValue;
        } else if (value.getClass().isArray()) {
            return Arrays.asList((Object[]) value);
        } else {
            return Collections.singletonList((Object) value.toString());
        }
        return list;
    }

}
