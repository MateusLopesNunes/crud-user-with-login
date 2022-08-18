package br.com.managementSystem.dto;

import br.com.managementSystem.model.User;
import br.com.managementSystem.model.enumeration.Sex;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserUpdate {

    @NotBlank
    private Long id;

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

    public User update(User user) {
        user.setId(id);
        user.setAddress(address);
        user.setCpf(cpf);
        user.setEmail(email);
        user.setFacebook(facebook);
        user.setGithub(github);
        user.setLinkedin(linkedin);
        user.setName(name);
        user.setNickname(nickname);
        user.setSchooling(schooling);
        user.setSex(sex);

        return user;
    }
}
