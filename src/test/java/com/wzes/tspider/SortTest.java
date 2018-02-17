package com.wzes.tspider;

import org.junit.Test;

/**
 * @author Create by xuantang
 * @date on 2/17/18
 */
public class SortTest {
    @Test
    public void SortTests() {
        System.out.println(6 & 1);
        System.out.println(minRunLength(33));
    }

    private static int minRunLength(int n) {
        assert n >= 0;
        int r = 0;      // 只要不是 2的幂就会置 1
        while (n >= 32) {
            r |= (n & 1);
            n >>= 1;
        }
        return n + r;
    }
}
