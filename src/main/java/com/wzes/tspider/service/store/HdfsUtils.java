package com.wzes.tspider.service.store;

import com.wzes.tspider.module.spider.Result;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

/**
 * @author Create by xuantang
 * @date on 2/13/18
 */
public class HdfsUtils {

    private static final String dir = "hdfs://192.168.1.59:9000/tspider/";

    /**
     *
     * @param id
     * @return
     */
    public static String getFile(String id) {
        if (!exists(id)) {
            merge(id);
        }
        return getContent(id);
    }

    /**
     *
     * @param id
     * @return
     */
    public static String getContent(String id) {
        Configuration config = new Configuration();
        config.set("fs.hdfs.impl",
                org.apache.hadoop.hdfs.DistributedFileSystem.class.getName()
        );
        config.set("fs.file.impl",
                org.apache.hadoop.fs.LocalFileSystem.class.getName()
        );
        // get file system
        try {
            FileSystem fileSystem = FileSystem.get(URI.create(dir), config, "root");
            FSDataInputStream in = fileSystem.open(new Path(dir + id + "_merged/out.txt"));
            byte[] bytes = new byte[1024];
            StringBuilder sb = new StringBuilder();
            while (in.read(bytes) != -1) {
                String tmp = new String(bytes);
                sb.append(tmp);
            }
            in.close();
            return sb.toString();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean exists(String id) {
        // get config
        Configuration config = new Configuration();
        config.set("fs.hdfs.impl",
                org.apache.hadoop.hdfs.DistributedFileSystem.class.getName()
        );
        config.set("fs.file.impl",
                org.apache.hadoop.fs.LocalFileSystem.class.getName()
        );
        // get file system
        try {
            FileSystem fileSystem = FileSystem.get(URI.create(dir), config, "root");
            Path path = new Path(dir + id + "_merged/out.txt");
            return fileSystem.exists(path);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Merge files
     * @param dir
     */
    public static void merge(String id) {
        // get config
        Configuration config = new Configuration();
        config.set("fs.hdfs.impl",
                org.apache.hadoop.hdfs.DistributedFileSystem.class.getName()
        );
        config.set("fs.file.impl",
                org.apache.hadoop.fs.LocalFileSystem.class.getName()
        );
        // get file system
        try {
            FileSystem fileSystem = FileSystem.get(URI.create(dir), config, "root");
            FileUtil.copyMerge(fileSystem, new Path(dir + id), fileSystem,
                    new Path(dir + id + "_merged/out.txt"), true, config, null);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
