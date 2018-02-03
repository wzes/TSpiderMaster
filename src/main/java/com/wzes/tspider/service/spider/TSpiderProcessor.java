package com.wzes.tspider.service.spider;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.wzes.tspider.module.spider.*;
import com.wzes.tspider.service.store.PipeLine;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Create by xuantang
 * @date on 1/29/18
 */
public class TSpiderProcessor {

    private Logger logger = Logger.getLogger(TSpiderProcessor.class);

    /**
     * 任务集合
     */
    private List<Task> tasks;

    /**
     * 存储类
     */
    private PipeLine pipeLine;


    private TSpiderProcessor() {

    }

    private TSpiderProcessor(TSpiderProcessor tSpiderProcessor) {
        this.tasks = tSpiderProcessor.tasks;
        this.pipeLine = tSpiderProcessor.pipeLine;
    }

    public static class Builder {
        private TSpiderProcessor tSpiderProcessor;

        /**
         * 配置单个 task
         * @param task task
         * @return TSpiderProcessor
         */
        private Builder task(Task task) {
            if (tSpiderProcessor.tasks == null) {
                tSpiderProcessor.tasks = new ArrayList<>();
            }
            tSpiderProcessor.tasks.add(task);
            return this;
        }

        /**
         * 配置 task 集合
         * @param tasks tasks
         * @return Builder
         */
        private Builder tasks(List<Task> tasks) {
            tSpiderProcessor.tasks = tasks;
            return this;
        }

        /**
         * 配置存储
         * @param pipeLine pipeLine
         * @return Builder
         */
        private Builder pipeline(PipeLine pipeLine) {
            tSpiderProcessor.pipeLine = pipeLine;
            return this;
        }


        public Builder() {
            tSpiderProcessor = new TSpiderProcessor();
        }

        public TSpiderProcessor build() {
            return new TSpiderProcessor(tSpiderProcessor);
        }
    }


    /**
     * 启动爬虫
     */
    public void start() {
        // check
        if (tasks != null && tasks.size() > 0) {
            for (Task task : tasks) {
                PageType pageType = task.getPageType();
                switch (pageType) {
                    case PAGE_COMMON:
                        Spider.commonCrawl(task);
                        break;
                    case PAGE_AJAX:
                        break;
                    case PAGE_ROLL:
                        break;
                    default:
                        break;
                }
            }
        }

    }


}
