package com.siwoo.extweb;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ExtWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExtWebApplication.class, args);
	}

	@Bean
	public OkHttpClient okHttpClient() {
		return new OkHttpClient().newBuilder().build();
	}
	
	@RestController
	public static class UserController {
		
		@Autowired
		private OkHttpClient httpClient;
		
		private static final String PLATFORM_URL = "http://kubia:80";
		
		@GetMapping("/users")
		public ResponseEntity<?> users() throws IOException {
			String body = httpClient.newCall(new Request.Builder().url(
					String.format("%s/user/get/ids", PLATFORM_URL)
			).build()).execute().body().string();
			List<String> ids = new ArrayList<>();
			for (String id: body.substring(1, body.length()-1).split(","))
				ids.add(id);
			StringBuilder sb = new StringBuilder();
			for (int i=0; i<ids.size(); i++) {
				sb.append(i == 0 ? "" : ", ")
					.append(httpClient.newCall(new Request.Builder().url(
						String.format("%s/user/%s", PLATFORM_URL, ids.get(i))).build())
						.execute().body().string());
			}
			return ResponseEntity.ok(sb.toString());
		}
	}
}
