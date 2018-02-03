package com.wzes.tspider.service.spider;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.wzes.tspider.module.spider.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Create by xuantang
 * @date on 2/3/18
 */
public class Spider {

    /**
     * 获取普通的 WebClient
     * @return 普通的 WebClient
     */
    public static WebClient getCommonSpider() {
        // new webclient and initialize configure
        return new WebClient(BrowserVersion.CHROME);
    }

    /**
     * 获取配置的 WebClient
     * @param config 配置文件
     * @return 配置的 WebClient
     */
    public static WebClient getCommonSpider(Config config) {
        WebClient webClient;
        // 设置 proxy
        if (config.getProxies() != null && config.getProxies().size() > 0) {
            Proxy proxy = config.getProxies().get(0);
            webClient = new WebClient(BrowserVersion.CHROME, proxy.getServer(), proxy.getPort());

            // 设置 cookie
            if (config.getCookies() != null && config.getCookies().size() > 0) {
                List<String> cookies = config.getCookies();
                webClient.addRequestHeader("cookie", cookies.get(0));
            }
            // 设置 headers
            if (config.getHeaders() != null && config.getHeaders().size() > 0) {
                for (Header header : config.getHeaders()) {
                    webClient.addRequestHeader(header.getName(), header.getValue());
                }
            }
            // 设置 timeout
            if (config.getTimeout() != 0) {
                webClient.getOptions().setTimeout(config.getTimeout());
            }
            //
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setJavaScriptEnabled(false);
            return webClient;
        }
        //
        webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setTimeout(5000);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        return new WebClient();
    }

    /**
     * 根据 task 获取爬虫
     * @param task Task
     * @return WebClient
     */
    public static WebClient getSpider(final Task task) {
        if (task.getConfig() == null) {
            return getCommonSpider();
        }
        return getCommonSpider(task.getConfig());
    }

    /**
     *
     * @param page
     * @param extractItem
     * @return
     */
    public static Result.Item getContent(List<HtmlPage> pages, ExtractItem extractItem) {
        Result.Item item = new Result().new Item();
        ExtractType extractType = extractItem.getExtractType();
        List<String> xpaths = extractItem.getXpaths();
        String name = extractItem.getName();
        List<String> values = new ArrayList<>();
        for (HtmlPage htmlPage : pages) {
            getValueFromPage(htmlPage, extractType, xpaths, values);
        }

        item.setName(name);
        item.setExtractType(extractType);
        item.setValues(values);
        return item;
    }
    /**
     *
     * @param page
     * @param extractItem
     * @return
     */
    public static Result.Item getContent(HtmlPage page, ExtractItem extractItem) {
        Result.Item item = new Result().new Item();
        ExtractType extractType = extractItem.getExtractType();
        List<String> xpaths = extractItem.getXpaths();
        String name = extractItem.getName();
        List<String> values = new ArrayList<>();
        getValueFromPage(page, extractType, xpaths, values);
        item.setName(name);
        item.setExtractType(extractType);
        item.setValues(values);
        return item;
    }


    /**
     *
     * @param page
     * @param extractType
     * @param xpaths
     * @param values
     */
    private static void getValueFromPage(HtmlPage page, ExtractType extractType, List<String> xpaths, List<String> values) {
        for (int index = 0; index < xpaths.size(); index++) {
            List<Object> byXPath = page.getByXPath(xpaths.get(index));
            if (byXPath.size() > 0) {
                Object o = byXPath.get(0);
                switch (extractType) {
                    case EXTRACT_TEXT:
                        if( o instanceof HtmlTableDataCell) {
                            values.add(((HtmlTableDataCell) o).getTextContent().trim());
                        }else if( o instanceof HtmlAnchor) {
                            values.add(((HtmlAnchor) o).getTextContent().trim());
                        }else if( o instanceof HtmlArticle) {
                            values.add(((HtmlArticle) o).getTextContent().trim());
                        }else if( o instanceof HtmlLink) {
                            values.add(((HtmlLink) o).getTextContent().trim());
                        }else if( o instanceof HtmlSpan) {
                            values.add(((HtmlSpan) o).getTextContent().trim());
                        }
                        break;
                    case EXTRACT_LINK:
                        values.add(getAbsUrl(((HtmlAnchor) o).getBaseURI(),
                                ((HtmlAnchor) o).getHrefAttribute()));
                        break;

                    case EXTRACT_IMAGE:
                        values.add(getAbsUrl(((HtmlImage) o).getBaseURI(),
                                ((HtmlImage) o).getSrcAttribute()));
                        break;
                    default:
                        break;
                }
            }
        }
    }


    /**
     * 获取绝对链接
     * @param basePath the base url
     * @param relativePath the relative url
     * @return the absolute url
     */
    private static String getAbsUrl(String basePath, String relativePath){
        try {
            URL baseUrl = new URL(basePath);
            URL parseUrl = new URL(baseUrl ,relativePath);
            return parseUrl.toString();
        }
        catch (MalformedURLException e) {
            return "";
        }
    }

    /**
     * TODO 多线程爬取 边爬边写文件，最佳文件
     * 普通单个任务爬取
     * @param task task
     */
    public static void commonCrawl(Task task) {
        //
        List<ExtractRule> extractRules = task.getExtractRules();
        WebClient webClient = Spider.getCommonSpider(task.getConfig());
        // init
        Result[] results = new Result[extractRules.size()];
        for (int index = 0; index < results.length; index++) {
            results[index] = new Result();
        }
        //
        for (String url : task.getUrls()) {
            HtmlPage page = null;
            //
            for (int index = 0; index < extractRules.size(); index++) {
                ExtractRule extractRule = extractRules.get(index);
                try {
                    if (page == null) {
                        page = webClient.getPage(url);
                    }
                } catch (IOException e) {
                    // 异常
                    if (extractRule.getOnCrawlListener() != null) {
                        extractRule.getOnCrawlListener().onError(e.getCause());
                    }
                    break;
                }
                //
                for (int j = 0; j < extractRule.getExtractItems().size(); j++) {
                    ExtractItem extractItem = extractRule.getExtractItems().get(j);
                    // 爬取内容
                    Result.Item item = Spider.getContent(page, extractItem);
                    // 内容追加
                    if (results[index].getItems() == null
                            || results[index].getItems().size() < extractRule.getExtractItems().size()) {
                        results[index].addItem(item);
                    } else {
                        results[index].getItems().get(j).addValues(item.getValues());
                    }
                }
            }
        }
        // 爬取内容
        for (int index = 0; index < extractRules.size(); index++) {
            ExtractRule extractRule = extractRules.get(index);
            if (extractRule.getOnCrawlListener() != null) {
                extractRule.getOnCrawlListener().onNext(results[index]);
            }
            // 爬取结束
            if (extractRule.getOnCrawlListener() != null) {
                extractRule.getOnCrawlListener().onComplete();
            }
        }
    }
}
