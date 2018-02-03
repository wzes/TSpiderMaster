package com.wzes.tspider.module.spider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Create by xuantang
 * @date on 1/29/18
 *
 * 一个 ExtractRule 对应一个 Result
 */
public class Result {
    /**
     * 结果
     */
    private List<Item> items;
    /**
     * 保存类型
     */
    private StorageType storageType;

    public StorageType getStorageType() {
        return storageType;
    }

    public void setStorageType(StorageType storageType) {
        this.storageType = storageType;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * 添加 item
     * @param item
     */
    public void addItem(Item item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
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

        /**
         * 存储类型
         */
        private ExtractType extractType;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getValues() {
            return values;
        }

        public void setValues(List<String> values) {
            this.values = values;
        }

        public ExtractType getExtractType() {
            return extractType;
        }

        public void setExtractType(ExtractType extractType) {
            this.extractType = extractType;
        }
    }
}
