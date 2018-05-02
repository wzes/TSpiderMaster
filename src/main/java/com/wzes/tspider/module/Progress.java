package com.wzes.tspider.module;

/**
 * @author Create by xuantang
 * @date on 5/2/18
 */
public class Progress {
    private int total;
    private int crawled;
    private int success;

    public Progress() {
    }

    public Progress(int total, int crawled, int success) {
        this.total = total;
        this.crawled = crawled;
        this.success = success;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCrawled() {
        return crawled;
    }

    public void setCrawled(int crawled) {
        this.crawled = crawled;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
}
