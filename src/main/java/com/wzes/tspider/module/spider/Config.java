package com.wzes.tspider.module.spider;

import java.util.List;

/**
 * @author Create by xuantang
 * @date on 1/29/18
 */
public class Config {
    /**
     * cookie集合
     */
    private List<String> cookies;
    /**
     * 头部集合
     */
    private List<String> headers;
    /**
     * 代理集合
     */
    private List<String> proxies;
    /**
     * 设置 timeout
     */
    private int timeout;
}
