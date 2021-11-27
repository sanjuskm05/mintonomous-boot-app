package com.mintonomous.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity // This tells Hibernate to make a table out of this class
@NoArgsConstructor
@AllArgsConstructor
public class Plant {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer plantId;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	private String description;
	
	private LocalDate lastUpdatedDate;

}
