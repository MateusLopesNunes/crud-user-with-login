package br.com.managementSystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.managementSystem.dto.UserDto;
import br.com.managementSystem.exception.ObjectNotFoundException;
import br.com.managementSystem.model.User;
import br.com.managementSystem.model.enumeration.Sex;
import br.com.managementSystem.repository.UserRepository;

@SpringBootTest
class UserServiceImplTest {
	
	private static final Long ID = 1L;
	private static final String GITHUB = "url-3";
	private static final String FACEBOOK = "url-2";
	private static final String linkedin = "url-1";
	private static final String SCHOOLING = "Graduando";
	private static final String ENDEREÇO = "endereço";
	private static final String SENHA = "158C222E9B2BA8408C74DF45F82620B2F672B34EF4C5C3719FCED23E73077E56";
	private static final String CPF = "927.090.810-08";
	private static final String EMAIL = "mateus@gmail.com";
	private static final String NICKNAME = "cradfor";
	private static final String USER = "Mateus Lopes Nunes";
	private static final Sex SEX = Sex.MASCULINE;

	@InjectMocks
	private UserServiceImpl service;
	
	@Mock
	private UserRepository repository;
	
	private UriComponentsBuilder uriBuilder;
	private Pageable pageable;
	
	private Optional<User> userOpt;
	private UserDto userDto;
	private User user;
	private Page<User> userPage;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(service);
		startUser();
	}
	
	@Test
	public void whenFindByIdThenReturnUserDto() {
		Mockito.when(repository.findById(ID)).thenReturn(userOpt);
		
		UserDto response = service.findById(ID);
		
		assertNotNull(response);
		assertEquals(UserDto.class, response.getClass());
		assertEquals(userDto.getId(), response.getId());
		assertEquals(userDto.getName(), response.getName());
		assertEquals(userDto.getNickname(), response.getNickname());
		assertEquals(userDto.getEmail(), response.getEmail());
		assertEquals(userDto.getCpf(), response.getCpf());
		assertEquals(userDto.getAddress(), response.getAddress());
		assertEquals(userDto.getSchooling(), response.getSchooling());
		assertEquals(userDto.getSex(), response.getSex());
		assertEquals(userDto.getCurriculumUrl(), response.getCurriculumUrl());
		assertEquals(userDto.getLinkedin(), response.getLinkedin());
		assertEquals(userDto.getFacebook(), response.getFacebook());
		assertEquals(userDto.getGithub(), response.getGithub());
	}
	
	@Test
	public void whenFindByIdThenReturnAnObjectNotFoundException() {
		when(repository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Object not found"));

		try {
			service.findById(ID);
		} catch (Exception exception) {
			assertEquals(ObjectNotFoundException.class, exception.getClass());
			assertEquals("Object not found", exception.getMessage());
		}
	}

	@Test
	public void whenFindAllThenReturnPageDto() {
		Mockito.when(repository.findAll(pageable)).thenReturn(userPage);
		
		Page<UserDto> response = service.findAll(pageable);
		UserDto responseObj = response.getContent().get(0);
		
		assertNotNull(response);
		assertEquals(UserDto.class, responseObj.getClass());
		assertEquals(1L, response.getContent().size());
		assertEquals(userDto.getId(), responseObj.getId());
		assertEquals(userDto.getName(), responseObj.getName());
		assertEquals(userDto.getNickname(), responseObj.getNickname());
		assertEquals(userDto.getEmail(), responseObj.getEmail());
		assertEquals(userDto.getCpf(), responseObj.getCpf());
		assertEquals(userDto.getAddress(), responseObj.getAddress());
		assertEquals(userDto.getSchooling(), responseObj.getSchooling());
		assertEquals(userDto.getSex(), responseObj.getSex());
		assertEquals(userDto.getCurriculumUrl(), responseObj.getCurriculumUrl());
		assertEquals(userDto.getLinkedin(), responseObj.getLinkedin());
		assertEquals(userDto.getFacebook(), responseObj.getFacebook());
		assertEquals(userDto.getGithub(), responseObj.getGithub());
	}
	
	private void startUser() {
		user = new User(USER, NICKNAME, EMAIL, CPF, SENHA, ENDEREÇO, SCHOOLING, linkedin, FACEBOOK, GITHUB, SEX);
		user.setId(ID);
		userDto = new UserDto(ID, USER, NICKNAME, EMAIL, CPF, ENDEREÇO, SCHOOLING, linkedin, FACEBOOK, GITHUB, SEX);
		userOpt = Optional.of(user);
		userPage = new PageImpl<>(List.of(user));
	}
}
