package com.siwoo.simpleweb;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class SimpleWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleWebApplication.class, args);
	}
	
	@Data
	private static class User {
		private int id;
		private String email;

		public User(int id, String email) {
			this.id = id;
			this.email = email;
		}
	}
	
	@Slf4j
	@RestController
	public static class OpenController {
		private static final Map<Integer, User> db;
		private int cnt;
		
		static {
			db = Stream.of(new User(1, "abc@kubia.com"),
					new User(2, "acb@kubia.com"),
					new User(3, "bac@kubia.com"),
					new User(4, "bca@kubia.com"))
					.collect(Collectors.toMap(User::getId, k -> k));
		}
		
		@GetMapping("/")
		public ResponseEntity<?> index(HttpServletRequest request) {
			log.info(String.format("Received request from %s.", request.getRemoteAddr()));
			return ResponseEntity.ok(
					String.format("You've hit %s\n",
							System.getenv("HOSTNAME")));
		}
		
		@GetMapping("/fail")
		public ResponseEntity<?> fail() {
			if (cnt++ >= 5)
				return ResponseEntity.badRequest().build();
			else
				return ResponseEntity.ok("ok");
		}
		
		@GetMapping("/user/get/ids")
		public ResponseEntity<?> getUser() {
			return ResponseEntity.ok(db.keySet());
		}
		
		@GetMapping("/user/{userId}")
		public ResponseEntity<?> getUser(@PathVariable int userId) {
			if (db.containsKey(userId))
				return ResponseEntity.ok(db.get(userId));
			else
				return ResponseEntity.notFound().build();
		}
	}
}
