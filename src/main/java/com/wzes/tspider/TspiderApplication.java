package com.wzes.tspider;

import com.wzes.tspider.service.redis.RedisLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author xuantang
 */
@SpringBootApplication
@EnableCaching
public class TspiderApplication {

	public static void main(String[] args) {
		SpringApplication.run(TspiderApplication.class, args);
	}
}
