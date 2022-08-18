package br.com.managementSystem.configuration.error;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import br.com.managementSystem.exception.EmailNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import br.com.managementSystem.dto.ExceptionResponseDto;
import br.com.managementSystem.dto.ExceptionValidationDto;
import br.com.managementSystem.exception.DataIntegratyViolationException;
import br.com.managementSystem.exception.ObjectNotFoundException;

@RestControllerAdvice
public class ErrorHandler {

	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ObjectNotFoundException.class)
	public ExceptionResponseDto userNotFound(ObjectNotFoundException exception) {
		return new ExceptionResponseDto(HttpStatus.NOT_FOUND.value(), exception.getMessage());
	}
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UsernameNotFoundException.class)
	public ExceptionResponseDto loginException(UsernameNotFoundException exception) {
		return new ExceptionResponseDto(HttpStatus.NOT_FOUND.value(), exception.getMessage());
	}


	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DataIntegratyViolationException.class)
	public ExceptionResponseDto emailAlreadyExists(DataIntegratyViolationException exception) {
		return new ExceptionResponseDto(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(EmailNotFoundException.class)
	public ExceptionResponseDto emailNotFound(EmailNotFoundException exception) {
		return new ExceptionResponseDto(HttpStatus.NOT_FOUND.value(), exception.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<?> handle(MethodArgumentNotValidException exception) {
		List<ExceptionValidationDto> dto = new ArrayList<>();
		List<FieldError> fieldError = exception.getBindingResult().getFieldErrors();

		fieldError.forEach(x -> {
			String messageError = messageSource.getMessage(x, LocaleContextHolder.getLocale());
			ExceptionValidationDto erro = new ExceptionValidationDto(x.getField(), messageError);
			dto.add(erro);
		});

		return dto;
	}
}
