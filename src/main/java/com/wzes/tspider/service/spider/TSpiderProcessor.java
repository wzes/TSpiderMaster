package com.wzes.tspider.service.spider;

import com.wzes.tspider.module.spider.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Create by xuantang
 * @date on 1/29/18
 */
public class TSpiderProcessor {
    private List<Task> tasks;


    /**
     * 配置单个 task
     * @param task task
     * @return TSpiderProcessor
     */
    private TSpiderProcessor task(Task task) {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        tasks.add(task);
        return this;
    }

    /**
     * 配置 task 集合
     * @param tasks tasks
     * @return TSpiderProcessor
     */
    private TSpiderProcessor tasks(List<Task> tasks) {
        this.tasks = tasks;
        return this;
    }

    /**
     * 启动爬虫
     */
    public void start() {

    }
}
