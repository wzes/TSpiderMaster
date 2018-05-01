package com.wzes.tspider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
/**
 * @author xuantang
 */
@SpringBootApplication
@EnableCaching
public class TSpiderApplicationMaster {
//	private static CountDownLatch countDownLatch = new CountDownLatch(1);
//	public static void main(String[] args) throws InterruptedException {
//		SpringApplication.run(TSpiderApplicationMaster.class, args).registerShutdownHook();
//		countDownLatch.await();
//	}
	public static void main(String[] args) {
		SpringApplication.run(TSpiderApplicationMaster.class, args);
	}
}
