package com.wzes.tspider;

import com.wzes.tspider.service.store.HdfsUtils;
import org.junit.Test;

/**
 * @author Create by xuantang
 * @date on 2/14/18
 */
public class HdfsOperatorTest {
    @Test
    public void MergeTest() {
        HdfsUtils.merge("hdfs://192.168.1.59:9000/tspider/6c260106b1564dee86255bb8596cfb39_0");
    }

    @Test
    public void GetFileTest() {
        String file = HdfsUtils.getFile("9a1d39139f13468da50bbb4738b85a19_0");
        System.out.println(file);
    }
}
