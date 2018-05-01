package com.wzes.tspider;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author Create by xuantang
 * @date on 2/17/18
 */
public class SortTest {
    @Test
    public void SortTests() {
        final Counter counter = new Counter();
        for (int i = 0; i < 10000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    counter.inc();
                }
            }).start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(counter);
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

    class Counter {
        private volatile int count = 0;
        public void inc() {
            try {
                Thread.sleep(3);
                System.out.println(Thread.currentThread());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        }

        @Override
        public String toString() {
            return "[count=" + count + "]";
        }
    }

}
