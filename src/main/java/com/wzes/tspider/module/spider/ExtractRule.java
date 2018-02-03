package com.wzes.tspider.module.spider;

import com.wzes.tspider.service.listener.OnCrawlListener;

import java.util.List;

/**
 * @author Create by xuantang
 * @date on 1/29/18
 *
 * 一个规则对应一个文件
 */
public class ExtractRule {
    private List<ExtractItem> extractItems;
    private OnCrawlListener onCrawlListener;

    public List<ExtractItem> getExtractItems() {
        return extractItems;
    }

    public void setExtractItems(List<ExtractItem> extractItems) {
        this.extractItems = extractItems;
    }

    public OnCrawlListener getOnCrawlListener() {
        return onCrawlListener;
    }

    public void setOnCrawlListener(OnCrawlListener onCrawlListener) {
        this.onCrawlListener = onCrawlListener;
    }
}
