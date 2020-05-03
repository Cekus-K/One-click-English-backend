package pl.cekus.oneclickenglish.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api")
public class BasicAuthController {

	@GetMapping("/basicauth")
	public AuthenticationBean helloWorldBean() {
		return new AuthenticationBean("You are authenticated");
	}

	private static class AuthenticationBean {
		private String message;

		AuthenticationBean(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}
}
