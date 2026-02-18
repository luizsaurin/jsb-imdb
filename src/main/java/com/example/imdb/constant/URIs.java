package com.example.imdb.constant;

public class URIs {
	
	private URIs() {
		throw new IllegalStateException("Utility class");
	}

	public static final String CREATE_TITLE = "/titles";
	public static final String CREATE_TITLES = "/titles/many";
	public static final String FIND_TITLE_BY_ID = "/titles/{id}";
	public static final String FIND_ALL_TITLES = "/titles";
	public static final String UPDATE_TITLE = "/titles/{id}";
}
