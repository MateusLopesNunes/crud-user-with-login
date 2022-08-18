package br.com.managementSystem.controller;

import java.net.URI;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import br.com.managementSystem.dto.UserUpdateForm;
import br.com.managementSystem.exception.EmailNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.managementSystem.dto.AmazonImageDto;
import br.com.managementSystem.dto.UserDto;
import br.com.managementSystem.dto.UserForm;
import br.com.managementSystem.model.User;
import br.com.managementSystem.service.UserService;
import br.com.managementSystem.service.UserServiceImpl;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@PostMapping
	@Transactional
	public ResponseEntity<UserDto> create(@RequestPart(value = "body") @Valid UserForm form, UriComponentsBuilder builder, @RequestPart(value = "image",required = false ) MultipartFile image, @RequestPart(value = "pdf",required = false ) MultipartFile pdf) {
		UserDto userDto = service.create(form, builder, image, pdf);
		
		URI uri = builder.path("/{id}").buildAndExpand(userDto.getId()).toUri();
		return ResponseEntity.created(uri).body(userDto);
	}
	
	@GetMapping
	public ResponseEntity<Page<UserDto>> findAll(Pageable pageable) {
		Page<UserDto> usersDto = service.findAll(pageable);

		if (!usersDto.hasContent()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(usersDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> findById(@PathVariable Long id) {
		UserDto userDto = service.findById(id);
		return ResponseEntity.ok(userDto);
	}
	
	@GetMapping("/search")
	public ResponseEntity<Page<UserDto>> findByName(@RequestParam(required = false) String name, Pageable pageable) {
		Page<UserDto> usersDto = service.findByName(name, pageable);

		if (!usersDto.hasContent()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(usersDto);
	}
	
	@PostMapping("/images")
	public ResponseEntity<byte[]> downloadPdf(String pdf) {
		byte[] userDto = service.downloadPdf(pdf);
		return ResponseEntity.ok(userDto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<UserDto> deleteById(@PathVariable Long id) {
		UserDto userDto = service.deleteById(id);
		return ResponseEntity.ok(userDto);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<UserDto> update(@RequestPart(value = "body") @Valid UserUpdateForm form, @PathVariable Long id, @RequestPart(value = "image",required = false ) MultipartFile image, @RequestPart(value = "pdf",required = false ) MultipartFile pdf) {
		UserDto userDto = service.update(form, id, image, pdf);
		return ResponseEntity.ok(userDto);
	}
	
	@GetMapping("/resetPassword")
	@Transactional
	public ResponseEntity forgotMyPassword(@RequestParam(required = true) String email) throws MessagingException {
		service.forgotMyPassword(email);
		return ResponseEntity.noContent().build();
    }
}
