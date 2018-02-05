package com.wzes.tspider.service.spider;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Create by xuantang
 * @date on 2/5/18
 */
public class UrlUtils {
    /**
     * 获取绝对链接
     * @param basePath the base url
     * @param relativePath the relative url
     * @return the absolute url
     */
    public static String getAbsUrl(String basePath, String relativePath){
        try {
            URL baseUrl = new URL(basePath);
            URL parseUrl = new URL(baseUrl ,relativePath);
            return parseUrl.toString();
        }
        catch (MalformedURLException e) {
            return "";
        }
    }
}
