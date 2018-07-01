package com.wzes.tspider;

import com.wzes.tspider.service.listener.WorkerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * @author Create by xuantang
 * @date on 7/1/18
 */
@Service
public class InitCommandLineRunner implements CommandLineRunner {

    @Autowired
    WorkerListener workerListener;

    @Override
    public void run(String... strings) {
        workerListener.startListener();
    }
}
