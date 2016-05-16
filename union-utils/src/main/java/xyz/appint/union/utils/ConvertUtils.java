package xyz.appint.union.utils;

import xyz.appint.union.utils.exception.ConversionException;
import org.apache.commons.lang.BooleanUtils;

import java.awt.*;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


public class ConvertUtils {
    /**
     * list�ָ���
     */
    static final char LIST_ESC_CHAR = '\\';

    static final String LIST_ESCAPE = String.valueOf(LIST_ESC_CHAR);

    private static final String HEX_PREFIX = "0x";

    /**
     * 16 ���ƻ���
     */
    private static final int HEX_RADIX = 16;

    /**
     * 2������ǰ�
     */
    private static final String BIN_PREFIX = "0b";

    /**
     * 2���ƻ���
     */
    private static final int BIN_RADIX = 2;

    private static final String DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";


    private static final Class<?>[] CONSTR_ARGS = {String.class};

    private static final String INTERNET_ADDRESS_CLASSNAME = "javax.mail.internet.InternetAddress";


    private ConvertUtils() {
    }


    static Object to(Class<?> cls, Object value, Object[] params)
            throws ConversionException {
        if (Boolean.class.equals(cls) || Boolean.TYPE.equals(cls)) {
            return toBoolean(value);
        } else if (Number.class.isAssignableFrom(cls) || cls.isPrimitive()) {
            if (Integer.class.equals(cls) || Integer.TYPE.equals(cls)) {
                return toInteger(value);
            } else if (Long.class.equals(cls) || Long.TYPE.equals(cls)) {
                return toLong(value);
            } else if (Byte.class.equals(cls) || Byte.TYPE.equals(cls)) {
                return toByte(value);
            } else if (Short.class.equals(cls) || Short.TYPE.equals(cls)) {
                return toShort(value);
            } else if (Float.class.equals(cls) || Float.TYPE.equals(cls)) {
                return toFloat(value);
            } else if (Double.class.equals(cls) || Double.TYPE.equals(cls)) {
                return toDouble(value);
            } else if (BigInteger.class.equals(cls)) {
                return toBigInteger(value);
            } else if (BigDecimal.class.equals(cls)) {
                return toBigDecimal(value);
            }
        } else if (Date.class.equals(cls)) {
            return toDate(value, (String) params[0]);
        } else if (Calendar.class.equals(cls)) {
            return toCalendar(value, (String) params[0]);
        } else if (URL.class.equals(cls)) {
            return toURL(value);
        } else if (Locale.class.equals(cls)) {
            return toLocale(value);
        } else if (isEnum(cls)) {
            return convertToEnum(cls, value);
        } else if (Color.class.equals(cls)) {
            return toColor(value);
        } else if (cls.getName().equals(INTERNET_ADDRESS_CLASSNAME)) {
            return toInternetAddress(value);
        } else if (InetAddress.class.isAssignableFrom(cls)) {
            return toInetAddress(value);
        }

        throw new ConversionException("The value '" + value + "' ("
                + value.getClass() + ")" + " can't be converted to a "
                + cls.getName() + " object");
    }


    public static boolean isBaseDataType(Class<?> cls) {
        if (Boolean.class.equals(cls) || Boolean.TYPE.equals(cls)) {
            return true;
        } else if (Number.class.isAssignableFrom(cls) || cls.isPrimitive()) {
            if (Integer.class.equals(cls) || Integer.TYPE.equals(cls)) {
                return true;
            } else if (Long.class.equals(cls) || Long.TYPE.equals(cls)) {
                return true;
            } else if (Byte.class.equals(cls) || Byte.TYPE.equals(cls)) {
                return true;
            } else if (Short.class.equals(cls) || Short.TYPE.equals(cls)) {
                return true;
            } else if (Float.class.equals(cls) || Float.TYPE.equals(cls)) {
                return true;
            } else if (Double.class.equals(cls) || Double.TYPE.equals(cls)) {
                return true;
            } else if (BigInteger.class.equals(cls)) {
                return true;
            } else if (BigDecimal.class.equals(cls)) {
                return true;
            }
        } else if (Date.class.equals(cls)) {
            return true;
        } else if (String.class.equals(cls)) {
            return true;
        } else if (Timestamp.class.equals(cls)) {
            return true;
        }
        return false;
    }

