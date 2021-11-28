package com.mintonomous.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlantDataPayload {
	
	private String plantName;

	private Float temperature;

	private Float light;

	private Float humidity;

	private Float moisture;

}
