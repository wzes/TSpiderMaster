package com.wzes.tspider.util;

import java.util.UUID;

/**
 * @author Create by xuantang
 * @date on 12/9/17
 */
public class IdUtils {
    /**
     * Get id
     * @return
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr = str.replace("-", "");
        return uuidStr;
    }
}
