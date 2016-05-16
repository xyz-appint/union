package xyz.appint.union.utils;

public class ClassLoaderHelper {
    public static ClassLoader overridenClassLoader = null;

    public static ClassLoader getContextClassLoader() {
        return overridenClassLoader != null ? overridenClassLoader : Thread
                .currentThread().getContextClassLoader();
    }
}
