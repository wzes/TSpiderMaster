package com.wzes.tspider.service.spider;

import com.alibaba.fastjson.JSON;
import com.wzes.tspider.module.Progress;
import com.wzes.tspider.service.redis.RedisService;
import com.wzes.tspider.util.SpringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * @author Create by xuantang
 * @date on 2/4/18
 */
public class UrlWarehouse {

    private static Logger logger = Logger.getLogger(UrlWarehouse.class);

    private static UrlWarehouse urlWarehouse;

    private ApplicationContext applicationContext;

    private RedisService redisService;

    private UrlWarehouse() {
        applicationContext = SpringUtils.getApplicationContext();
        redisService = applicationContext.getBean(RedisService.class);
    }

    /**
     * 单例模式
     *
     * @return
     */
    public static UrlWarehouse getInstance() {
        if (urlWarehouse == null) {
            synchronized (UrlWarehouse.class) {
                if (urlWarehouse == null) {
                    urlWarehouse = new UrlWarehouse();
                }
            }
        }
        return urlWarehouse;
    }


    public void setUrls(String id, List<String> urls) {
        try {
            if (redisService.lock(id)) {
                // 设置 urls
//                UrlState[] spiderUrls = new UrlState[urls.size()];
//                for (int index = 0; index < urls.size(); index++) {
//                    UrlState url = new UrlState();
//                    url.setUrl(urls.get(index));
//                    url.setState(false);
//                    spiderUrls[index] = url;
//                    redisService.lpushAll();
//                }
                redisService.lpushAll(id, urls);
//                String data = JSON.toJSONString(spiderUrls);
//                redisService.set(id, data);
                // TODO
                logger.info(JSON.toJSONString(urls));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            redisService.unlock(id);
        }
    }

    /**
     * 添加单个  url
     * @param url
     */
    public void addUrl(String id, String url) {
        try {
            if (redisService.lock(id)) {
                redisService.lpush(id, url);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            redisService.unlock(id);
        }
    }

    /**
     * 获取单个 url
     * @return url
     */
    public synchronized String getUrl(String id) {
        return redisService.lpop(id);
    }
}
