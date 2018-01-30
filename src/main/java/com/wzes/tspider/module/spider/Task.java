package com.wzes.tspider.module.spider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Create by xuantang
 * @date on 1/29/18
 */
public class Task {
    /**
     * 爬取的 url 集合
     */
    private List<String> urls;
    /**
     * 爬取规则
     */
    private List<ExtractRule> extractRules;
    /**
     * 页面配置
     */
    private Config config;
    /**
     * 页面类型
     */
    private PageType pageType;

    public List<String> getUrls() {
        return urls;
    }

    public List<ExtractRule> getExtractRules() {
        return extractRules;
    }

    public Config getConfig() {
        return config;
    }

    public PageType getPageType() {
        return pageType;
    }

    private Task() {

    }

    /**
     * 使用 build 模式构造
     * @param task
     */
    private Task(Task task) {
        this.urls = task.urls;
        this.config = task.config;
        this.extractRules = task.extractRules;
        this.pageType = task.pageType;
    }

    /**
     * Builder 模式
     */
    public static class Builder {
        private Task task;

        public Builder() {
            task = new Task();
        }

        /**
         * 配置多个页面地址
         * @param urls 爬取的页面地址集合
         * @return This Task
         */
        public Builder urls(List<String> urls) {
            task.urls = urls;
            return this;
        }

        /**
         * 配置单个页面地址
         * @param url 爬取的页面地址
         * @return This Task
         */
        public Builder url(String url) {
            if (task.urls == null) {
                task.urls = new ArrayList<>();
            }
            task.urls.add(url);
            return this;
        }

        /**
         * 配置页面类型
         * @param pageType 页面类型
         * @return This Task
         */
        public Builder type(PageType pageType) {
            task.pageType = pageType;
            return this;
        }

        /**
         * 配置 config
         * @param config 爬虫
         * @return This config
         */
        public Builder config(Config config) {
            task.config = config;
            return this;
        }
        /**
         * build 模式 , 创建 Task 对象
         * @return Task
         */
        public Task build() {
            return new Task(task);
        }

    }

}
