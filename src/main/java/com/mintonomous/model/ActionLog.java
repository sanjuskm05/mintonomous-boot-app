package com.mintonomous.model;

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
public class ActionLog {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer actionLogId;

	private Integer actionId;

	private Boolean isComplete;	
	
	private LocalDateTime lastUpdatedDate;
	
	private Integer plantId;

}
