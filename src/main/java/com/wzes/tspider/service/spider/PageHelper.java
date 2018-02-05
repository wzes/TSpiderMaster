package com.wzes.tspider.service.spider;

import com.wzes.tspider.module.spider.PageType;
import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * @author Create by xuantang
 * @date on 2/5/18
 *
 * 获取页面
 */
public class PageHelper {

    /**
     *
     * @param url
     * @return
     */
    public static String get(String url) {
        return get(PageType.PAGE_COMMON, url);
    }

    /**
     *
     * @param pageType
     * @param url
     * @return
     */
    public static String get(PageType pageType, String url) {
        String html = null;
        switch (pageType) {
            case PAGE_COMMON:
                try {
                    html = Jsoup.connect(url).get().outerHtml();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case PAGE_AJAX:
                break;
            case PAGE_ROLL:
                break;
            default:
                break;
        }
        return html;
    }
}
