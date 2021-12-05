package com.mintonomous.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity // This tells Hibernate to make a table out of this class
@NoArgsConstructor
@AllArgsConstructor
public class PlantDataReportPayload {
	
	private Float avgTemperature;

	private Float avgLight;

	private Float avgHumidity;

	private Float avgMoisture;
	
	@Id
	private Integer dailyHour;
	
	private Integer isWaterOn;

}
