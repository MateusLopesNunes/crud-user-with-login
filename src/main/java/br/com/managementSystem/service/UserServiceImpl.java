package br.com.managementSystem.service;

import java.util.Optional;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import br.com.managementSystem.dto.UserUpdateForm;
import br.com.managementSystem.exception.EmailNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.managementSystem.dto.UserDto;
import br.com.managementSystem.dto.UserForm;
import br.com.managementSystem.exception.DataIntegratyViolationException;
import br.com.managementSystem.exception.ObjectNotFoundException;
import br.com.managementSystem.model.AmazonFile;
import br.com.managementSystem.model.AmazonImage;
import br.com.managementSystem.model.User;
import br.com.managementSystem.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AmazonS3ImageService amazonS3ImageService;
	
	@Autowired
	private AmazonS3FileService amazonS3FileService;
	
    @Autowired 
    private JavaMailSender mailSender;
	
	@Override
	public UserDto create(UserForm form, UriComponentsBuilder builder, MultipartFile image, MultipartFile pdf) {
		User user = form.dtoToModel();
		verifyIfCpfExists(user);
		verifyIfEmailExists(user);

		AmazonImage imageUpload = amazonS3ImageService.uploadImageToAmazon(image);
		AmazonFile pdfUpload = amazonS3FileService.uploadFileToAmazon(pdf);	
			
		user.setPhoto(imageUpload);		
		user.setCurriculum(pdfUpload);
		userRepository.save(user);
		
		UserDto userDto = new UserDto(user);
		return userDto;
	}
	
	@Override
	public Page<UserDto> findAll(Pageable pageable) {
		Page<User> users = userRepository.findAll(pageable);
		return UserDto.modelToDto(users);
	}
	
	@Override
	public UserDto findById(Long id) {
		User user = verifyIfUserExists(id);
		return new UserDto(user);
	}
	
	@Override
	public Page<UserDto> findByName(String name, Pageable pageable) {
		Page<User> user = userRepository.findByName(name, pageable);
		return UserDto.modelToDto(user);
	}
	
	@Override
	public UserDto deleteById(Long id) {
		User user = verifyIfUserExists(id);
		
		if (user.getPhoto() != null) {
			amazonS3ImageService.removeImageFromAmazon(user.getPhoto());
		}
		if (user.getCurriculum() != null) {
			amazonS3FileService.removeImageFromAmazon(user.getCurriculum());
		}
		
		userRepository.deleteById(id);
		return new UserDto(user);
	} 
	 
	@Override
	public UserDto update(UserUpdateForm form, Long id, MultipartFile image, MultipartFile pdf) {
		User user = verifyIfUserExists(id);
		
		if (user.getPhoto() != null) {
			amazonS3ImageService.removeImageFromAmazon(user.getPhoto());
		}
		if (user.getCurriculum() != null) {
			amazonS3FileService.removeImageFromAmazon(user.getCurriculum());
		}
		
		AmazonImage imageUpload = amazonS3ImageService.uploadImageToAmazon(image);
		AmazonFile pdfUpload = amazonS3FileService.uploadFileToAmazon(pdf);	
		
		User userForm = form.update(user);
		userForm.setPhoto(imageUpload);
		userForm.setCurriculum(pdfUpload);
		System.out.println(userForm);
		verifyIfEmailExists(userForm);
		verifyIfCpfExists(userForm);
		userRepository.save(userForm);
		
		return new UserDto(userForm);
	}
	
	@Override
	public byte[] downloadPdf(String pdf) {
		return amazonS3FileService.downloadFile(pdf);
	}

	@Override
	public synchronized void forgotMyPassword(String email) {
		User user = verifyIfEmailNotExists(email);
        String token = UUID.randomUUID().toString();

		String message = "<span style=\"font-size:20px\">Hello " + user.getName() + "!</span><br/><br/>"
				+ "We have received your request to change the system password Contact Directory.<br/>"
				+ "Your new password is: <span style=\"font-weight: bold; color: #FF0000\">" + token + "</span><br/>"
				+ "For your security, please change your password the first time you access the system. <br/>"
				+ "Sincerely,<br/>"
				+ "Development equipment.";

		findEmail(message, email, "Reset password");

		user.setPass(new BCryptPasswordEncoder().encode(token));
		userRepository.save(user);
    }

	private void findEmail(String text, String toEmail, String subject) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					MimeMessage mail = mailSender.createMimeMessage();
					MimeMessageHelper message = new MimeMessageHelper(mail);

					message.setText(text, true);
					message.setFrom("matheuslopesnunes9@gmail.com");
					message.setTo(toEmail);
					message.setSubject(subject);
					mailSender.send(mail);

				} catch (MessagingException ex) {
					ex.getMessage();
				}
			}
		}).start();
	}
	
	private User verifyIfEmailNotExists(String email) {
		Optional<User> userOpt = userRepository.findByEmail(email);

		if (userOpt.isEmpty()) {
			throw new EmailNotFoundException("This email not exists");
		}
		return userOpt.get();
	}
	
	private User verifyIfUserExists(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User not found"));
		return user;
	}
	
	private void verifyIfEmailExists(User obj) {
		Optional<User> userOpt = userRepository.findByEmail(obj.getEmail());
		System.out.println(obj);
		System.out.println(userOpt.isPresent());

		if (userOpt.isPresent()) {
			System.out.println(userOpt.get());
		}

		if (userOpt.isPresent() && !userOpt.get().getId().equals(obj.getId())) {
			throw new DataIntegratyViolationException("This email aready exists");
		}
	}
	
	private void verifyIfCpfExists(User obj) {
		Optional<User> userOpt = userRepository.findByCpf(obj.getCpf());
		System.out.println(obj);
		System.out.println(userOpt.isPresent());

		if (userOpt.isPresent()) {
			System.out.println(userOpt.get());
		}

		if (userOpt.isPresent() && !userOpt.get().getId().equals(obj.getId())) {
			throw new DataIntegratyViolationException("This cpf aready exists");
		}
	}
}
