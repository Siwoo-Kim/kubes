package com.siwoo.simpleweb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
public class SimpleWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleWebApplication.class, args);
	}
	
	@Slf4j
	@RestController
	public static class OpenController {
		
		private int cnt;
		
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
	}
}
