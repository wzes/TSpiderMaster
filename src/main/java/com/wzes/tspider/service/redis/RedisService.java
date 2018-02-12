package com.wzes.tspider.service.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

/**
 * @author Create by xuantang
 * @date on 2/8/18
 */
@Service
public class RedisService {
    private static Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    private static final int DEFAULT_ACQUIRE_RESOLUTION_MILLIS = 100;


    public int getExpireMsecs() {
        return expireMsecs;
    }

    public void setExpireMsecs(int expireMsecs) {
        this.expireMsecs = expireMsecs;
    }

    public int getTimeoutMillis() {
        return timeoutMillis;
    }

    public void setTimeoutMillis(int timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }

    /**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
    private int expireMsecs = 20 * 1000;

    /**
     * 锁等待时间，防止线程饥饿
     */
    private int timeoutMillis = 10 * 1000;

    private volatile boolean locked = false;


    public void set(final String key, final String value) {
        Object obj = null;
        try {
            obj = redisTemplate.execute((RedisCallback<Object>) connection -> {
                StringRedisSerializer serializer = new StringRedisSerializer();
                connection.set(serializer.serialize(key), serializer.serialize(value));
                connection.close();
                return null;
            });
        } catch (Exception e) {
            logger.error("set redis error, key : {}", key);
        }
    }

    public String get(final String key) {
        Object obj = null;
        try {
            obj = redisTemplate.execute((RedisCallback<Object>) connection -> {
                StringRedisSerializer serializer = new StringRedisSerializer();
                byte[] data = connection.get(serializer.serialize(key));
                connection.close();
                if (data == null) {
                    return null;
                }
                return serializer.deserialize(data);
            });
        } catch (Exception e) {
            logger.error("get redis error, key : {}", key);
        }
        return obj != null ? obj.toString() : null;
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setNX(final String key, final String value) {
        Object obj = null;
        try {
            obj = redisTemplate.execute((RedisCallback<Object>) connection -> {
                StringRedisSerializer serializer = new StringRedisSerializer();
                Boolean success = connection.setNX(serializer.serialize(key), serializer.serialize(value));
                connection.close();
                return success;
            });
        } catch (Exception e) {
            logger.error("setNX redis error, key : {}", key);
        }
        return obj != null ? (Boolean) obj : false;
    }

    /**
     * GETSET
     * @param key
     * @param value
     * @return
     */
    public String getSet(final String key, final String value) {
        Object obj = null;
        try {
            obj = redisTemplate.execute((RedisCallback<Object>) connection -> {
                StringRedisSerializer serializer = new StringRedisSerializer();
                byte[] ret = connection.getSet(serializer.serialize(key), serializer.serialize(value));
                connection.close();
                return serializer.deserialize(ret);
            });
        } catch (Exception e) {
            logger.error("setNX redis error, key : {}", key);
        }
        return obj != null ? (String) obj : null;
    }

    /**
     * 删除key
     * @param key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     *
     * @return
     * @throws InterruptedException
     */
    public synchronized boolean lock(String lockKey) throws InterruptedException {
        String key = "tspider" + lockKey;
        int timeout = timeoutMillis;
        while (timeout >= 0) {
            long expires = System.currentTimeMillis() + expireMsecs + 1;
            //锁到期时间
            String expiresStr = String.valueOf(expires);
            if (this.setNX(key, expiresStr)) {
                // lock acquired
                locked = true;
                return true;
            }

            //redis里的时间
            String currentValueStr = this.get(key);
            if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
                // lock is expired

                String oldValueStr = this.getSet(key, expiresStr);
                //只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    //防止误删（覆盖，因为key是相同的）了他人的锁——这里达不到效果，这里值会被覆盖，但是因为什么相差了很少的时间，所以可以接受

                    //[分布式的情况下]:如过这个时候，多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，他才有权利获取锁
                    // lock acquired
                    locked = true;
                    return true;
                }
            }
            timeout -= DEFAULT_ACQUIRE_RESOLUTION_MILLIS;

            Thread.sleep(DEFAULT_ACQUIRE_RESOLUTION_MILLIS);

        }
        return false;
    }


    /**
     * Acquired lock release.
     */
    public synchronized void unlock(String lockKey) {
        if (locked) {
            String key = "tspider" + lockKey;
            this.delete(key);
            locked = false;
        }
    }
}
