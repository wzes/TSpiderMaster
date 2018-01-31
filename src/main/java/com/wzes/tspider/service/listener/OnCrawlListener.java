package com.wzes.tspider.service.listener;

import com.wzes.tspider.module.spider.Result;

/**
 * @author Create by xuantang
 * @date on 1/31/18
 */
public interface OnCrawlListener {
    /**
     *
     */
    void onNext(Result result);

    /**
     *
     * @param e
     */
    void onError(Throwable e);

    /**
     *
     */
    void onComplete();
}