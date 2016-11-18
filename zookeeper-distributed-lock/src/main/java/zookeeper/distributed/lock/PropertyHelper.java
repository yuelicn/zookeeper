package zookeeper.distributed.lock;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyHelper {
    protected static final Logger logger = LoggerFactory.getLogger(PropertyHelper.class);
    // zookeeper 配置文件路劲 
    private static String ZOOKEEPER_PATH="/home/work/apps/conf/zookeeper.properties";
    
    private static Properties zookeeperConfig = null;

    static {
        if (zookeeperConfig == null) {
            loadZoopeeperConfig();
        }
    }
    /**
     * load zookeeperConfig 到内存中
     *  void
     * @author yue.li3 create to 2016年11月18日 下午2:19:21
     */
    private static void loadZoopeeperConfig() {
        try {
            InputStream inStream = new BufferedInputStream(new FileInputStream(new File(ZOOKEEPER_PATH)));
            zookeeperConfig = new Properties();
            zookeeperConfig.load(inStream);
        } catch (FileNotFoundException e) {
            logger.info("读取 zookeeper 配置信息异常 exception = {} ", e);
            // 可以异常进行特殊处理 - 也可以抛出一个自定义异常
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.info("读取 zookeeper 配置信息异常 exception = {} ", e);
            throw new RuntimeException(e);
        }
    }
    /**
     *  key ----> value
     * if value is null , return "";
     * @param key
     * @return String
     * @author yue.li3 create to 2016年11月18日 下午2:20:37
     */
    public static String getValue(String key) {
        return zookeeperConfig.getProperty(key) == null ? "" : zookeeperConfig.getProperty(key).trim();
    }
    /**
     * key --- > value 
     * if value is null , return defaultValue
     * @param key
     * @param defaultValue
     * @return String
     * @author yue.li3 create to 2016年11月18日 下午2:22:15
     */
    public static String getDefaultValue(String key,String defaultValue){
        String retrunKey = zookeeperConfig.getProperty(key) == null ? "" : zookeeperConfig.getProperty(key).trim();
        return StringUtils.isEmpty(retrunKey) ? defaultValue : retrunKey;
    }
    
    @SuppressWarnings("unused")
    private static String getPropertiesPath(){
        String path = PropertyHelper.class.getResource("/").getPath() + "property/zookeeper.properties"; 
        return path;
    }
}
