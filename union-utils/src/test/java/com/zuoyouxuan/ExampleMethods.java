package com.zuoyouxuan;

public class ExampleMethods {

    public boolean simpleMethod(@Col(name = "stringParam") String stringParam, @Col(name = "intParam") int intParam) {
        System.out.println("String: " + stringParam + ", integer: " + intParam);

        MethodParameterSpy.transformParameters(stringParam, intParam);
        return true;
    }
//
//    public int varArgsMethod(String... manyStrings) {
//        return manyStrings.length;
//    }
//
//    public boolean methodWithList(List<String> listParam) {
//        return listParam.isEmptyArray();
//    }
//
//
//
//    public <T> void genericMethod(T[] a, Collection<T> c) {
//        System.out.println("Length of array: " + a.length);
//        System.out.println("Size of collection: " + c.size());
//    }

}