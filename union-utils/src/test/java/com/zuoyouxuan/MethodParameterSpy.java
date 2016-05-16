package com.zuoyouxuan;


import xyz.appint.union.utils.JsonUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;

public class MethodParameterSpy {

    private static final String fmt = "%24s: %s%n";

    // for the morbidly curious
    <E extends RuntimeException> void genericThrow() throws E {
    }

    public static void printClassConstructors(Class c) {
        Constructor[] allConstructors = c.getConstructors();
        out.format(fmt, "Number of constructors", allConstructors.length);
        for (Constructor currentConstructor : allConstructors) {
            printConstructor(currentConstructor);
        }
        Constructor[] allDeclConst = c.getDeclaredConstructors();
        out.format(fmt, "Number of declared constructors",
                allDeclConst.length);
        for (Constructor currentDeclConst : allDeclConst) {
            printConstructor(currentDeclConst);
        }
    }

    public static void printClassMethods(Class c) {
        Method[] allMethods = c.getDeclaredMethods();
        out.format(fmt, "Number of methods", allMethods.length);
        for (Method m : allMethods) {
            printMethod(m);
        }
    }

    public static void printConstructor(Constructor c) {
        out.format("%s%n", c.toGenericString());
        Parameter[] params = c.getParameters();
        out.format(fmt, "Number of parameters", params.length);
        for (int i = 0; i < params.length; i++) {
            printParameter(params[i]);
        }
    }

    public static void printMethod(Method m) {
        out.format("%s%n", m.toGenericString());
        out.format(fmt, "Return type", m.getReturnType());
        out.format(fmt, "Generic return type", m.getGenericReturnType());


        Parameter[] params = m.getParameters();
        for (int i = 0; i < params.length; i++) {
            printParameter(params[i]);
        }
    }

    public static Method getMethodByName(Class theClass, String name) {
        Method[] methods = theClass.getMethods();
        for (int n = 0; n < methods.length; ++n) {
            System.err.println(theClass.getName() + "===>" + methods[n].getName());
            if (name.equals(methods[n].getName()))
                return methods[n];
        }
        return null;
    }

    public static void transformParameters(Object... paramVal) {
        StackTraceElement stack[] = (new Throwable()).getStackTrace();
        try {
            Class currentClass = Class.forName(stack[1].getClassName());
            System.err.println(JsonUtils.toPrettyJSONString(stack));
            Method method = getMethodByName(currentClass, stack[1].getMethodName());
            Parameter[] params = method.getParameters();
            Map<String, Object> args = new HashMap<>();
            for (int i = 0; i < params.length; i++) {
                Parameter p = params[i];
                if (p.isAnnotationPresent(Col.class)) {
                    Col col = p.getAnnotation(Col.class);
                    args.put(col.name(), paramVal[i]);
                    System.err.println(col.name() + "：" + paramVal[i]);
                }
            }
            System.err.println(JsonUtils.toPrettyJSONString(args));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void printParameter(Parameter p) {

//        System.err.println(p.getName() + "====" + p.isAnnotationPresent(Col.class) + p.getAnnotation(Col.class));
        if (p.isAnnotationPresent(Col.class)) {
            Col col = p.getAnnotation(Col.class);
            System.err.println(col.name());
        }

        out.format(fmt, "Parameter class", p.getType());
        out.format(fmt, "Parameter name", p.getName());
        out.format(fmt, "Modifiers", p.getModifiers());
        out.format(fmt, "Is implicit?", p.isImplicit());
        out.format(fmt, "Is name present?", p.isNamePresent());
        out.format(fmt, "Is synthetic?", p.isSynthetic());
    }

    public static void main(String... args) {
        try {
//            printClassConstructors(TimestampUtil.class);
//            printClassMethods(ExampleMethods.class);

            ExampleMethods exampleMethods = new ExampleMethods();
            exampleMethods.simpleMethod("Hello", 123);
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}