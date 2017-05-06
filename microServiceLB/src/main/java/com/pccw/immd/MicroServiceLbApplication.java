package com.pccw.immd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MicroServiceLbApplication {
	@Autowired
	DiscoveryClient client;

	@RequestMapping(value = "/greeting")
	public String greet() {
		ServiceInstance localInstance = client.getLocalServiceInstance();
		return " FROM S2 " + localInstance.getServiceId()+":"+localInstance.getHost()+":"+localInstance.getPort();
	}

	public static void main(String[] args) {
		SpringApplication.run(MicroServiceLbApplication.class, args);
	}
}
