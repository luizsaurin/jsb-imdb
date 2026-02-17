package com.example.imdb.specification;

import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import com.example.imdb.entity.TitleEntity;

public class TitleSpecifications {

	private TitleSpecifications() {
		throw new IllegalStateException("Utility class");
	}

	public static Specification<TitleEntity> nameContains(String name) {
		return (root, query, cb) -> {
			if (Strings.isBlank(name)) return null;
			return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
		};
	}

	public static Specification<TitleEntity> releaseYearGte(Integer year) {
		return (root, query, cb) -> {
			if (year == null) return null;
			return cb.greaterThanOrEqualTo(root.get("releaseYear"), year);
		};
	}
	
	public static Specification<TitleEntity> releaseYearLte(Integer year) {
		return (root, query, cb) -> {
			if (year == null) return null;
			return cb.lessThanOrEqualTo(root.get("releaseYear"), year);
		};
	}
	
}
