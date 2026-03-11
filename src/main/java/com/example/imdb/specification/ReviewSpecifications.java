package com.example.imdb.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.imdb.entity.ReviewEntity;

public class ReviewSpecifications {
	
	private ReviewSpecifications() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<ReviewEntity> titleIdEquals(Long titleId) {
        return (root, query, cb) -> {
            if (titleId == null) return null;
            return cb.equal(root.get("title").get("id"), titleId);
        };
    }

    public static Specification<ReviewEntity> ratingGte(Integer rating) {
        return (root, query, cb) -> {
            if (rating == null) return null;
            return cb.greaterThanOrEqualTo(root.get("rating"), rating);
        };
    }

    public static Specification<ReviewEntity> ratingLte(Integer rating) {
        return (root, query, cb) -> {
            if (rating == null) return null;
            return cb.lessThanOrEqualTo(root.get("rating"), rating);
        };
    }
}
