package com.example.imdb.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "titles")
@Getter @Setter
@NoArgsConstructor
public class TitleEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "actors")
	private List<String> actors;
	
	@Column(name = "releaseYear")
	private Integer releaseYear;

	@OneToMany(mappedBy = "title")
	private List<ReviewEntity> reviews;

}
