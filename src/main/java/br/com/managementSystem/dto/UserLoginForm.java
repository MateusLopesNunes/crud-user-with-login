package br.com.managementSystem.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class UserLoginForm {
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	@Min(value = 8)
	private String password;
	
	public UserLoginForm() {
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}
