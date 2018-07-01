package com.wzes.tspider.service.listener;

import com.wzes.tspider.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author Create by xuantang
 * @date on 7/1/18
 */
@Service
public class WorkerListener {
    @Value("${workers.key}")
    private String workerKey;

    @Autowired
    RedisService redisService;

    public void startListener() {
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        ScheduledExecutorService scheduledExecutorService =
                new ScheduledThreadPoolExecutor(1, threadFactory);
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            try {
                if (redisService.lock(workerKey)) {
                    Set<String> workers = redisService.slist(workerKey);
                    List<String> actives = new ArrayList<>();
                    for (String worker1 : workers) {
                        String[] worker = worker1.split(":");
                        String host = worker[0];
                        int port = Integer.parseInt(worker[1]);
                        if (isConnected(host, port)) {
                            actives.add(worker1);
                        }
                    }
                    actives.forEach(a -> redisService.sadd(workerKey, a));
                    redisService.unlock(workerKey);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 0, 5000, TimeUnit.MILLISECONDS);
    }

    private boolean isConnected(String host, int port) {
        Socket socket = new Socket();

        try {
            socket.connect(new InetSocketAddress(host, port), 2000);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
