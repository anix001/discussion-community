package com.DiscussionCommunity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableAsync
@EnableWebMvc
public class DiscussionCommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscussionCommunityApplication.class, args);
	}

}
