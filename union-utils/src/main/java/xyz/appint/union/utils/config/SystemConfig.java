package xyz.appint.union.utils.config;

import org.apache.commons.configuration2.*;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.reloading.PeriodicReloadingTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * 通用配置类
 * Created by Justin on 2014/5/9.
 */
public class SystemConfig {
    private static final String CONFIG_FILE_NAME = "system_config.xml";
    private static Logger log = LoggerFactory.getLogger(SystemConfig.class);
    private static CompositeConfiguration config = null;

    static {
        if (config == null) {
            log.debug("system config init........");
            config = new CompositeConfiguration();
            config.addConfiguration(new SystemConfiguration());
        }
    }

    public static void loadSystemConfig(String profile) {
        try {
            String classPath = new File(SystemConfig.class.getResource("/").getFile()).getCanonicalPath();
            String configFilePath = classPath + File.separator + "profile" + File.separator + profile + File.separator + CONFIG_FILE_NAME;

            addXMLConfiguration(configFilePath);
//
//            Parameters params = new Parameters();
//            ReloadingFileBasedConfigurationBuilder<XMLConfiguration> builder =
//                    new ReloadingFileBasedConfigurationBuilder<>(XMLConfiguration.class)
//                            .configure(params.fileBased()
//                                    .setFile(new File(configFilePath)));
//            PeriodicReloadingTrigger trigger = new PeriodicReloadingTrigger(builder.getReloadingController(),  null, 1, TimeUnit.MINUTES);
//            trigger.start();
//
//            XMLConfiguration defaultConfiguration = builder.getConfiguration();
//            config.addConfiguration(defaultConfiguration);

        } catch (Exception e) {
            log.error("加载配置文件失败", e);
        }
    }

    // public static HierarchicalConfiguration configurationAt(String key) {
    // return new XMLConfiguration("tables.xml").configurationAt("tables.table(0)");
    // }

    /**
     * 添加属性文件配置
     *
     * @param propertyFilePath
     */
    public static void addPropertyConfiguration(String propertyFilePath) {
        try {
            Parameters params = new Parameters();
            ReloadingFileBasedConfigurationBuilder<PropertiesConfiguration> builder =
                    new ReloadingFileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
                            .configure(params.fileBased()
                                    .setFile(new File(propertyFilePath)));
            PeriodicReloadingTrigger trigger = new PeriodicReloadingTrigger(builder.getReloadingController(),  null, 1, TimeUnit.MINUTES);
            trigger.start();

            PropertiesConfiguration propertiesConfiguration = builder.getConfiguration();
//            propertiesConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());//Automatic　Reloading
            config.addConfiguration(propertiesConfiguration);
        } catch (ConfigurationException e) {
            log.error("add property configuration error", e);
        }
    }

    /**
     * 添加xml配置
     *
     * @param xmlFilePath
     */
    public static void addXMLConfiguration(String xmlFilePath) {
        try {
            Parameters params = new Parameters();
            ReloadingFileBasedConfigurationBuilder<XMLConfiguration> builder =
                    new ReloadingFileBasedConfigurationBuilder<>(XMLConfiguration.class)
                            .configure(params.fileBased()
                                    .setFile(new File(xmlFilePath)));
            PeriodicReloadingTrigger trigger = new PeriodicReloadingTrigger(builder.getReloadingController(),  null, 1, TimeUnit.MINUTES);
            trigger.start();

            XMLConfiguration xmlConfiguration = builder.getConfiguration();
//            xmlConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());//Automatic　Reloading
            //xmlConfiguration.setDefaultExpressionEngine(new XPathExpressionEngine());

            config.addConfiguration(xmlConfiguration);
        } catch (ConfigurationException e) {
            log.error("add property configuration error", e);
        }
    }

    /**
     * 读取子集
     *
     * @param prefix
     * @return
     */
    public static Configuration subset(String prefix) {
        return config.subset(prefix);
    }

    public static boolean isEmpty() {
        return config.isEmpty();
    }

