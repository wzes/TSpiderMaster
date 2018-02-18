package com.wzes.tspider.module.spider;

import java.util.List;

/**
 * @author Create by xuantang
 * @date on 1/29/18
 */
public class ExtractItem {
    /**
     * 字段名
     */
    private String name;
    /**
     * 选取位置集合
     */
    private List<String> xpaths;

    private String selector;

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    /**
     * 爬取的内容类型
     */
    private ExtractType extractType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getXpaths() {
        return xpaths;
    }

    public void setXpaths(List<String> xpaths) {
        this.xpaths = xpaths;
    }

    public ExtractType getExtractType() {
        return extractType;
    }

    public void setExtractType(ExtractType extractType) {
        this.extractType = extractType;
    }
}
