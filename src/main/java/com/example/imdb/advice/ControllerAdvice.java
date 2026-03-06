package com.example.imdb.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.imdb.advice.exception.BadRequestException;
import com.example.imdb.advice.exception.DuplicatedResourceException;
import com.example.imdb.advice.exception.NotFoundException;
import com.example.imdb.constant.ErrorMessages;
import com.example.imdb.dto.ApiErrorResponseDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Void> handleException(Exception ex) {
		log.error(ex.getMessage(), ex);
		return ResponseEntity.internalServerError().build();
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiErrorResponseDTO> handleBadRequestException(BadRequestException ex) {
		return ResponseEntity.badRequest().body(ApiErrorResponseDTO.builder().message(ex.getMessage()).build());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

		FieldError firstError = ex.getBindingResult().getFieldErrors().stream().findFirst().orElse(null);

		if (firstError == null) {
            return ResponseEntity.badRequest().body(
				ApiErrorResponseDTO.builder().message(ErrorMessages.VALIDATION_FAILED).build());
        }

		String message = firstError.getField() + ": " + firstError.getDefaultMessage();

		return ResponseEntity.badRequest().body(ApiErrorResponseDTO.builder().message(message).build());
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Void> handleNotFoundException() {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(DuplicatedResourceException.class)
	public ResponseEntity<ApiErrorResponseDTO> handleDuplicatedResourceException() {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(
			ApiErrorResponseDTO.builder().message(ErrorMessages.RESOURCE_ALREADY_EXISTS).build());
	}

}
