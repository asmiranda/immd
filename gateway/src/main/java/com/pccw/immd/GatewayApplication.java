package com.pccw.immd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
@EnableResourceServer
@RestController
@EnableDiscoveryClient
@EnableFeignClients
public class GatewayApplication extends ResourceServerConfigurerAdapter {
	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	@Autowired
	RestTemplate restTemplate;

	final List<Message> messages = Collections.synchronizedList(new LinkedList<>());

	@RequestMapping("/")
	public Message home() {
		Message m = new Message();
		m.greetingFromS1 = "Hello World";
		return m;
	}

	@RequestMapping(path = "api/messages", method = RequestMethod.GET)
	List<Message> getMessages(Principal principal) {
		return messages;
	}

	@RequestMapping(path = "api/messages", method = RequestMethod.POST)
	Message postMessage(Principal principal, @RequestBody Message message) {
		String greetingS1 = this.restTemplate.getForObject("http://single-service/greeting", String.class);
		String greetingS2 = this.restTemplate.getForObject("http://lb-service/greeting", String.class);
		String greetingS1ThenS2 = this.restTemplate.getForObject("http://single-service/greetingS1S2", String.class);

		message.username = principal.getName();
		message.createdAt = LocalDateTime.now();
		message.greetingFromS1 = greetingS1;
		message.greetingFromS2 = greetingS2;
		message.greetingFromS1ThenS2 = greetingS1ThenS2;
		messages.add(0, message);
		return message;
	}

	public static class Message {
		public String text;
		public String username;
		public LocalDateTime createdAt;
		public String greetingFromS1;
		public String greetingFromS2;
		public String greetingFromS1ThenS2;
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/api/**").access("#oauth2.hasScope('read')")
				.antMatchers(HttpMethod.POST, "/api/**").access("#oauth2.hasScope('write')");
	}

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
//
//	@Profile("!cloud")
//	@Bean
//	RequestDumperFilter requestDumperFilter() {
//		return new RequestDumperFilter();
//	}
}
