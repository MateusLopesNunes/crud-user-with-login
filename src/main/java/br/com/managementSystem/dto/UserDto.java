package br.com.managementSystem.dto;

import org.springframework.data.domain.Page;

import br.com.managementSystem.model.User;
import br.com.managementSystem.model.enumeration.Sex;

public class UserDto {

	private Long id;
	private String name;
	private String nickname;
	private String email;
	private String cpf;
	private String address;
	private String schooling;
	private String linkedin;
	private String facebook;
	private String github;
	private Sex sex;
	private String imageUrl;
	private String curriculumUrl;
	
	public UserDto(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.nickname = user.getNickname();
		this.email = user.getEmail();
		this.cpf = user.getCpf();
		this.address = user.getAddress();
		this.schooling = user.getSchooling();
		this.linkedin = user.getLinkedin();
		this.facebook = user.getFacebook();
		this.github = user.getGithub();
		this.sex = user.getSex();
		
		if (user.getPhoto() != null) {
			this.imageUrl = user.getPhoto().getImageUrl();
		}
		
		if (user.getCurriculum() != null) {
			this.curriculumUrl = user.getCurriculum().getFileUrl();
		}
	}
	
	
	
	public UserDto(Long id, String name, String nickname, String email, String cpf, String address, String schooling,
			String linkedin, String facebook, String github, Sex sex) {
		super();
		this.id = id;
		this.name = name;
		this.nickname = nickname;
		this.email = email;
		this.cpf = cpf;
		this.address = address;
		this.schooling = schooling;
		this.linkedin = linkedin;
		this.facebook = facebook;
		this.github = github;
		this.sex = sex;

	}

	public Long getId() {
		return id;
	}
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

	public String getPhotoUrl() {
		return imageUrl;
	}

	public String getCurriculumUrl() {
		return curriculumUrl;
	}

	public static Page<UserDto> modelToDto(Page<User> user) {
		return user.map(UserDto::new);
	}
}
