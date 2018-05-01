package com.wzes.tspider.service.spider;

import com.alibaba.fastjson.JSON;
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
                Url[] spiderUrls = new Url[urls.size()];
                for (int index = 0; index < urls.size(); index++) {
                    Url url = new Url();
                    url.setUrl(urls.get(index));
                    url.setState(false);
                    spiderUrls[index] = url;
                }
                String data = JSON.toJSONString(spiderUrls);
                redisService.set(id, data);
                // TODO
                logger.info(data);
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
                // 设置 urls
                String data = redisService.get(id);
                List<Url> spiderUrl = JSON.parseArray(data, Url.class);
                Url urlTemp = new Url();
                urlTemp.setUrl(url);
                urlTemp.setState(false);
                spiderUrl.add(urlTemp);
                redisService.set(id, JSON.toJSONString(spiderUrl));
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
        try {
            if (redisService.lock(id)) {
                String data = redisService.get(id);
                // TODO
                List<Url> urls = JSON.parseArray(data, Url.class);
                for (int index = 0; index < urls.size(); index++) {
                    if (!urls.get(index).isState()) {
                        String url = urls.get(index).getUrl();
                        logger.info("GET URL : " + url);
                        urls.get(index).setState(true);
                        redisService.delete(id);
                        redisService.set(id, JSON.toJSONString(urls));
                        return url;
                    }
                }
                return "";
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "";
        } finally {
            redisService.unlock(id);
        }
        return "";
    }

    public float getProgress(String id) {
        try {
            String data = redisService.get(id);
            // TODO
            List<Url> urls = JSON.parseArray(data, Url.class);
            int total = urls.size();
            int crawled = 0;
            int success = 0;
            for (Url url : urls) {
                if (url.isState()) {
                    if (url.getCode() == 200) {
                        success++;
                    }
                    crawled++;
                }
            }
            return crawled / total;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