    public static final String toString(Object obj) {
        return toString(obj, "");
    }

    public static final String toString(Object obj, String defaultVal) {
        if (obj != null) {
            return obj.toString();
        } else {
            return defaultVal;
        }
    }


    public static Boolean toBoolean(Object value) throws ConversionException {
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof String) {
            Boolean b = BooleanUtils.toBooleanObject((String) value);
            if (b == null) {
                throw new ConversionException("The value " + value + " can't be converted to a Boolean object");
            }
            return b;
        } else {
            throw new ConversionException("The value " + value + " can't be converted to a Boolean object");
        }
    }

    public static Byte toByte(Object value) throws ConversionException {
        Number n = toNumber(value, Byte.class);
        if (n instanceof Byte) {
            return (Byte) n;
        } else {
            return new Byte(n.byteValue());
        }
    }

    public static Short toShort(Object value) throws ConversionException {
        Number n = toNumber(value, Short.class);
        if (n instanceof Short) {
            return (Short) n;
        } else {
            return new Short(n.shortValue());
        }
    }

    public static Integer toInteger(Object value) throws ConversionException {
        Number n = toNumber(value, Integer.class);
        if (n instanceof Integer) {
            return (Integer) n;
        } else {
            return new Integer(n.intValue());
        }
    }


    public static Long toLong(Object value) throws ConversionException {
        Number n = toNumber(value, Long.class);
        if (n instanceof Long) {
            return (Long) n;
        } else {
            return new Long(n.longValue());
        }
    }


    public static Float toFloat(Object value) throws ConversionException {
        Number n = toNumber(value, Float.class);
        if (n instanceof Float) {
            return (Float) n;
        } else {
            return new Float(n.floatValue());
        }
    }


    public static Double toDouble(Object value) throws ConversionException {
        Number n = toNumber(value, Double.class);
        if (n instanceof Double) {
            return (Double) n;
        } else {
            return new Double(n.doubleValue());
        }
    }


    public static BigInteger toBigInteger(Object value)
            throws ConversionException {
        Number n = toNumber(value, BigInteger.class);
        if (n instanceof BigInteger) {
            return (BigInteger) n;
        } else {
            return BigInteger.valueOf(n.longValue());
        }
    }


    public static BigDecimal toBigDecimal(Object value)
            throws ConversionException {
        Number n = toNumber(value, BigDecimal.class);
        if (n instanceof BigDecimal) {
            return (BigDecimal) n;
        } else {
            return new BigDecimal(n.doubleValue());
        }
    }

    static Number toNumber(Object value, Class<?> targetClass)
            throws ConversionException {
        if (value instanceof Number) {
            return (Number) value;
        } else {
            String str = value.toString();
            if (str.startsWith(HEX_PREFIX)) {
                try {
                    return new BigInteger(str.substring(HEX_PREFIX.length()),
                            HEX_RADIX);
                } catch (NumberFormatException nex) {
                    throw new ConversionException("Could not convert " + str
                            + " to " + targetClass.getName()
                            + "! Invalid hex number.", nex);
                }
            }

            if (str.startsWith(BIN_PREFIX)) {
                try {
                    return new BigInteger(str.substring(BIN_PREFIX.length()),
                            BIN_RADIX);
                } catch (NumberFormatException nex) {
                    throw new ConversionException("Could not convert " + str
                            + " to " + targetClass.getName()
                            + "! Invalid binary number.", nex);
                }
            }

            try {
                Constructor<?> constr = targetClass.getConstructor(CONSTR_ARGS);
                return (Number) constr.newInstance(new Object[]{str});
            } catch (InvocationTargetException itex) {
                throw new ConversionException("Could not convert " + str
                        + " to " + targetClass.getName(),
                        itex.getTargetException());
            } catch (Exception ex) {
                // Treat all possible exceptions the same way
                throw new ConversionException(
                        "Conversion error when trying to convert " + str
                                + " to " + targetClass.getName(), ex);
            }
        }
    }


    public static URL toURL(Object value) throws ConversionException {
        if (value instanceof URL) {
            return (URL) value;
        } else if (value instanceof String) {
            try {
                return new URL((String) value);
            } catch (MalformedURLException e) {
                throw new ConversionException("The value " + value
                        + " can't be converted to an URL", e);
            }
        } else {
            throw new ConversionException("The value " + value
                    + " can't be converted to an URL");
        }
    }


    public static Locale toLocale(Object value) throws ConversionException {
        if (value instanceof Locale) {
            return (Locale) value;
        } else if (value instanceof String) {
            List<String> elements = split((String) value, '_');
            int size = elements.size();

            if (size >= 1
                    && ((elements.get(0)).length() == 2 || (elements.get(0))
                    .length() == 0)) {
                String language = elements.get(0);
                String country = (size >= 2) ? elements.get(1) : "";
                String variant = (size >= 3) ? elements.get(2) : "";

                return new Locale(language, country, variant);
            } else {
                throw new ConversionException("The value " + value
                        + " can't be converted to a Locale");
            }
        } else {
            throw new ConversionException("The value " + value
                    + " can't be converted to a Locale");
        }
    }


    public static List<String> split(String s, char delimiter, boolean trim) {
        if (s == null) {
            return new ArrayList<String>();
        }
        List<String> list = new ArrayList<String>();
        StringBuilder token = new StringBuilder();
        int begin = 0;
        boolean inEscape = false;

        while (begin < s.length()) {
            char c = s.charAt(begin);
            if (inEscape) {
                if (c != delimiter && c != LIST_ESC_CHAR) {
                    token.append(LIST_ESC_CHAR);
                }
                token.append(c);
                inEscape = false;
            } else {
                if (c == delimiter) {
                    // found a list delimiter -> add token and
                    // resetDefaultFileSystem buffer
                    String t = token.toString();
                    if (trim) {
                        t = t.trim();
                    }
                    list.add(t);
                    token = new StringBuilder();
                } else if (c == LIST_ESC_CHAR) {
                    // eventually escape next character
                    inEscape = true;
                } else {
                    token.append(c);
                }
            }

            begin++;
        }

        // Trailing delimiter?
        if (inEscape) {
            token.append(LIST_ESC_CHAR);
        }
        // Add last token
        String t = token.toString();
        if (trim) {
            t = t.trim();
        }
        list.add(t);

        return list;
    }


    public static List<String> split(String s, char delimiter) {
        return split(s, delimiter, true);
    }


    public static String escapeDelimiters(String s, char delimiter) {
        String s1 = StringUtils.replace(s, LIST_ESCAPE, LIST_ESCAPE + LIST_ESCAPE);
        return escapeListDelimiter(s1, delimiter);
    }

    public static String escapeListDelimiter(String s, char delimiter) {
        return StringUtils.replace(s, String.valueOf(delimiter), LIST_ESCAPE
                + delimiter);
    }

    public static Color toColor(Object value) throws ConversionException {
        if (value instanceof Color) {
            return (Color) value;
        } else if (value instanceof String
                && !StringUtils.hasText((String) value)) {
            String color = ((String) value).trim();

            int[] components = new int[3];

            int minlength = components.length * 2;
            if (color.length() < minlength) {
                throw new ConversionException("The value " + value
                        + " can't be converted to a Color");
            }

            if (color.startsWith("#")) {
                color = color.substring(1);
            }

            try {
                // parse the components
                for (int i = 0; i < components.length; i++) {
                    components[i] = Integer.parseInt(
                            color.substring(2 * i, 2 * i + 2), HEX_RADIX);
                }

                // parse the transparency
                int alpha;
                if (color.length() >= minlength + 2) {
                    alpha = Integer.parseInt(
                            color.substring(minlength, minlength + 2),
                            HEX_RADIX);
                } else {
                    alpha = Color.black.getAlpha();
                }

                return new Color(components[0], components[1], components[2],
                        alpha);
            } catch (Exception e) {
                throw new ConversionException("The value " + value
                        + " can't be converted to a Color", e);
            }
        } else {
            throw new ConversionException("The value " + value
                    + " can't be converted to a Color");
        }
    }


    static InetAddress toInetAddress(Object value) throws ConversionException {
        if (value instanceof InetAddress) {
            return (InetAddress) value;
        } else if (value instanceof String) {
            try {
                return InetAddress.getByName((String) value);
            } catch (UnknownHostException e) {
                throw new ConversionException("The value " + value
                        + " can't be converted to a InetAddress", e);
            }
        } else {
            throw new ConversionException("The value " + value
                    + " can't be converted to a InetAddress");
        }
    }

    static Object toInternetAddress(Object value) throws ConversionException {
        if (value.getClass().getName().equals(INTERNET_ADDRESS_CLASSNAME)) {
            return value;
        } else if (value instanceof String) {
            try {
                Constructor<?> ctor = Class.forName(INTERNET_ADDRESS_CLASSNAME)
                        .getConstructor(new Class[]{String.class});
                return ctor.newInstance(new Object[]{value});
            } catch (Exception e) {
                throw new ConversionException("The value " + value
                        + " can't be converted to a InternetAddress", e);
            }
        } else {
            throw new ConversionException("The value " + value
                    + " can't be converted to a InternetAddress");
        }
    }


    static boolean isEnum(Class<?> cls) {
        return cls.isEnum();
    }


    static <E extends Enum<E>> E toEnum(Object value, Class<E> cls)
            throws ConversionException {
        if (value.getClass().equals(cls)) {
            return cls.cast(value);
        } else if (value instanceof String) {
            try {
                return Enum.valueOf(cls, (String) value);
            } catch (Exception e) {
                throw new ConversionException("The value " + value
                        + " can't be converted to a " + cls.getName());
            }
        } else if (value instanceof Number) {
            try {
                E[] enumConstants = cls.getEnumConstants();
                return enumConstants[((Number) value).intValue()];
            } catch (Exception e) {
                throw new ConversionException("The value " + value
                        + " can't be converted to a " + cls.getName());
            }
        } else {
            throw new ConversionException("The value " + value
                    + " can't be converted to a " + cls.getName());
        }
    }


    public static Date toDate(Object value) throws ConversionException {
        return toDate(value, DATA_FORMAT);
    }

    public static Date toDate(Object value, String format)
            throws ConversionException {
        if (value instanceof Date) {
            return (Date) value;
        } else if (value instanceof Calendar) {
            return ((Calendar) value).getTime();
        } else if (value instanceof String) {
            try {
                return new SimpleDateFormat(format).parse((String) value);
            } catch (ParseException e) {
                throw new ConversionException("The value " + value
                        + " can't be converted to a Date", e);
            }
        } else {
            throw new ConversionException("The value " + value
                    + " can't be converted to a Date");
        }
    }

    public static Calendar toCalendar(Object value, String format)
            throws ConversionException {
        if (value instanceof Calendar) {
            return (Calendar) value;
        } else if (value instanceof Date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((Date) value);
            return calendar;
        } else if (value instanceof String) {
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new SimpleDateFormat(format)
                        .parse((String) value));
                return calendar;
            } catch (ParseException e) {
                throw new ConversionException("The value " + value
                        + " can't be converted to a Calendar", e);
            }
        } else {
            throw new ConversionException("The value " + value
                    + " can't be converted to a Calendar");
        }
    }


    public static Iterator<?> toIterator(Object value, char delimiter) {
        return flatten(value, delimiter).iterator();
    }

    private static Collection<?> flatten(Object value, char delimiter) {
        if (value instanceof String) {
            String s = (String) value;
            if (s.indexOf(delimiter) > 0) {
                return split(s, delimiter);
            }
        }

        Collection<Object> result = new LinkedList<Object>();
        if (value instanceof Iterable) {
            flattenIterator(result, ((Iterable<?>) value).iterator(), delimiter);
        } else if (value instanceof Iterator) {
            flattenIterator(result, (Iterator<?>) value, delimiter);
        } else if (value != null) {
            if (value.getClass().isArray()) {
                for (int len = Array.getLength(value), idx = 0; idx < len; idx++) {
                    result.addAll(flatten(Array.get(value, idx), delimiter));
                }
            } else {
                result.add(value);
            }
        }

        return result;
    }


    private static void flattenIterator(Collection<Object> target,
                                        Iterator<?> it, char delimiter) {
        while (it.hasNext()) {
            target.addAll(flatten(it.next(), delimiter));
        }
    }


    @SuppressWarnings("unchecked")
    private static Object convertToEnum(Class<?> enumClass, Object value) throws ConversionException {
        return toEnum(value, enumClass.asSubclass(Enum.class));
    }

    public static void main(String args[]) {
        try {
            //int temp = ConverUtils.toInteger("50");
            //System.out.println(ConverUtils.toInteger("50"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
