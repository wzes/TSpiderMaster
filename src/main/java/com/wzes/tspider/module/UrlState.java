package com.wzes.tspider.module;

/**
 * @author Create by xuantang
 * @date on 2/12/18
 */
public class UrlState {
    private String url;
    private boolean state;
    private int code;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
