package com.pccw.immd;

import org.apache.catalina.filters.RequestDumperFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableOAuth2Sso
@EnableDiscoveryClient
@Controller
public class SampleUiApplication {

		@Autowired
		OAuth2RestTemplate restTemplate;
		@Value("${messages.url:http://localhost:7777}/api")
		String messagesUrl;

		@RequestMapping("/")
		String home(Model model) {
			List<Message> messages = Arrays.asList(restTemplate.getForObject(messagesUrl + "/messages", Message[].class));
			for (Message m:messages) {
				System.out.println("MESSAGE : "+m.greetingFromS1+":"+m.greetingFromS2+":"+m.greetingFromS1ThenS2);
			}
			model.addAttribute("messages", messages);
			return "index";
		}

		@RequestMapping(path = "messages", method = RequestMethod.POST)
		String postMessages(@RequestParam String text) {
			Message message = new Message();
			message.text = text;
			restTemplate.exchange(RequestEntity
					.post(UriComponentsBuilder.fromHttpUrl(messagesUrl).pathSegment("messages").build().toUri())
					.body(message), Message.class);
			return "redirect:/";
		}

	public static class Message {
		public String text;
		public String username;
		public LocalDateTime createdAt;
		public String greetingFromS1;
		public String greetingFromS2;
		public String greetingFromS1ThenS2;
	}


	public static void main(String[] args) {
		SpringApplication.run(SampleUiApplication.class, args);
	}

	@Bean
	OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext, OAuth2ProtectedResourceDetails details) {
		return new OAuth2RestTemplate(details, oauth2ClientContext);
	}
}