    /**
     * 是否包含key
     *
     * @param key
     * @return
     */
    public static boolean containsKey(String key) {
        return config.containsKey(key);
    }

    /**
     * 添加属性
     *
     * @param key
     * @param value
     */
    public static void addProperty(String key, Object value) {
        config.addProperty(key, value);
    }

    /**
     * 设置属性
     *
     * @param key
     * @param value
     */
    public static void setProperty(String key, Object value) {
        config.setProperty(key, value);
    }

    /**
     * 清除属性
     *
     * @param key
     */
    public static void clearProperty(String key) {
        config.clearProperty(key);
    }

    /**
     * 清除所有配置属性
     */
    public static void clear() {
        config.clear();
    }


    public static Object getProperty(String key) {
        return config.getProperties(key);
    }

    /**
     * 根据前辍读取配置属性Key
     *
     * @param prefix
     * @return
     */
    public static Iterator<String> getKeys(String prefix) {
        return config.getKeys(prefix);
    }

    /**
     * 获取所有属性key
     *
     * @return
     */
    public static Iterator<String> getKeys() {
        return config.getKeys();
    }

    public static Properties getProperties(String key) {
        return config.getProperties(key);
    }

    public static boolean getBoolean(String key) {
        return config.getBoolean(key);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return config.getBoolean(key, defaultValue);
    }

    public static Boolean getBoolean(String key, Boolean defaultValue) {
        return config.getBoolean(key, defaultValue);
    }

    public static byte getByte(String key) {
        return config.getByte(key);
    }

    public static byte getByte(String key, byte defaultValue) {
        return config.getByte(key, defaultValue);
    }

    public static Byte getByte(String key, Byte defaultValue) {
        return config.getByte(key, defaultValue);
    }

    public static double getDouble(String key) {
        return config.getDouble(key);
    }

    public static double getDouble(String key, double defaultValue) {
        return config.getDouble(key, defaultValue);
    }

    public static Double getDouble(String key, Double defaultValue) {
        return config.getDouble(key, defaultValue);
    }

    public static float getFloat(String key) {
        return config.getFloat(key);
    }

    public static float getFloat(String key, float defaultValue) {
        return config.getFloat(key, defaultValue);
    }

    public static Float getFloat(String key, Float defaultValue) {
        return config.getFloat(key, defaultValue);
    }

    public static int getInt(String key) {
        return config.getInt(key);
    }

    public static int getInt(String key, int defaultValue) {
        return config.getInt(key, defaultValue);
    }

    public static Integer getInteger(String key, Integer defaultValue) {
        return config.getInteger(key, defaultValue);
    }

    public static long getLong(String key) {
        return config.getLong(key);
    }

    public static long getLong(String key, long defaultValue) {
        return config.getLong(key, defaultValue);
    }

    public static Long getLong(String key, Long defaultValue) {
        return config.getLong(key, defaultValue);
    }

    public static short getShort(String key) {
        return config.getShort(key);
    }

    public static short getShort(String key, short defaultValue) {
        return config.getShort(key, defaultValue);
    }

    public static Short getShort(String key, Short defaultValue) {
        return config.getShort(key, defaultValue);
    }

    public static BigDecimal getBigDecimal(String key) {
        return config.getBigDecimal(key);
    }

    public static BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
        return config.getBigDecimal(key, defaultValue);
    }

    public static BigDecimal getBigInteger(String key) {
        return config.getBigDecimal(key);
    }

    public static BigInteger getBigInteger(String key, BigInteger defaultValue) {
        return config.getBigInteger(key, defaultValue);
    }

    public static String getString(String key) {
        return config.getString(key);
    }

    public static String getString(String key, String defaultValue) {
        return config.getString(key, defaultValue);
    }

    public static String[] getStringArray(String key) {
        return config.getStringArray(key);
    }

    public static List<Object> getList(String key) {
        return config.getList(key);
    }

    public static List<Object> getList(String key, List<?> defaultValue) {
        return config.getList(key, defaultValue);
    }
}
