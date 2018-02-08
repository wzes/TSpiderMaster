package com.wzes.tspider.controller;

import com.wzes.tspider.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Create by xuantang
 * @date on 2/8/18
 */
@RestController
public class RedisTemplateController {

    @Autowired
    RedisService redisService;

    @GetMapping(value = "/redis")
    public String getValue(@RequestParam(value = "key") String key) {

        String _key = "hello";

        try {
            if(redisService.lock()) {
                return redisService.get(key);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，
            // 因为可能客户端因为某个耗时的操作而挂起，
            //操作完的时候锁因为超时已经被别人获得，这时就不必解锁了。 ————这里没有做
            redisService.unlock();
        }
        return "fail";
    }
}
