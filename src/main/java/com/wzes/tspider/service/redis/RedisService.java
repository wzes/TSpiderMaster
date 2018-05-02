package com.wzes.tspider.service.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    private int expireMillis = 20 * 1000;

    private int timeoutMillis = 10 * 1000;

    private volatile boolean locked = false;


    public void set(final String key, final String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            logger.error("set redis error, key : {}", key);
        }
    }

    public String get(final String key) {
        Object obj = null;
        try {
            obj = redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            logger.error("get redis error, key : {}", key);
        }
        return obj != null ? obj.toString() : null;
    }

    public void lpush(String key, String value) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
        } catch (Exception e) {
            logger.error("get redis error, key : {}", key);
        }
    }

    public void lpushAll(String key, List<String> values) {
        try {
            redisTemplate.opsForList().leftPushAll(key, values);
        } catch (Exception e) {
            logger.error("get redis error, key : {}", key);
        }
    }

    public List<String> lrange(String key, int size) {
        List<String> respockets = new ArrayList<>();
        try {
            respockets = redisTemplate.opsForList().range(key, 0, size);
        } catch (Exception e) {
            logger.error("get redis error, key : {}", key);
        }
        return respockets;
    }

    public List<String> lrange(String key) {
        List<String> respockets = new ArrayList<>();
        try {
            respockets = redisTemplate.opsForList().range(key, 0, lsize(key));
        } catch (Exception e) {
            logger.error("get redis error, key : {}", key);
        }
        return respockets;
    }

    public String lpop(String key) {
        Object obj = null;
        try {
            obj = redisTemplate.opsForList().rightPop(key);
        } catch (Exception e) {
            logger.error("get redis error, key : {}", key);
        }
        return obj != null ? obj.toString() : null;
    }

    public Long lsize(String key) {
        Object obj = null;
        try {
            obj = redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            logger.error("get redis error, key : {}", key);
        }
        return obj != null ? Long.valueOf(obj.toString()) : 0;
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
            obj = redisTemplate.opsForValue().setIfAbsent(key, value);
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
            obj = redisTemplate.opsForValue().getAndSet(key, value);
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
            long expires = System.currentTimeMillis() + expireMillis + 1;
            String expiresStr = String.valueOf(expires);
            if (this.setNX(key, expiresStr)) {
                // lock acquired
                locked = true;
                return true;
            }

            String currentValueStr = this.get(key);
            if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
                // lock is expired
                String oldValueStr = this.getSet(key, expiresStr);
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
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
