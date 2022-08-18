package br.com.managementSystem.service;

import br.com.managementSystem.dto.UserUpdateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.managementSystem.dto.UserDto;
import br.com.managementSystem.dto.UserForm;

import javax.mail.MessagingException;

public interface UserService {

	public UserDto create(UserForm form, UriComponentsBuilder builder, MultipartFile image, MultipartFile pdf);
	
	public Page<UserDto> findAll(Pageable pageable);
	
	public UserDto findById(Long id);
	
	public Page<UserDto> findByName(String name, Pageable pageable);
	
	public UserDto deleteById(Long id);
	
	public UserDto update(UserUpdateForm form, Long id, MultipartFile image, MultipartFile pdf);
	
	public byte[] downloadPdf(String pdf);

	public void forgotMyPassword(String email) throws MessagingException;
}
