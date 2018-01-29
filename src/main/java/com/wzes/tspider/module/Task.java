package com.wzes.tspider.module;

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
}
