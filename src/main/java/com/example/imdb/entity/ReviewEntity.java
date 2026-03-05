package com.example.imdb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "reviews", uniqueConstraints = @UniqueConstraint(columnNames = { "email", "title_id" }))
public class ReviewEntity extends BaseEntity {

	@Id
	@ToString.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ToString.Include
	private String email;

	@ToString.Include
	private Integer rating;

	@ToString.Include
	private String description;

	@ManyToOne
	@JoinColumn(name = "title_id")
	private TitleEntity title;
}
