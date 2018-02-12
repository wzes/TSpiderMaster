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

	public static void main(String[] args) {
		SpringApplication.run(TSpiderApplicationMaster.class, args);
	}
}
