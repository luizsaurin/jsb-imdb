package com.example.imdb.advice.exception;

import org.slf4j.helpers.MessageFormatter;

public class NotFoundException extends RuntimeException {

	public NotFoundException(String message, Object... args) {
		super(MessageFormatter.arrayFormat(message, args).getMessage());
	}
	
}
