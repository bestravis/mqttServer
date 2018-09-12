package com.youxuepai.mqtt.server;

import com.youxuepai.mqtt.server.broker.config.BrokerProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 通过SpringBoot启动服务
 */
@SpringBootApplication
public class BrokerApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(BrokerApplication.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args);
	}

	@Bean
	public BrokerProperties brokerProperties() {
		return new BrokerProperties();
	}

}
