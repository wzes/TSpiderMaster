package com.wzes.tspider;

import com.wzes.tspider.service.redis.RedisService;
import org.junit.Assert;
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
                Assert.assertTrue(true);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Assert.assertTrue(true);
        } finally {

            redisService.unlock("test");
        }
    }
}
