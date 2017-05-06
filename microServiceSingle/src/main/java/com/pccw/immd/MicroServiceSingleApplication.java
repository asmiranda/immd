package com.pccw.immd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MicroServiceSingleApplication {
	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	@Autowired
	RestTemplate restTemplate;

	@RequestMapping("/greeting")
	public String hi() {
		return "FROM S1";
	}

	@RequestMapping("/greetingS1S2")
	public String hiS1S2() {
		String greeting = this.restTemplate.getForObject("http://lb-service/greeting", String.class);
		return "FROM S1 THEN "+greeting;
	}

	public static void main(String[] args) {
		SpringApplication.run(MicroServiceSingleApplication.class, args);
	}
}
