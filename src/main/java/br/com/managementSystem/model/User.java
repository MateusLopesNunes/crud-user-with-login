package br.com.managementSystem.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.managementSystem.model.enumeration.Sex;

@Entity
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String nickname;
	private String email;
	private String cpf;
	private String password;
	private String address;
	private String schooling;
	private String linkedin;
	private String facebook;
	private String github;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Profile> profiles = new ArrayList<>();
	
	@Enumerated(EnumType.STRING)
	private Sex sex;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	private AmazonImage photo;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	private AmazonFile curriculum;

	public User() {
	}

	public User(String name, String nickname, String email, String cpf, String password, String address,
			String schooling, String linkedin, String facebook, String github, Sex sexo) {
		this.name = name;
		this.nickname = nickname;
		this.email = email;
		this.cpf = cpf;
		this.password = password;
		this.address = address;
		this.schooling = schooling;
		this.linkedin = linkedin;
		this.facebook = facebook;
		this.github = github;
		this.sex = sexo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPass() {
		return password;
	}

	public void setPass(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSchooling() {
		return schooling;
	}

	public void setSchooling(String schooling) {
		this.schooling = schooling;
	}

	public String getLinkedin() {
		return linkedin;
	}

	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getGithub() {
		return github;
	}

	public void setGithub(String github) {
		this.github = github;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sexo) {
		this.sex = sexo;
	}

	public AmazonImage getPhoto() {
		return photo;
	}

	public void setPhoto(AmazonImage photo) {
		this.photo = photo;
	}

	public AmazonFile getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(AmazonFile curriculo) {
		this.curriculum = curriculo;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", nickname=" + nickname + ", email=" + email + ", cpf=" + cpf
				+ ", password=" + password + ", address=" + address + ", schooling=" + schooling + ", linkedin="
				+ linkedin + ", facebook=" + facebook + ", github=" + github + ", sex=" + sex + ", photo=" + photo
				+ ", curriculum=" + curriculum + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.profiles;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
