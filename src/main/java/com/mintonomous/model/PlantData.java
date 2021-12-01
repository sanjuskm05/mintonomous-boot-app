package com.mintonomous.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
public class PlantData {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer plantDataId;
	
	private Integer plantId;

	private Float temperature;

	private Float light;

	private Float humidity;

	private Float moisture;
	
	private LocalDateTime lastUpdatedDate;
	
	private Boolean isAnalyzed = Boolean.FALSE;

}
