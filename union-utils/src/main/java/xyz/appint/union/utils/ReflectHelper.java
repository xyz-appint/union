package xyz.appint.union.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectHelper {
    public void getDeclaredFields(Object bean) {

    }

    public static Class<? extends Object> classForName(String name) throws ClassNotFoundException {
        try {
            ClassLoader classLoader = ClassLoaderHelper.getContextClassLoader();
            if (classLoader != null) {
                return classLoader.loadClass(name);
            }
        } catch (Throwable ignore) {
        }
        return Class.forName(name);
    }

    /**
     * 将值反射到Bean
     *
     * @param bean
     * @param fieldName
     * @param val
     */
    public static void setVal(Object bean, String fieldName, String val) {
        Class<? extends Object> beanClass = bean.getClass();
        Field[] fields = beanClass.getDeclaredFields();
        String methodName = "";
        if (fields != null) {
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (fieldName.equalsIgnoreCase(field.getName())) {
                    methodName = "set"
                            + Character.toTitleCase(field.getName().charAt(0))
                            + field.getName().substring(1);
                    try {
                        Method setMethod = beanClass.getMethod(methodName,
                                new Class[]{field.getType()});
                        Object[] obj;
                        if (field.getType().toString().equals("int")) {
                            obj = new Object[]{Integer.parseInt(val)};
                        } else {
                            obj = new Object[]{val};
                        }
                        setMethod.invoke(bean, obj);
                    } catch (Exception e) {
                        System.out.println(methodName + "==" + field.getType());
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    public static Object getVal(Object bean, String fieldName) {
        Class<? extends Object> beanClass = bean.getClass();
        Field[] fields = beanClass.getDeclaredFields();
        String methodName = "";
        Object val = "";
        if (fields != null) {
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (fieldName.equalsIgnoreCase(field.getName())) {
                    methodName = "get"
                            + Character.toTitleCase(field.getName().charAt(0))
                            + field.getName().substring(1);
                    try {
                        Method getMethod = beanClass.getMethod(methodName);
                        val = getMethod.invoke(bean, new Object[]{});
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
            return val;
        } else {
            return val;
        }
    }

    public static void main(String args[]) {
        try {
            System.out.println(ReflectHelper.classForName("com.zpuyu.utils.ReflectHelper").getName());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
