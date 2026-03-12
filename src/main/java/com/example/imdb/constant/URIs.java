package com.example.imdb.constant;

public class URIs {
	
	private URIs() {
		throw new IllegalStateException("Utility class");
	}

	public static final String CREATE_TITLE = "/titles";
	public static final String CREATE_TITLES = "/titles/many";
	public static final String FIND_TITLE_BY_ID = "/titles/{id}";
	public static final String FIND_TITLES = "/titles";
	public static final String UPDATE_TITLE = "/titles/{id}";
	public static final String DELETE_TITLE = "/titles/{id}";
	
	public static final String CREATE_REVIEW = "/reviews";
	public static final String FIND_REVIEW_BY_ID = "/reviews/{id}";
	public static final String FIND_REVIEWS = "/reviews";
	public static final String UPDATE_REVIEW = "/reviews/{id}";
}
