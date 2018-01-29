package com.wzes.tspider.module.spider;

import java.util.List;

/**
 * @author Create by xuantang
 * @date on 1/29/18
 */
public class Item {
    /**
     * 字段名
     */
    private String name;
    /**
     * 选取位置集合
     */
    private List<String> xpaths;
    /**
     * 爬取的内容类型
     */
    private ExtractType extractType;
}
