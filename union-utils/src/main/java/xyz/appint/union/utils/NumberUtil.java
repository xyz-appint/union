package xyz.appint.union.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NumberUtil {
    public static boolean isNumber(Object obj) {
        if (obj == null) {
            return false;
        }
        return obj instanceof Number;
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException nfe) {
        }
        return false;
    }

    /**
     * Returns the BigDecimal value n with trailing zeroes removed.
     */
    public static BigDecimal trim(BigDecimal n) {
        try {
            while (true) {
                n = n.setScale(n.scale() - 1);
            }
        } catch (ArithmeticException e) {
            // no more trailing zeroes so exit.
        }
        return n;
    }

    /**
     * Returns the BigDecimal value n with exactly 'prec' decimal places. Zeroes
     * are padded to the right of the decimal point if necessary.
     */
    public static BigDecimal format(BigDecimal n, int prec) {
        return n.setScale(prec, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Convert an Object of type Class to a Number.
     */
    public static Object toObject(Class<? extends Object> clazz, Object value) {
        if (value == null)
            return null;
        if (clazz == null)
            return value;

        if (Boolean.class.isAssignableFrom(clazz))
            return toBoolean(value);
        if (Byte.class.isAssignableFrom(clazz))
            return toByte(value);
        if (Short.class.isAssignableFrom(clazz))
            return toShort(value);
        if (Integer.class.isAssignableFrom(clazz))
            return toInteger(value);
        if (Long.class.isAssignableFrom(clazz))
            return toLong(value);
        if (Float.class.isAssignableFrom(clazz))
            return toFloat(value);
        if (Double.class.isAssignableFrom(clazz))
            return toDouble(value);
        if (BigInteger.class.isAssignableFrom(clazz))
            return toBigInteger(value);
        if (BigDecimal.class.isAssignableFrom(clazz))
            return toBigDecimal(value);

        return value;
    }

    /**
     * Convert a Sting 'TRUE' to 1, otherwise 0.
     */
    public static int valueOfBoolean(String string) {
        return string != null && "TRUE".equalsIgnoreCase(string) ? 1 : 0;
    }

    /**
     * Optimisation Code
     */
    public static Boolean getBoolean(boolean bool) {
        return bool ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * Convert an Object to a Boolean.
     */
    public static Boolean toBoolean(Object value) {
        if (value == null)
            return false;
        //return null;
        if (value instanceof Boolean)
            return (Boolean) value;

        if ("TRUE".equalsIgnoreCase(value.toString()))
            return Boolean.TRUE;
        if ("".equals(value.toString()))
            return false;
        //return null;

        return Boolean.FALSE;
    }

    /**
     * Convert an Object to an Integer.
     */
    public static Integer toInteger(Object value) {
        if (value == null)
            return null;
        if (value instanceof Integer)
            return (Integer) value;
        if (value instanceof String) {
            if ("".equals((String) value))
                return null;
            return new Integer((String) value);
        }

        if (value instanceof Number)
            return new Integer(((Number) value).intValue());

        return new Integer(value.toString());
    }

    /**
     * Convert an Object to a Long.
     */
    public static Long toLong(Object value) {
        if (value == null)
            return null;
        if (value instanceof Long)
            return (Long) value;
        if (value instanceof String) {
            if ("".equals((String) value))
                return null;
            return new Long((String) value);
        }
        if (value instanceof Number)
            return new Long(((Number) value).shortValue());

        return new Long(value.toString());
    }

    /**
     * Convert an Object to a Short.
     */
    public static Short toShort(Object value) {
        if (value == null)
            return null;
        if (value instanceof Short)
            return (Short) value;
        if (value instanceof String) {
            if ("".equals((String) value))
                return null;
            return new Short((String) value);
        }
        if (value instanceof Number)
            return new Short(((Number) value).shortValue());

        return new Short(value.toString());
    }

    /**
     * Convert an Object to a Byte.
     */
    public static Byte toByte(Object value) {
        if (value == null)
            return null;
        if (value instanceof Byte)
            return (Byte) value;
        if (value instanceof String) {
            if ("".equals((String) value))
                return null;
            return new Byte((String) value);
        }
        if (value instanceof Number)
            return new Byte(((Number) value).byteValue());

        return new Byte(value.toString());
    }

    /**
     * Convert an Object to a Float.
     */
    public static Float toFloat(Object value) {
        if (value == null)
            return null;
        if (value instanceof Float)
            return (Float) value;
        if (value instanceof String) {
            if ("".equals((String) value))
                return null;
            return new Float((String) value);
        }
        if (value instanceof Number)
            return new Float(((Number) value).floatValue());

        return new Float(value.toString());
    }

    /**
     * Convert an Object to a Double.
     */
    public static Double toDouble(Object value) {
        if (value == null)
            return null;
        if (value instanceof Double)
            return (Double) value;
        if (value instanceof String) {
            if ("".equals((String) value))
                return null;
            return new Double((String) value);
        }
        if (value instanceof Number)
            return new Double(((Number) value).doubleValue());

        return new Double(value.toString());
    }

    /**
     * Convert an Object to a BigInteger.
     */
    public static BigInteger toBigInteger(Object value) {
        if (value == null)
            return null;
        if (value instanceof BigInteger)
            return (BigInteger) value;
        if (value instanceof String) {
            if ("".equals((String) value))
                return null;
            return new BigInteger((String) value);
        }

        return new BigInteger(value.toString());
    }

    /**
     * Convert an Object to a BigDecimal.
     */
    public static BigDecimal toBigDecimal(Object value) {
        if (value == null)
            return null;
        if (value instanceof BigDecimal)
            return (BigDecimal) value;
        if (value instanceof String) {
            if ("".equals((String) value))
                return null;
            return new BigDecimal((String) value);
        }
        if (value instanceof Number)
            return new BigDecimal(((Number) value).doubleValue());

        return new BigDecimal(value.toString());
    }

    /**
     * Convert an Object to a Boolean.
     */
    public static boolean booleanValue(Object value) {
        if (value == null)
            return false;
        if (value instanceof Boolean)
            return ((Boolean) value).booleanValue();
        if (value instanceof Number)
            return ((Number) value).intValue() != 0;
        return "TRUE".equalsIgnoreCase(value.toString());
    }

    /**
     * Convert an Object to an int, or 0 if it is null.
     */
    public static int intValue(Object value) {
        if (value == null)
            return 0;
        return toInteger(value).intValue();
    }

    /**
     * Convert an Object to a long, or 0 if it is null.
     */
    public static long longValue(Object value) {
        if (value == null)
            return 0L;
        return toLong(value).longValue();
    }

    /**
     * Convert an Object to a short, or 0 if it is null.
     */
    public static short shortValue(Object value) {
        if (value == null)
            return 0;
        return toShort(value).shortValue();
    }

    /**
     * Convert an Object to a byte, or 0 if it is null.
     */
    public static byte byteValue(Object value) {
        if (value == null)
            return 0;
        return toByte(value).byteValue();
    }

    /**
     * Convert an Object to a float, or 0 if it is null.
     */
    public static float floatValue(Object value) {
        if (value == null)
            return 0.0f;
        return toFloat(value).floatValue();
    }

    /**
     * Convert an Object to a double, or 0 if it is null.
     */
    public static double doubleValue(Object value) {
        if (value == null)
            return 0.0;
        return toDouble(value).doubleValue();
    }
}
