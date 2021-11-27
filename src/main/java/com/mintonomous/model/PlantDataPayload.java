package com.mintonomous.model;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlantDataPayload {
	
	private String plantName;

	private Float temperature;

	private Float light;

	private Float humidity;

	private Float mositure;
	
	private LocalDate lastUpdatedDate;

}
