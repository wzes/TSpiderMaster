package com.wzes.tspider.module.spider;

import java.util.List;

/**
 * @author Create by xuantang
 * @date on 1/29/18
 *
 * 一个 ExtractRule 对应一个 Result
 */
public class Result {

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * 对应 ExtractItem
     */
    public class Item {
        /**
         * 字段名
         */
        private String name;
        /**
         * 结果集
         */
        private List<String> values;
    }
}
