package com.happy.springboot.admin;

import com.happy.springboot.security.config.IgnoreUrlsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
/**
 *
 * @author Happy
 * @date 2019/11/22
 */
@SpringBootApplication
@ComponentScans(value = {@ComponentScan(value = "com.happy.springboot.*")})
@EnableConfigurationProperties(IgnoreUrlsConfig.class)
public class SpringbootAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAdminApplication.class, args);
	}

}
