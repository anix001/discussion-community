package com.DiscussionCommunity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DiscussionCommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscussionCommunityApplication.class, args);
	}

}
