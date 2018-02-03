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
    private List<Header> headers;
    /**
     * 代理集合
     */
    private List<Proxy> proxies;

    public List<String> getCookies() {
        return cookies;
    }

    public void setCookies(List<String> cookies) {
        this.cookies = cookies;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public List<Proxy> getProxies() {
        return proxies;
    }

    public void setProxies(List<Proxy> proxies) {
        this.proxies = proxies;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * 设置 timeout
     */
    private int timeout;


}
