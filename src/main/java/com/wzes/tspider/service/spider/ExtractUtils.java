package com.wzes.tspider.service.spider;

import com.wzes.tspider.module.spider.*;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Create by xuantang
 * @date on 2/3/18
 */
public class ExtractUtils {

    /**
     * 根据一个页面爬取 item 返回结果 item
     * @param html
     * @param extractItem
     * @return item
     */
    public static Result.Item getContent(String url, String html, ExtractItem extractItem) {
        Result.Item item = new Result().new Item();
        ExtractType extractType = extractItem.getExtractType();
        List<String> XPaths = extractItem.getXpaths();
        String name = extractItem.getName();
        // 获取结果
        List<String> values = getValuesFromPage(url, html, extractType, XPaths);

        item.setSize(values.size());
        item.setName(name);
        item.setExtractType(extractType);
        item.setValues(values);
        return item;
    }


    /**
     * 爬取一个页面的对应的XPaths的内容并保存到values中
     * @param html
     * @param extractType
     * @param XPaths
     */
    public static List<String> getValuesFromPage(String url, String html, ExtractType extractType, List<String> XPaths) {
        List<String> values = new ArrayList<>();
        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode tagNode = cleaner.clean(html);
        Document dom = null;
        try {
            dom = new DomSerializer(new CleanerProperties()).createDOM(tagNode);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        XPath xPath = XPathFactory.newInstance().newXPath();
        for (String XPath : XPaths) {
            try {
                Object result = xPath.evaluate(XPath, dom, XPathConstants.NODESET);
                String content = null;
                if (result instanceof NodeList) {
                    NodeList nodeList = (NodeList) result;
                    if (nodeList.getLength() > 0) {
                        Node node = nodeList.item(0);
                        switch (extractType) {
                            case EXTRACT_TEXT:
                                content = node.getTextContent().trim();
                                break;
                            case EXTRACT_LINK:
                                content = UrlUtils.getAbsUrl(url, node.getAttributes()
                                        .getNamedItem("href").getNodeValue());
                                break;
                            case EXTRACT_IMAGE:
                                content = UrlUtils.getAbsUrl(url, node.getAttributes()
                                        .getNamedItem("src").getNodeValue());
                                break;
                            case EXTRACT_FILE:
                                content = UrlUtils.getAbsUrl(url, node.getAttributes()
                                        .getNamedItem("src").getNodeValue());
                                break;
                            default:
                                values.add("-");
                                break;
                        }
                    }
                }
                if (content != null && !content.isEmpty()) {
                    values.add(content);
                } else {
                    values.add("-");
                }
            } catch (Exception e) {
                values.add("-");
            }
        }
        return values;
    }
}
