package br.com.managementSystem.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.br.CPF;

import br.com.managementSystem.model.User;
import br.com.managementSystem.model.enumeration.Sex;
import br.com.managementSystem.repository.UserRepository;

public class UserForm {

	@NotBlank
	private String name;
	
	@NotBlank
	private String nickname;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	@CPF
	private String cpf;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String address;
	
	@NotBlank
	private String schooling;
	
	@URL
	private String linkedin;
	
	@URL
	private String facebook;
	
	@URL
	private String github;
	
	private Sex sex;

	public String getName() {
		return name;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getSchooling() {
		return schooling;
	}
	
	public String getLinkedin() {
		return linkedin;
	}
	
	public String getFacebook() {
		return facebook;
	}
	
	public String getGithub() {
		return github;
	}
	
	public Sex getSex() {
		return sex;
	}
	
	public User dtoToModel() {
		return new User(name, nickname, email, cpf, password, address, schooling, 
						linkedin, facebook, github, sex);
	}

}
