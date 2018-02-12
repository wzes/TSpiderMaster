package com.wzes.tspider;

import com.wzes.tspider.service.redis.RedisService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Create by xuantang
 * @date on 2/7/18
 */
public class RedisLockTests {
    @Autowired
    RedisService redisService;

    @Test
    public void LockTest() {

        try {
            if (redisService.lock("test")) {
                //需要加锁的代码
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，
            // 因为可能客户端因为某个耗时的操作而挂起，
            //操作完的时候锁因为超时已经被别人获得，这时就不必解锁了。 ————这里没有做
            redisService.unlock("test");
        }
    }
}
