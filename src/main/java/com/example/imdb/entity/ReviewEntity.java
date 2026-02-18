package com.example.imdb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter 
@Setter 
@Table(name = "reviews")
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class ReviewEntity extends BaseEntity {
	
	@Id
	@ToString.Include
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ToString.Include
	@Column(name = "user_name", unique = true)
	private String userName;
	
	@ToString.Include
	@Column(name = "rating")
	private Integer rating;
	
	@ToString.Include
	@Column(name = "description")
	private String description;

	@ManyToOne
	private TitleEntity title;
}
