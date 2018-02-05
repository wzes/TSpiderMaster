package com.wzes.tspider.module.spider;

import com.wzes.tspider.service.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Create by xuantang
 * @date on 1/29/18
 *
 * 一个 ExtractRule 对应一个 Result
 */
public class Result {
    private static final String SPLIT_ITEM = Constant.RESULT_ITEM_SPLIT_SYMBOL;
    private static final String SPLIT_LINE = Constant.RESULT_LINE_SPLIT_SYMBOL;
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
         * 字段长度
         */
        int size;
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

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

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

        public void addValues(List<String> values) {
            if (this.values == null) {
                this.values = values;
            } else {
                this.values.addAll(values);
            }
        }
    }

    /**
     * 表格显示结果
     */
    public void show() {
        int size = items.size();
        String[] names = new String[items.size()];
        int max = 0;
        // 初始化
        for (int index = 0; index < size; index++) {
            names[index] = items.get(index).getName();
            if (max < items.get(index).getValues().size()) {
                max = items.get(index).getValues().size();
            }
        }
        // 输出标题
        for (int index = 0; index < size; index++ ) {
            if (index == size - 1) {
                System.out.print(names[index] + SPLIT_LINE);
            } else {
                System.out.print(names[index] + SPLIT_ITEM);
            }
        }
        // 输出内容
        for (int index = 0; index < max; index++ ) {
            for (int j = 0; j < items.size(); j++ ) {
                if (j == items.size() - 1) {
                    System.out.print(items.get(j).getValues().get(index) + SPLIT_LINE);
                } else {
                    System.out.print(items.get(j).getValues().get(index) + SPLIT_ITEM);
                }
            }
        }
    }
}
