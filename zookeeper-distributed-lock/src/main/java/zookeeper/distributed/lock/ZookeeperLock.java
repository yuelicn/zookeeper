package zookeeper.distributed.lock;

import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreV2;

/**
 * zookeeper  分布式锁
 * Shared Reentrant Lock 可重入锁
 * Shared Lock  不可重入锁
 * @author yue.li3
 * @date 2016年11月18日 下午2:31:24
 */
public class ZookeeperLock {
    /**
     * 可重入锁
     * @param lockKey 需要加锁 key   
     * @param count 同时 允许 多少个实列 同时拿到锁
     * @return
     */
    public static InterProcessSemaphoreV2 getSharedSemaphore(String lockKey, int count){
        return new InterProcessSemaphoreV2(ZookeeperClient.getClient(), lockKey, count);
    }
    
    /**
     * 可重入锁， 实现单一锁 count is default 1
     * @param lockKey 需要加锁 key 
     * @return
     */
    public static InterProcessSemaphoreV2 getSharedSemaphore(String lockKey){
        return getSharedSemaphore(lockKey, 1);
    }
    
    /**
     * 不可重入锁Shared Lock 不能在同一个线程中重入
     * @param lockKey
     * @return
     */
    public static InterProcessMutex getInterProcessMutex(String lockKey){
        return new InterProcessMutex(ZookeeperClient.getClient(), lockKey);
    }
}
