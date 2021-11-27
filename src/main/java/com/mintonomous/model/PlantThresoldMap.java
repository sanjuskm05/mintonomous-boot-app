package com.mintonomous.model;

import java.time.LocalDate;

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
public class PlantThresoldMap {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer plantThresoldMapId;
	
	private Integer plantId;

	private Float minTemperature;

	private Float minLight;

	private Float minHumidity;

	private Float minMositure;
	
	private Float maxTemperature;

	private Float maxLight;

	private Float maxHumidity;

	private Float maxMositure;
	
	private LocalDate lastUpdatedDate;
	
	private Integer configuredActionId;

}
