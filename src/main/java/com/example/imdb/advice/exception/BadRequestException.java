package com.example.imdb.advice.exception;

import org.slf4j.helpers.MessageFormatter;

public class BadRequestException extends RuntimeException {

	public BadRequestException(String message, Object... args) {
		super(MessageFormatter.arrayFormat(message, args).getMessage());
	}
	
}
