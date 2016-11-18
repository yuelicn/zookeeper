package zookeeper.distributed.lock;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreV2;
import org.apache.curator.framework.recipes.locks.Lease;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 获取zookeeper 分布式锁
 * @author yue.li3
 * @date 2016年11月18日 下午2:42:13
 */
public class Lock {
    protected static final Logger logger = LoggerFactory.getLogger(Lock.class);

    /**
     * 根据名称获取锁
     * 1： 此方法没有设置锁的强制过期时间
     * 2： 调用此方法客服端必须手动释放锁
     * @param LockName
     * @return Lease
     * @author yue.li3 create to 2016年11月18日 下午2:44:14
     */
    public static Lease getLock(String LockName){
        InterProcessSemaphoreV2 process = ZookeeperLock.getSharedSemaphore(LockName);
        Lease lock=null ;
        try {
            lock = process.acquire();
        } catch (Exception e) {
            logger.error("获取锁异常 exception = {}" , e);
            throw new RuntimeException("获取锁异常", e);
        }
        return lock;
    }
    
    /**
     * 根据名称获取锁
     * 1： 此方法没有设置锁的最大持有时间
     * 2： 调用此方法传入锁的做大持有时间 单位 秒
     * @param LockName
     * @return Lease
     * @author yue.li3 create to 2016年11月18日 下午2:44:14
     */
    public static Lease getLock(String LockName , long time){
        InterProcessSemaphoreV2 process = ZookeeperLock.getSharedSemaphore(LockName);
        Lease lock=null ;
        try {
            lock = process.acquire(time, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("获取锁异常 exception = {}" , e);
            throw new RuntimeException("获取锁异常", e);
        }
        return lock;
    }
    /**
     * 释放锁 - 建议放到finally 中执行
     * @param lock void
     * @author yue.li3 create to 2016年11月18日 下午2:51:35
     */
    public static void unLock(Lease lock){
        try {
            lock.close();
        } catch (IOException e) {
            logger.error("释放锁异常 exception = {}" , e);
            throw new RuntimeException("释放锁异常", e);
        }
    }

}
