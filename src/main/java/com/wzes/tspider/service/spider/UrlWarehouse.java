package com.wzes.tspider.service.spider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Create by xuantang
 * @date on 2/4/18
 */
public class UrlWarehouse {
    private List<String> urls;
    private int index;
    private static UrlWarehouse urlWarehouse;

    private UrlWarehouse() {

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

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    /**
     * 添加 url
     * @param url
     */
    public void addUrl(String url) {
        if (urls == null) {
            this.urls = new ArrayList<>();
        }
        urls.add(url);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void clear() {
        index = 0;
    }

    /**
     * 获取单个 url
     * @return url
     */
    public synchronized String getUrl() {
        if (urls != null) {
            if (index < urls.size()) {
                return urls.get(index++);
            } else {
                return "";
            }
        } else {
            throw new NullPointerException();
        }
    }
}
