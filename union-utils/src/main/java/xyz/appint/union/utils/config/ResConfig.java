package xyz.appint.union.utils.config;

/**
 * 资源配置
 * Created by Justin on 2014/9/10.
 */
public class ResConfig {
    private static String fastdfsServer = SystemConfig.getString("system.fastdfsServer");

    public static String getFastdfsServer() {
        return fastdfsServer;
    }

    /**
     * 获取并拼装资源完整路径
     *
     * @param imagePath
     * @return
     */
    public static String getRealPath(String imagePath) {
        if (imagePath != null && !imagePath.equals("")) {
            imagePath = fastdfsServer + imagePath;
        }
        return imagePath;
    }
}
