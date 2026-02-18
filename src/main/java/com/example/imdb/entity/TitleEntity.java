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
import lombok.ToString;

@Entity
@Getter 
@Setter 
@Table(name = "titles")
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class TitleEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ToString.Include
	@Column(name = "id")
	private Long id;

	@ToString.Include
	@Column(name = "name", unique = true)
	private String name;

	@ToString.Include
	@Column(name = "releaseYear")
	private Integer releaseYear;

	@OneToMany(mappedBy = "title")
	private List<ReviewEntity> reviews;

}
